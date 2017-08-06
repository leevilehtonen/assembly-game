package com.asm.game.objects

import com.asm.game.utils.Constants
import com.asm.game.utils.PlayerState
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

class Player(body: Body, texture: TextureRegion, val animation: PlayerAnimation) : PhysicsGameObject(body, texture, 0f) {


    var playerState: PlayerState = PlayerState.POSITION_BOTTOM
    var lastState: PlayerState = PlayerState.POSITION_BOTTOM
    var rotate: Boolean = false
    var walkStateTime: Float = 0f
    var coins: Int = 0
    var points: Float = 0f

    override fun update(delta: Float) {
        if (body.position.x * Constants.BOX_TO_WORLD > Constants.GAME_WIDTH) {
            body.setTransform(Constants.GAME_WIDTH * Constants.WORLD_TO_BOX, body.position.y, body.angle)
        }
        sprite.setPosition(Constants.BOX_TO_WORLD * body.position.x - sprite.width / 2, Constants.BOX_TO_WORLD * body.position.y - sprite.height / 2)
        sprite.rotation = body.angle * MathUtils.radiansToDegrees
        walkStateTime += delta
        sprite.setRegion(animation.walkAnimation.getKeyFrame(walkStateTime, true))


        if ((Gdx.input.isTouched || Gdx.input.isKeyPressed(Input.Keys.SPACE)) && playerState != PlayerState.POSITION_INAIR) {
            jump()
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


        applyForce()
    }

    fun applyForce() {
        val width = Constants.GAME_WIDTH
        val distance = Math.abs((sprite.x + sprite.width / 2) - width)
        var forceMultiplier = 1f / distance * 10
        if (distance < width / 2) {
            forceMultiplier *= -1
        } else if(distance <= width / 2 + width / 4 && distance >=width / 2){
            forceMultiplier  = 0f
        } else {
            forceMultiplier *= 0.2f
        }

        this.body.applyLinearImpulse(Vector2(Constants.PUSH_FORCE * forceMultiplier, 0f), body.position, true)

    }

    fun addCoin() {
        coins += 1
    }

    fun coinString(): String {
        return coins.toString()
    }

    fun pointString(): String {
        return "Points: " + points.toInt()
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