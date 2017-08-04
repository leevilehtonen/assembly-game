package com.asm.game.objects

import com.asm.game.utils.Constants
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.Body

class Player(val body: Body, val texture: Texture) : PhysicsGameObject(texture) {

    override fun update(delta: Float) {
        sprite.setPosition(Constants.BOX_TO_WORLD * body.position.x - sprite.width / 2, Constants.BOX_TO_WORLD * body.position.y - sprite.height / 2)
    }
}