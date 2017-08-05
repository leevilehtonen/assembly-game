package com.asm.game.core

import com.asm.game.objects.Updateable
import com.asm.game.utils.Constants
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import ktx.collections.gdxArrayOf
import ktx.collections.gdxMapOf

class GravityHandler(val mGameWorld: GameWorld): Updateable {

    var spawnTimer: Long = TimeUtils.nanoTime()
    var speed: Float = Constants.DEFAULT_SPEED
    val gValues = gdxArrayOf<Float>(-10f, -15f, -25f)
    val gBarValue = gdxMapOf(-10f to 75f, -15f to 100f, -25f to 150f)
    var currentGBar = 75f
    var lastGBar = currentGBar


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
        lastGBar = currentGBar
        currentGBar = gBarValue[gravity]
        println(mGameWorld.physicsWorld.world.gravity)
    }
}