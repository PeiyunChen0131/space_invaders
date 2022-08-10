package a3

import javafx.scene.image.ImageView

class EnemyBullet(enemy: Enemy, type: Int): ImageView("images/bullet${type}.png") {
    var moveSpeed = 10.0
    var isDead = false
    init {
        this.translateX = enemy.translateX + enemy.image.width / 2
        this.translateY = enemy.translateY + enemy.image.height
    }

    fun update() {
        this.translateY += moveSpeed
    }
}