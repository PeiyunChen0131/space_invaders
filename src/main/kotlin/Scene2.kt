package a3

import javafx.animation.AnimationTimer
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.layout.Pane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.util.Duration
import java.util.Random
import kotlin.system.exitProcess

class Scene2(stage: Stage) {
    private var primaryStage = stage
    private var root = Pane()
    private var keyAPressed: Int = 0
    private var keyDPressed: Int = 0
    private var time = 0.0
    private var timeStep = 0.05
    private var enemyGroup = Group()
    private var playerGroup = Group()
    private var moveDir = 0

    var shootAvailable = 2
    var scoreText = Text("Scores: 0")
    var lifeText = Text("Lives: 0")
    var levelText = Text("Level: 0")

    var score = 0
    var life = 2
    var level = 1

    var timer: AnimationTimer? = null

    private var player1 = Player("images/player.png")

    fun setup(): Scene {
        setText()

        nextLevel(level)

        playerGroup.children.add(player1)
        var sc = Scene(root, 1600.0, 1200.0)
        root.style = "-fx-background-color: black;"
        root.children.add(enemyGroup)
        root.children.add(playerGroup)

        sc.onKeyPressed = EventHandler {
            when (it.code) {
                KeyCode.A, KeyCode.LEFT -> {
                    keyAPressed = 1
                    player1.moveLeft()
                }
                KeyCode.D, KeyCode.RIGHT -> {
                    keyDPressed = 1
                    player1.moveRight()
                }
                KeyCode.R -> {
                    timer?.stop()
                    primaryStage.scene = Scene2(primaryStage).setup()
                }
                KeyCode.Q -> {
                    exitProcess(0)
                }
                KeyCode.SPACE -> {
                    var bullet = player1.shoot()
                    if (shootAvailable > 0) {
                        enemyGroup.children.add(bullet)
                        playSound("shoot")
                        shootAvailable -= 1
                    }
                }
            }
        }

        sc.onKeyReleased = EventHandler {
            when (it.code) {
                KeyCode.A, KeyCode.LEFT -> {
                    keyAPressed = 0
                }
                KeyCode.D, KeyCode.RIGHT -> {
                    keyDPressed = 0
                }
            }
            if (keyAPressed == 0 && keyDPressed == 0) {
                player1.stop()
            }
        }

        timer = object : AnimationTimer() {
            @Override
            override fun handle(now: Long) {
                time += timeStep
                if (time >= 2.0) {
                    playSound("fastinvader1")
                    shootAvailable = 2
                    time = 0.0
                }

                update()
            }
        }

        (timer as AnimationTimer).start()
        return sc
    }

    private fun update() {
        var alienDead = false
        var enemyShoot = false
        if (moveDir > 0) {
            enemyGroup.children.forEach {
                if (it is Enemy) {
                    it.moveSpeed *= -1
                    it.nextStep()
                }
            }
            moveDir = 0
            enemyShoot = true
        } else {
            enemyGroup.children.forEach {
                if (it is Enemy) {
                    it.update()
                }
            }
        }
        if (enemyShoot) {
            val enemyList = enemyGroup.children.filtered {
                it is Enemy
            }
            if (enemyList != null && enemyList.isNotEmpty()) {
                val tmpSize = enemyList.size
                var shootedOne: Int
                if (tmpSize == 1) {
                    shootedOne = 0
                } else {
                    shootedOne = Random().nextInt(0, tmpSize - 1)
                }

                val curItem = enemyList[shootedOne]
                if (curItem is Enemy) {
                    playerGroup.children.add(curItem.shoot())
                    playSound("shoot")
                }
            }

        }

        enemyGroup.children.stream().forEach{
            if (it is Enemy) {
                if (moveDir == 0) {
                    moveDir = it.check()
                }
            }
            if (it is PlayerBullet) {
                it.update()
                if (it.translateY < 0) {
                    it.isDead = true
                }
                enemyGroup.children.filtered { it1 ->  (it1 is Enemy) }.forEach{ enemy ->
                    if (it.boundsInParent.intersects(enemy.boundsInParent)) {
                        score += level
                        if (enemy is Enemy) {
                            enemy.isDead = true
                        }
                        it.isDead = true
                        playSound("explosion")
                        alienDead = true
                    }
                }
            }
        }

        playerGroup.children.stream().forEach{
            if (it is Player) {
                it.update()
            }
            if (it is EnemyBullet) {
                it.update()
                if (it.translateY < 0) {
                    it.isDead = true
                }
                if (player1.boundsInParent.intersects(it.boundsInParent)) {
                    life -= 1
                    player1.isDead = true
                    it.isDead = true
                    playSound("explosion")
                }
            }
        }

        playerGroup.children.removeIf {
            if (it is Player) {
                it.isDead
            } else if (it is EnemyBullet) {
                it.isDead
            } else {
                false
            }
        }
        enemyGroup.children.removeIf {
            if (it is Enemy) {
                it.isDead
            } else if (it is PlayerBullet) {
                it.isDead
            } else {
                false
            }
        }

        if (enemyGroup.children.filtered{it is Enemy}.isEmpty()) {
            if (level == 3) {
                timer?.stop()
                primaryStage.scene = SceneWin(primaryStage).setup(score)
            }
            level += 1
            nextLevel(level)
        }

        if (playerGroup.children.filtered{it is Player}.isEmpty()) {
            if (life < 0) {
                timer?.stop()
                primaryStage.scene = SceneLose(primaryStage).setup(score)
            }
            player1 = Player("images/player.png")
            player1.translateX = Random().nextDouble(0.0, 1300.0)
            playerGroup.children.add(player1)
        }

        if (alienDead) {
            enemyGroup.children.filtered{ it is Enemy }.forEach {
                if (it is Enemy) {
                    if (it.moveSpeed < 0) {
                        it.moveSpeed -= 0.5
                    } else {
                        it.moveSpeed += 0.5
                    }
                }
            }
        }

        levelText.text = "Level: $level"
        scoreText.text = "Scores: $score"
        lifeText.text = "Lives: $life"
    }

    private fun nextLevel(level: Int) {
        for (j in 1..2) {
            for (i in 1..10) {
                var enemy = Enemy("images/enemy${3 - j}.png", 3 - j, level)
                enemy.fitWidth = 100.0
                enemy.fitHeight = 80.0
                enemy.translateX = 150 + i * 100.0 + 20
                enemy.translateY = 100 + 2 * j * 80.0 + 10
                enemyGroup.children.add(enemy)
            }
            for (i in 1..10) {
                var enemy = Enemy("images/enemy${3 - j}.png", 3 - j, level)
                enemy.fitWidth = 100.0
                enemy.fitHeight = 80.0
                enemy.translateX = 150 + i * 100.0 + 20
                enemy.translateY = 100 + (2 * j + 1) * 80.0 + 10
                enemyGroup.children.add(enemy)
            }
        }

        for (i in 1..10) {
            var enemy = Enemy("images/enemy${3}.png", 3, level)
            enemy.fitWidth = 100.0
            enemy.fitHeight = 80.0
            enemy.translateX = 150 + i * 100.0 + 20
            enemy.translateY = 100 + 80.0 + 10
            enemyGroup.children.add(enemy)
        }
    }
    private fun setText() {
        levelText.font = Font.font("Verdana", FontWeight.BOLD, 30.0)
        lifeText.font = Font.font("Verdana", FontWeight.BOLD, 30.0)
        scoreText.font = Font.font("Verdana", FontWeight.BOLD, 30.0)

        levelText.fill = Color.WHITE
        lifeText.fill = Color.WHITE
        scoreText.fill = Color.WHITE

        scoreText.translateX = 50.0
        scoreText.translateY = 50.0

        lifeText.translateX = 1200.0
        lifeText.translateY = 50.0

        levelText.translateX = 1400.0
        levelText.translateY = 50.0

        root.children.add(scoreText)
        root.children.add(lifeText)
        root.children.add(levelText)
    }

    private fun playSound(fileName: String) {
        return MediaPlayer(Media(Enemy::class.java.classLoader.getResource("sounds/$fileName.wav")?.toExternalForm())).play()
    }
}