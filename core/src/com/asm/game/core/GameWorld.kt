package com.asm.game.core

import com.asm.game.AsmGdxGame
import com.asm.game.objects.Background
import com.asm.game.objects.GameObject
import com.asm.game.objects.Player
import com.asm.game.objects.PlayerAnimation
import com.asm.game.screens.GameScreen
import com.asm.game.utils.Constants
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.TimeUtils
import ktx.collections.gdxListOf

class GameWorld(val mGame: AsmGdxGame, val mGameScreen: GameScreen) {

    lateinit var background: Background
    lateinit var player: Player
    lateinit var physicsWorld: PhysicsWorld
    lateinit var spawner: Spawner
    lateinit var gravityHandler: GravityHandler
    var speed: Float = Constants.DEFAULT_SPEED
    var counterSpeed: Long = 0L
    var gravTextString: String = "Time until change: "
    var gravString: String = ""

    var objects = gdxListOf<GameObject>()

    init {
        initWorld()
        createObjects()
    }

    private fun initWorld() {
        physicsWorld = PhysicsWorld(this)
        gravityHandler = GravityHandler(this)
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
        val animation: PlayerAnimation = PlayerAnimation(mGame.mAssetLoader)
        var playerTexture = animation.walkAnimation.getKeyFrame(0f)
        val playerBody: Body = physicsWorld.createPlayer(Vector2(500f, 500f), 200f, Constants.PLAYER_PHYSICS_TAG)
        player = Player(playerBody, playerTexture, animation)
        objects + player
    }

    fun createMap() {
        physicsWorld.createStaticBody(Vector2(2048F, 69F), Vector2(Constants.GAME_WIDTH / 2, 32F), 40F, Constants.BORDER_BOTTOM_PHYSICS_TAG)
        physicsWorld.createStaticBody(Vector2(2045F, 69F), Vector2(Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT - 32F), 40F, Constants.BORDER_TOP_PHYSICS_TAG)
        physicsWorld.createStaticBody(Vector2(60f, 576F), Vector2(-30f, Constants.GAME_HEIGHT / 2f), 40F, Constants.BORDER_SIDE_PHYSICS_TAG)

        spawner = Spawner(this)
        counterSpeed = TimeUtils.nanoTime()
    }

    fun update(delta: Float) {
        updateGravityString()
        physicsWorld.update(delta)
        background.update(delta)
        objects.forEach { it.update(delta) }
        spawner.update(delta)
        gravityHandler.update(delta)

        if (TimeUtils.timeSinceNanos(counterSpeed) > Constants.SPEEDUPDATE_TARGET_TIME) {
            updateSpeed()
            counterSpeed = TimeUtils.nanoTime()
        }
    }

    fun updateGravityString(){
        gravString = ((5 - TimeUtils.timeSinceNanos(gravityHandler.spawnTimer) / 1000000000f)).toString().take(3)
    }



    fun updateSpeed() {
        this.speed += 10f
        this.background.updateSpeed(speed)
        this.spawner.updateSpeed(speed)
    }
}

