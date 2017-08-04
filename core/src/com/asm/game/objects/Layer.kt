package com.asm.game.objects

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxArrayOf

class Layer(val speed: Float, val textureRegion: TextureRegion, val y: Float) {
    val sprites = gdxArrayOf<Sprite>()
    val minX = -1586f
    val moveToX = 1486f
    val spawn1 = -50f
    lateinit var sprite1: Sprite
    lateinit var sprite2: Sprite

    init {
        sprite1 = Sprite(textureRegion)
        sprite2 = Sprite(textureRegion)

        sprite1.x = minX
        sprite2.x = sprite1.x + sprite1.width
        sprite1.y = y
        sprite2.y = y

        addSprite(sprite1)
        addSprite(sprite2)
    }

    fun addSprite(sprite: Sprite) {
        sprites.add(sprite)
    }

    fun update(delta: Float) {
        sprites.forEach {
            it.x -= speed * delta
            if (it.x <= minX) it.x += it.width * 2f
        }
    }
}