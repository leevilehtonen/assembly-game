package com.asm.game.objects

import com.asm.game.utils.Constants
import com.asm.game.utils.PlayerState
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

class Player(val body: Body, val texture: TextureRegion, val animation: PlayerAnimation) : PhysicsGameObject(texture) {

    var playerState: PlayerState = PlayerState.POSITION_BOTTOM
    var lastState: PlayerState = PlayerState.POSITION_BOTTOM
    var rotate: Boolean = false
    var walkStateTime: Float = 0f

    override fun update(delta: Float) {
        sprite.setPosition(Constants.BOX_TO_WORLD * body.position.x - sprite.width / 2, Constants.BOX_TO_WORLD * body.position.y - sprite.height / 2)
        println(sprite.rotation.toString() + "        " + body.angle)
        sprite.rotation = body.angle * MathUtils.radiansToDegrees
        walkStateTime += delta
        sprite.setRegion(animation.walkAnimation.getKeyFrame(walkStateTime, true))


        if (Gdx.input.isTouched && playerState != PlayerState.POSITION_INAIR) {
            jump()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && playerState != PlayerState.POSITION_INAIR) {
            gravityFlip()
        }
        if (rotate) {
            val addition: Float = delta * Constants.ROTATE_SPEED
            if (lastState == PlayerState.POSITION_BOTTOM) {
                if (sprite.rotation + addition > 180f) {
                    sprite.rotation = 180f
                    rotate = false
                } else {
                    sprite.rotation += addition
                }
            } else if (lastState == PlayerState.POSITION_TOP) {
                if (sprite.rotation - addition < 0f) {
                    sprite.rotation = 0f
                    rotate = false
                } else {
                    sprite.rotation -= addition
                }
            }
        }
    }

    fun gravityFlip() {
        body.world.gravity = body.world.gravity.cpy().scl(-1f)
        lastState = playerState
        playerState = PlayerState.POSITION_INAIR
        rotate = true

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