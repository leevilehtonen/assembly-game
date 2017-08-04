package com.asm.game.core

import com.asm.game.objects.PhysicsGameObject
import com.asm.game.objects.Player
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import ktx.collections.gdxListOf
import java.util.*

class Spawner(val interval: Int, val mGameWorld: GameWorld) {

    val objects = gdxListOf<PhysicsGameObject>()

    fun spawnObject(){
        /*
        var obj: PhysicsGameObject
        val scale = 100
        val random = Math.random() * scale
        val playerTexture: Texture = Texture(Gdx.files.internal("GameScreen/Obstacle.png"))
        val playerBody: Body = mGameWorld.physicsWorld.createDynamicBody(Vector2(playerTexture.width.toFloat(), playerTexture.height.toFloat()), Vector2(500f, 500f), 30f)
        obj = Player(playerBody, playerTexture)

        objects.add(obj)
    */
    }

}