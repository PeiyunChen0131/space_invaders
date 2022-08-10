package a3

import javafx.scene.image.ImageView
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer


class Enemy(url: String, type: Int, level: Int): ImageView(url) {
    var type = type
    var level = 1
    var isDead = false
    var moveSpeed: Double = level * 5.0

    fun moveLeft() {
        this.translateX -= moveSpeed
    }

    fun moveRight() {
        this.translateX += moveSpeed
    }
    fun check(): Int {
        if (this.translateX + moveSpeed >= 0 && this.translateX + moveSpeed + this.image.width <= 1600.0) {
            return 0
        } else {
            return 1
        }
    }
    fun update(): Int {

        if (this.translateX + moveSpeed >= 0 && this.translateX + moveSpeed + this.image.width <= 1600.0) {
            this.translateX += moveSpeed
        } else {
            return 1
        }
        return 0
    }

    fun nextStep() {
        this.translateY += 12.0
    }

    fun shoot(): EnemyBullet {
        return EnemyBullet(this, type)
    }
}