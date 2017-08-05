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
    val obstacleTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/Spikes.png")))
    val obstacleInvTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/SpikesInv.png")))
    val bombTexture: TextureRegion = TextureRegion(Texture(Gdx.files.internal("GameScreen/Bomb.png")))
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
        val type = MathUtils.random.nextInt(3)


        if (type == 0) {
            var obstacleBody = mGameWorld.physicsWorld.createStaticBody(Vector2(obstacleTexture.regionWidth.toFloat(), obstacleTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, Constants.GAME_HEIGHT - (obstacleTexture.regionHeight / 2 + 69f)), 100f, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, obstacleInvTexture, speed, 0f)
            return obstacle
        } else if (type == 1) {
            var obstacleBody = mGameWorld.physicsWorld.createStaticBody(Vector2(obstacleTexture.regionWidth.toFloat(), obstacleTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, obstacleTexture.regionHeight / 2 + 69f), 100f, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, obstacleTexture, speed, 0f)
            return obstacle
        } else {
            var obstacleBody = mGameWorld.physicsWorld.createDynamicBody(Vector2(bombTexture.regionWidth.toFloat(), bombTexture.regionHeight.toFloat()), Vector2(xBase + xDiff, Constants.GAME_HEIGHT / 2 - bombTexture.regionHeight / 2), 100f, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, bombTexture, speed, 0f)
            return obstacle
        }
    }

    fun updateSpeed(speed: Float) {
        this.speed = speed
        objects.forEach { it.speed = speed }
    }

}