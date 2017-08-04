package com.asm.game.objects

import com.asm.game.utils.Constants
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

class Player(val body: Body, val texture: Texture) : PhysicsGameObject(texture) {
    var jumped: Boolean = false
    var rotated: Boolean = false
    var rotate: Boolean = false
    var flipped: Boolean = false
    var flipflop: Boolean = false
    override fun update(delta: Float) {
        sprite.setPosition(Constants.BOX_TO_WORLD * body.position.x - sprite.width / 2, Constants.BOX_TO_WORLD * body.position.y - sprite.height / 2)

        if (Gdx.input.isTouched and !jumped) {
            jump()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) and !flipped) {
            gravityFlip()
        }
        /*
        if(sprite.rotation < 180 && rotate) {
            sprite.rotation += delta * 500f;
        }
        */
    }

    fun gravityFlip() {
        body.world.gravity = body.world.gravity.cpy().scl(-1f)
        println("flipped")
        flipped = true
        flipflop = !flipflop
    }

    fun jump() {
        if (flipflop) {
            body.applyLinearImpulse(Vector2(0F, -Constants.JUMP_AMOUNT), body.position, true)
        } else {
            body.applyLinearImpulse(Vector2(0F, Constants.JUMP_AMOUNT), body.position, true)
        }
        //body.world.gravity = body.world.gravity.cpy().scl(-1f);

        jumped = true
    }
}