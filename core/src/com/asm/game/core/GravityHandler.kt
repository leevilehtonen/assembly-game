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
    val gBarValue = gdxMapOf(-10f to 75f, -15f to 100f, -25f to 150f, 10f to 75f, 15f to 100f, 25f to 150f)
    var currentGBar = 75f
    var lastGBar = currentGBar
    var currentGravity = mGameWorld.physicsWorld.world.gravity.y
    val sX = 50f
    var sY = currentGBar
    var mX = 40f
    var mY = 240f
    var lastGravity = currentGravity
    var bX = mX
    var bY = mY

    var counter = 0f
    var tweenSpeed = 30f
    var shouldTween = false

    fun getX(): Float {
        return mX
    }

    fun getSizeY(): Float {
        if (sY < 0) return sY else return sY
    }

    fun getSizeX(): Float {
        return sX
    }

    fun getY(): Float {
        if (currentGravity > 0f) {
            return mY
        } else {
            // return bY
            return mY - sY
        }
    }

    fun topX() {

    }

    fun topY() {

    }

    override fun update(delta: Float) {
        if (TimeUtils.timeSinceNanos(spawnTimer) > Constants.GRAVITY_INTERVAL) {
            changeGravity()
            spawnTimer = TimeUtils.nanoTime()
        }

        tweenSpeed = Math.abs(lastGBar - currentGBar) * 10f

        if (currentGravity > 0) {
            bY = mY
            if (currentGBar > lastGBar) {
                sY += tweenSpeed * delta
                if (sY >= currentGBar) {

                    sY = currentGBar
                }
            } else {
                sY -= tweenSpeed * delta
                if (sY <= currentGBar) {

                    sY = currentGBar
                } else if(sY <= 0){
                    sY += tweenSpeed * delta
                    bY -= tweenSpeed * delta
                    if (sY >= currentGBar) {
                        sY = currentGBar
                        bY = currentGBar + mY

                    }
                }
            }

        } else if (currentGravity < 0) {
            if (currentGBar < lastGBar) {
                println("current on pienempi" + sY + "   " + bY)
                sY -= tweenSpeed * delta
                bY += tweenSpeed * delta

                if (sY <= currentGBar) {

                    sY = currentGBar
                    bY = currentGBar + mY
                } else if(sY <= 0){
                    sY += tweenSpeed * delta
                    if (sY >= currentGBar) {

                        sY = currentGBar
                    }
                }

            }
        } else {
            println("current on isompi" + sY + "   " + bY)

            sY += tweenSpeed * delta
            bY -= tweenSpeed * delta
            if (sY >= currentGBar) {
                sY = currentGBar
                bY = currentGBar + mY

            } else if(sY >= currentGBar && lastGravity > 0){
                sY = tweenSpeed * delta
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
        println(mGameWorld.physicsWorld.world.gravity)
    }

}