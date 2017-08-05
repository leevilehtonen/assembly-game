package com.asm.game.core

import com.asm.game.objects.GameObject
import com.asm.game.objects.Obstacle
import com.asm.game.objects.Updateable
import com.asm.game.utils.Constants
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import ktx.collections.gdxListOf

class Spawner(val mGameWorld: GameWorld) : Updateable{

    var spawnTimer: Long = 0L
    var objects = gdxListOf<GameObject>()
    val obstacleTexture:Texture = Texture(Gdx.files.internal("GameScreen/Spikes.png"))


    override fun update(delta: Float) {
        if(TimeUtils.timeSinceNanos(spawnTimer) > Constants.SPAWNER_TARGET_TIME) {
            objects + spawnObject()
            spawnTimer = TimeUtils.nanoTime()
        }
        objects.forEach { gameObject: GameObject ->
            gameObject.update(delta);
        }

    }


    fun spawnObject():GameObject{
        val xBase = Constants.GAME_WIDTH + 300f
        val xDiff = MathUtils.random.nextInt(400)-200f
        val top = MathUtils.random.nextBoolean();

        if(top) {
            var obstacleBody = mGameWorld.physicsWorld.createStaticBody(Vector2(obstacleTexture.width.toFloat(), obstacleTexture.height.toFloat()), Vector2(xBase + xDiff, obstacleTexture.height / 2 + 69f), 100f, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, obstacleTexture, Constants.DEFAULT_SPEED, 14f)
            return obstacle;
        } else {
            var obstacleBody = mGameWorld.physicsWorld.createStaticBody(Vector2(obstacleTexture.width.toFloat(), obstacleTexture.height.toFloat()), Vector2(xBase + xDiff, obstacleTexture.height / 2 + 69f), 100f, Constants.OBSTACLE_PHYSICS_TAG)
            var obstacle: Obstacle = Obstacle(obstacleBody, obstacleTexture, Constants.DEFAULT_SPEED, 14f)
            return obstacle;
        }
    }

}