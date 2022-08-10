package a3

import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment

class Scene1 {
    public fun setup(): Scene {
        val titleImage = ImageView("images/logo.png")
        val authorText = Text("Peiyun Chen, 20817850")
        authorText.font = Font.font("Verdana", FontWeight.BOLD, 40.0)
        val instructionTitle = Text("Instruction: ")
        instructionTitle.font = Font.font("Verdana", FontWeight.BOLD, 50.0)

        val instructionText = Text(
            "ENTER - Start Game\nA or <-, D or -> - Move ship left or right.\n" +
                    "SPACE - Fire!\n" +
                    "Q - Quit Game\n" +
                    "1 or 2 or 3 - Start Game at a specific level\n"
        )
        instructionText.textAlignment = TextAlignment.CENTER
        instructionText.lineSpacing = 10.0
        instructionText.font = Font.font("Verdana", FontWeight.NORMAL, 20.0)
        val textBox = VBox(instructionTitle, instructionText)
        textBox.alignment = Pos.CENTER
        textBox.spacing = 40.0

        val root1 = VBox(titleImage, authorText, textBox)
        root1.spacing = 150.0
        root1.alignment = Pos.CENTER

        return Scene(root1, 1600.0, 1200.0)
    }
}