package com.asm.game.core

import com.asm.game.AsmGdxGame
import com.asm.game.objects.Background
import com.asm.game.objects.GameObject
import com.asm.game.objects.Player
import com.asm.game.screens.GameScreen
import com.asm.game.utils.Constants
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import ktx.collections.gdxListOf

class GameWorld(val mGame: AsmGdxGame, val mGameScreen: GameScreen) {

    lateinit var background: Background
    lateinit var player: Player
    lateinit var physicsWorld: PhysicsWorld

    var objects = gdxListOf<GameObject>()

    init {
        initWorld()
        createObjects()
    }

    private fun initWorld() {
        physicsWorld = PhysicsWorld(this)
    }

    private fun createObjects() {
        createMap()
        createPlayer()
        createBackground()
    }

    private fun createBackground(){
        background = Background(mGame, Constants.DEFAULT_SPEED)

    }

    private fun createPlayer() {
        val playerTexture: Texture = Texture(Gdx.files.internal("GameScreen/Squid.png"))
        val playerBody: Body = physicsWorld.createDynamicBody(Vector2(playerTexture.width.toFloat(), playerTexture.height.toFloat()), Vector2(500f, 500f), 30f, Constants.PLAYER_PHYSICS_TAG)
        player = Player(playerBody, playerTexture)
        objects + player
    }

    fun createMap() {
        physicsWorld.createStaticBody(Vector2(1024F, 69F), Vector2(Constants.GAME_WIDTH / 2, 32F), 40F, Constants.BORDER_BOTTOM_PHYSICS_TAG)
        physicsWorld.createStaticBody(Vector2(1024F, 69F), Vector2(Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT - 32F), 40F, Constants.BORDER_TOP_PHYSICS_TAG)

    }

    fun update(delta: Float) {
        physicsWorld.update(delta)
        background.update(delta)
        objects.forEach { it.update(delta) }
    }
}

