package com.asm.game.objects

import com.asm.game.utils.Constants
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.Body

class Obstacle(val body: Body, val texture: Texture, var speed: Float, val offsetBody: Float = 0f) : PhysicsGameObject(texture) {

    init {
        sprite.setPosition(Constants.BOX_TO_WORLD * body.position.x - sprite.width / 2, Constants.BOX_TO_WORLD * body.position.y - sprite.height / 2)
    }
    override fun update(delta: Float) {
        sprite.x -= delta * speed
        sprite.y = Constants.BOX_TO_WORLD * body.position.y - sprite.height/2 - offsetBody
        body.setTransform((sprite.x+sprite.width/2) * Constants.WORLD_TO_BOX, body.position.y, 0f)
    }
}