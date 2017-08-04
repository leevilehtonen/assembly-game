package com.asm.game.objects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.Body

class Obstacle(val body: Body, val texture: Texture, val speed: Float) : PhysicsGameObject(texture)  {

    val minX = 0 + texture.width

    override fun update(delta: Float) {
        sprite.setPosition(sprite.x - (speed * delta),sprite.y)
    }
}