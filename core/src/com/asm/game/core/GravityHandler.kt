package com.asm.game.core

import com.asm.game.objects.Updateable
import com.asm.game.utils.Constants
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import ktx.collections.gdxArrayOf
import ktx.collections.gdxMapOf

class GravityHandler(val mGameWorld: GameWorld) : Updateable {

    var spawnTimer: Long = TimeUtils.nanoTime()
    var speed: Float = Constants.DEFAULT_SPEED
    val gValues = gdxArrayOf<Float>(-10f, -15f, -25f)
    //val gBarValue = gdxMapOf(-25f to 0f, -15f to 20f, -10f to 40f, 10f to 60f, 15f to 80f, 25f to 100f)
    val gBarValue = gdxMapOf(-25f to -20f, -15f to -15f, -10f to -5f, 10f to 5f, 15f to 10f, 25f to 20f)

    var currentGBar = 75f
    var lastGBar = currentGBar
    var currentGravity = mGameWorld.physicsWorld.world.gravity.y
    val sX = 15f
    var sY = gBarValue[10f]
    var mX = 0f//Constants.GAME_WIDTH /2 + 100f
    var mY = Constants.GAME_HEIGHT / 2
    var lastGravity = currentGravity
    var bX = mX
    var bY = mY

    var counter = 0f
    var tweenSpeed = 30f
    var shouldTween = false

    fun getX(): Float {
        return mX
    }

    fun getY(): Float {
        return mY
    }

    fun sizeX(): Float {
        return sX
    }

    fun sizeY(): Float {
        return sY * 10
    }

    override fun update(delta: Float) {
        if (TimeUtils.timeSinceNanos(spawnTimer) > Constants.GRAVITY_INTERVAL) {
            changeGravity()
            spawnTimer = TimeUtils.nanoTime()
        }

        //tweenSpeed = Math.abs(lastGBar - currentGBar) * 1000000f
        tweenSpeed = 100f
        if (shouldTween) {

            if (currentGBar > lastGBar) {
             //  println("tweenin to: " + currentGBar)

                sY += tweenSpeed * delta
                if (sY >= currentGBar) {
                    sY = currentGBar
                    shouldTween = false
                }
            } else if (currentGBar < lastGBar) {
              //  println("tweenin")

                sY -= tweenSpeed * delta
                if (sY <= currentGBar) {
                    sY = currentGBar
                    shouldTween = false
                }
            }
        }


    }


    fun changeGravity() {
        shouldTween = true
        val gIndex = MathUtils.random.nextInt(gValues.size)
        val inverted = MathUtils.random.nextBoolean()
        val gravity = if (inverted) gValues[gIndex] else (-gValues[gIndex])
        mGameWorld.physicsWorld.world.gravity = Vector2(0f, gravity)
        lastGBar = currentGBar
        currentGBar = gBarValue[gravity]
        lastGravity = currentGravity
        currentGravity = gravity
    }
}