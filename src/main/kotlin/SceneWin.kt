package a3

import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.stage.Stage
import kotlin.system.exitProcess

class SceneWin(stage: Stage) {
    private var primaryStage = stage

    fun setup(score: Int): Scene {
        val titleImage = ImageView("images/logo.png")
        val authorText = Text("Peiyun Chen, 20817850")
        authorText.font = Font.font("Verdana", FontWeight.BOLD, 40.0)
        val loseTitle = Text("You Win!")
        loseTitle.font = Font.font("Verdana", FontWeight.BOLD, 50.0)

        val loseScore = Text("Final Score: $score\n" +
                "R - Restart Game\n" +
                "Q - Quit Game\n")
        loseScore.textAlignment = TextAlignment.CENTER
        loseScore.lineSpacing = 10.0
        loseScore.font = Font.font("Verdana", FontWeight.NORMAL, 20.0)
        val textBox = VBox(loseTitle, loseScore)
        textBox.alignment = Pos.CENTER
        textBox.spacing = 40.0

        val root1 = VBox(titleImage, authorText, textBox)
        root1.spacing = 150.0
        root1.alignment = Pos.CENTER

        val sc = Scene(root1, 1600.0, 1200.0)
        sc.onKeyPressed = EventHandler {
            when (it.code) {
                KeyCode.R -> {
                    primaryStage.scene = Scene2(primaryStage).setup()
                }
                KeyCode.Q -> {
                    exitProcess(0)
                }
            }
        }

        return sc
    }
}