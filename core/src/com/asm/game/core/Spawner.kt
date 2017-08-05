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
import ktx.collections.gdxListOf

class Spawner(val mGameWorld: GameWorld) : Updateable{

    var spawnTimer: Long = 0L
    var objects = gdxListOf<Obstacle>()
    val spikesTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/Spikes.png")))
    val spikesInvTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/SpikesInv.png")))
    val bombTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/Bomb.png")))
    val obstacleTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/Obstacle.png")))

    var speed: Float = Constants.DEFAULT_SPEED



    override fun update(delta: Float) {
        if(TimeUtils.timeSinceNanos(spawnTimer) > Constants.SPAWNER_TARGET_TIME) {
            objects + spawnObject()
            spawnTimer = TimeUtils.nanoTime()
        }
        objects.iterate { gameObject, iterator ->
            gameObject.update(delta)
            if (gameObject.sprite.x < -gameObject.sprite.width) {
                iterator.remove()
            }
        }
    }


    fun spawnObject(): Obstacle {
        val xBase = Constants.GAME_WIDTH + 300f
        val xDiff = MathUtils.random.nextInt(400)-200f
        val type = MathUtils.random.nextInt(6)
        val density = MathUtils.random.nextFloat() * 100f + 10000f

        if (type == 0) {
            var obstacleBody = mGameWorld.physicsWorld.createStaticBody(Vector2(spikesTexture.regionWidth.toFloat(), spikesTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, Constants.GAME_HEIGHT - (spikesTexture.regionHeight / 2 + 69f)), 100f, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, spikesInvTexture, speed, 0f)
            return obstacle
        } else if (type == 1) {
            var obstacleBody = mGameWorld.physicsWorld.createStaticBody(Vector2(spikesTexture.regionWidth.toFloat(), spikesTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, spikesTexture.regionHeight / 2 + 69f), 100f, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, spikesTexture, speed, 0f)
            return obstacle
        } else if (type == 2 or 3) {
            var obstacleBody = mGameWorld.physicsWorld.createDynamicBody(Vector2(bombTexture.regionWidth.toFloat(), bombTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, Constants.GAME_HEIGHT / 2 - bombTexture.regionHeight / 2), density, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, bombTexture, speed, 0f)
            return obstacle
        } else {
            var obstacleBody = mGameWorld.physicsWorld.createDynamicBody(Vector2(obstacleTexture.regionWidth.toFloat(), obstacleTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, Constants.GAME_HEIGHT / 2 - obstacleTexture.regionHeight / 2), density, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, obstacleTexture, speed, 0f)
            return obstacle
        }
    }

    fun updateSpeed(speed: Float) {
        this.speed = speed
        objects.forEach { it.speed = speed }
    }

}