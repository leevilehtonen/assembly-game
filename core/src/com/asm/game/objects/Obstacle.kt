package com.asm.game.objects

import com.asm.game.utils.Constants
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Body

class Obstacle(val body: Body, val texture: TextureRegion, var speed: Float, val offsetBody: Float = 0f) : PhysicsGameObject(texture) {

    init {
        sprite.setPosition(Constants.BOX_TO_WORLD * body.position.x - sprite.width / 2, Constants.BOX_TO_WORLD * body.position.y - sprite.height / 2)
        sprite.rotation = body.angle * MathUtils.radiansToDegrees

    }
    override fun update(delta: Float) {
        sprite.x -= delta * speed
        sprite.y = Constants.BOX_TO_WORLD * body.position.y - sprite.height/2 - offsetBody
        body.setTransform((sprite.x+sprite.width/2) * Constants.WORLD_TO_BOX, body.position.y, 0f)
    }
}