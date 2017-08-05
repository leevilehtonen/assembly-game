package com.asm.game.core

import com.asm.game.objects.*
import com.asm.game.utils.Constants
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import ktx.collections.gdxArrayOf

class Spawner(val mGameWorld: GameWorld) : Updateable {

    var spawnTimer: Long = 0L
    var objects = gdxArrayOf<PhysicsGameObject>()
    var objectsToRemove = gdxArrayOf<PhysicsGameObject>()

    val spikesTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/Spikes.png")))
    val spikesInvTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/SpikesInv.png")))
    val bombTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/Bomb.png")))
    val obstacleTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/Obstacle.png")))
    val animation: CoinAnimation = CoinAnimation(mGameWorld.mGame.mAssetLoader)
    var coinTexture = animation.coinAnimation.getKeyFrame(0f)

    var speed: Float = Constants.DEFAULT_SPEED


    override fun update(delta: Float) {


        if (TimeUtils.timeSinceNanos(spawnTimer) > Constants.SPAWNER_TARGET_TIME) {
            spawnObject()
            spawnTimer = TimeUtils.nanoTime()
        }
        println("Objects count:" + objects.size)
        objects.forEach {
            it.update(delta)
            if (it.sprite.x < -it.sprite.width) {
                if (!objectsToRemove.contains(it)) {
                    objectsToRemove.add(it)
                }
            }
        }

        if (!mGameWorld.physicsWorld.world.isLocked) {
            objectsToRemove.forEach {
                it.body.userData = null
                mGameWorld.physicsWorld.world.destroyBody(it.body)
                objects.removeValue(it, true)
            }
            objectsToRemove.clear()
        }


    }

    fun deleteObject(physicsGameObject: PhysicsGameObject) {
        println("dell")
        if (!objectsToRemove.contains(physicsGameObject)) {
            objectsToRemove.add(physicsGameObject)
        }
    }


    fun spawnObject() {
        val xBase = Constants.GAME_WIDTH + 300f
        val xDiff = MathUtils.random.nextInt(400) - 200f
        val type = MathUtils.random.nextInt(9)
        val density = MathUtils.random.nextFloat() * 100f + 10000f

        if (type == 0) {
            var obstacleBody = mGameWorld.physicsWorld.createStaticBody(Vector2(spikesTexture.regionWidth.toFloat(), spikesTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, Constants.GAME_HEIGHT - (spikesTexture.regionHeight / 2 + 69f)), 100f, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, spikesInvTexture, speed, this)
            obstacleBody.userData = obstacle
            objects.add(obstacle)
        } else if (type == 1) {
            var obstacleBody = mGameWorld.physicsWorld.createStaticBody(Vector2(spikesTexture.regionWidth.toFloat(), spikesTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, spikesTexture.regionHeight / 2 + 69f), 100f, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, spikesTexture, speed, this)
            obstacleBody.userData = obstacle
            objects.add(obstacle)
        } else if (type == 2 or 3) {
            var obstacleBody = mGameWorld.physicsWorld.createDynamicBody(Vector2(bombTexture.regionWidth.toFloat(), bombTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, Constants.GAME_HEIGHT / 2 - bombTexture.regionHeight / 2), density, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, bombTexture, speed, this)
            obstacleBody.userData = obstacle
            objects.add(obstacle)
        } else if (type == 4 or 5) {
            var obstacleBody = mGameWorld.physicsWorld.createDynamicBody(Vector2(obstacleTexture.regionWidth.toFloat(), obstacleTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, Constants.GAME_HEIGHT / 2 - obstacleTexture.regionHeight / 2), density, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, obstacleTexture, speed, this)
            obstacleBody.userData = obstacle
            objects.add(obstacle)
        } else {
            if (MathUtils.random.nextBoolean()) {
                objects.add(createCoin(xBase + xDiff, 2 * Constants.GAME_HEIGHT / 3 - coinTexture.regionHeight / 2))
            }
            if (MathUtils.random.nextBoolean()) {
                objects.add(createCoin(xBase + xDiff, Constants.GAME_HEIGHT / 2 - coinTexture.regionHeight / 2))
            }
            if (MathUtils.random.nextBoolean()) {
                objects.add(createCoin(xBase + xDiff, Constants.GAME_HEIGHT / 3 - coinTexture.regionHeight / 2))

            }
        }
    }

    fun createCoin(xPos: Float, yPos: Float): Coin {
        var coinBody = mGameWorld.physicsWorld.createStaticBody(Vector2(48f, 48f), Vector2(xPos, yPos), 100f, Constants.COIN_PHYSICS_TAG)
        var coin: Coin = Coin(coinBody, coinTexture, speed, animation, this)
        coinBody.userData = coin
        return coin
    }

    fun updateSpeed(speed: Float) {
        this.speed = speed
        objects.forEach { it.speed = speed }
    }

}