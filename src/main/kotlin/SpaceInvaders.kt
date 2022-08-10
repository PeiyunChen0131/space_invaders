package a3

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.stage.Stage
import java.security.Key
import java.util.Scanner
import kotlin.system.exitProcess


class SpaceInvaders: Application() {
    var scene1: Scene? = Scene1().setup()

    override fun start(primaryStage: Stage) {
        var scene2build = Scene2(primaryStage)
        primaryStage.maxWidth = 1600.0
        primaryStage.maxHeight = 1200.0
        primaryStage.minWidth = 720.0
        primaryStage.minHeight = 470.0

        primaryStage.title = "Space Invaders"

        primaryStage.scene = scene1

        scene1?.onKeyTyped = EventHandler{
            when (it.character) {
                "1" -> {
                    scene2build.level = 1
                    primaryStage.scene = scene2build.setup()
                }
                "2" -> {
                    scene2build.level = 2
                    primaryStage.scene = scene2build.setup()
                }
                "3" -> {
                    scene2build.level = 3
                    primaryStage.scene = scene2build.setup()
                }
            }
        }

        enterFunc(primaryStage, scene2build)
        primaryStage.isResizable = false
        primaryStage.show()
    }

    private fun enterFunc(stage: Stage, scene2build: Scene2) {
        scene1?.onKeyPressed = EventHandler<KeyEvent> {
            if (it.code == KeyCode.ENTER) {
                stage.scene = scene2build.setup()
            }
            if (it.code == KeyCode.Q) {
                exitProcess(0)
            }
        }
    }
}
