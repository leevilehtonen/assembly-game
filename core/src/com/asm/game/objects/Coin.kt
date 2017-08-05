package com.asm.game.objects

import com.badlogic.gdx.graphics.g2d.TextureRegion

class Coin(val x: Float, val y: Float, val texture: TextureRegion): PhysicsGameObject(texture) {
    val value:Int = 1
    override fun update(delta: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}