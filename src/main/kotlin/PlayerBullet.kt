package a3

import javafx.scene.image.ImageView

class PlayerBullet(player: Player): ImageView("images/player_bullet.png") {
    var moveSpeed = 15.0
    var isDead = false
    init {
        this.translateX = player.translateX + player.image.width / 2
        this.translateY = player.translateY
    }

    fun update() {
        this.translateY -= moveSpeed
    }
}