package a3

import javafx.scene.image.ImageView


class Player(url: String): ImageView(url) {
    private var moveSpeed: Double = 0.0
    var isDead = false

    init {
        this.translateX = 700.0
        this.translateY = 1076.0
    }

    fun moveLeft() {
        moveSpeed = -20.0
    }

    fun moveRight() {
        moveSpeed = 20.0
    }

    fun stop() {
        moveSpeed = 0.0
    }

    fun update() {
        if (this.translateX + moveSpeed >= 0 && this.translateX + moveSpeed + this.image.width <= 1600.0) {
            this.translateX += moveSpeed
        }
    }

    fun shoot(): PlayerBullet {
        return PlayerBullet(this)
    }
}