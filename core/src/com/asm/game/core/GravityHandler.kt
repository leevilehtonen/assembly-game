package com.asm.game.core

import com.asm.game.objects.Obstacle
import com.asm.game.objects.Updateable
import com.asm.game.utils.Constants
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import ktx.collections.gdxArrayOf
import ktx.collections.gdxListOf

class GravityHandler(val mGameWorld: GameWorld): Updateable {

    var spawnTimer: Long = 0L
    var speed: Float = Constants.DEFAULT_SPEED
    val gValues = gdxArrayOf<Float>(-5f, -10f, -15f, -25f)

    override fun update(delta: Float) {
        if (TimeUtils.timeSinceNanos(spawnTimer) > Constants.GRAVITY_INTERVAL) {
            changeGravity()
            spawnTimer = TimeUtils.nanoTime()
        }
    }

    fun changeGravity() {
        val gIndex = MathUtils.random.nextInt(gValues.size)
        val inverted = MathUtils.random.nextBoolean()
        val gravity = if (inverted) gValues[gIndex] else (-gValues[gIndex])
        mGameWorld.physicsWorld.world.gravity = Vector2(0f, gravity)
        println(mGameWorld.physicsWorld.world.gravity)
    }
}