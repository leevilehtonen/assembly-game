package com.asm.game.objects

import com.asm.game.utils.Constants
import com.asm.game.utils.PlayerState
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

class Player(val body: Body, val texture: Texture) : PhysicsGameObject(texture) {

    var playerState: PlayerState = PlayerState.POSITION_BOTTOM

    override fun update(delta: Float) {
        sprite.setPosition(Constants.BOX_TO_WORLD * body.position.x - sprite.width / 2, Constants.BOX_TO_WORLD * body.position.y - sprite.height / 2)

        if (Gdx.input.isTouched && playerState != PlayerState.POSITION_INAIR) {
            jump()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && playerState != PlayerState.POSITION_INAIR) {
            gravityFlip()
        }
    }

    fun gravityFlip() {
        body.world.gravity = body.world.gravity.cpy().scl(-1f)
        playerState = PlayerState.POSITION_INAIR

    }

    fun jump() {
        if (playerState == PlayerState.POSITION_TOP) {
            body.applyLinearImpulse(Vector2(0F, -Constants.JUMP_AMOUNT), body.position, true)
        } else if (playerState == PlayerState.POSITION_BOTTOM) {
            body.applyLinearImpulse(Vector2(0F, Constants.JUMP_AMOUNT), body.position, true)
        }
        playerState = PlayerState.POSITION_INAIR

    }
}