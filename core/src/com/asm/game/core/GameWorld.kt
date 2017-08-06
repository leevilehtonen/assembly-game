package com.asm.game.core

import com.asm.game.AsmGdxGame
import com.asm.game.objects.Background
import com.asm.game.objects.GameObject
import com.asm.game.objects.Player
import com.asm.game.objects.PlayerAnimation
import com.asm.game.screens.GameScreen
import com.asm.game.utils.Constants
import com.badlogic.gdx.graphics.Color
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
    var gravTextString: String = ""
    var gravString: String = ""
    var coinTextColor: Color = Color(1f,1f,1f,1f)
    var objects = gdxListOf<GameObject>()
    var pointMul = 1f

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
        playerBody.userData = player
        objects + player
    }

    fun createMap() {
        physicsWorld.createStaticBody(Vector2(2048F, 69F), Vector2(Constants.GAME_WIDTH / 2, 32F), 40F, Constants.BORDER_BOTTOM_PHYSICS_TAG, false)
        physicsWorld.createStaticBody(Vector2(2045F, 69F), Vector2(Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT - 32F), 40F, Constants.BORDER_TOP_PHYSICS_TAG, false)
        physicsWorld.createStaticBody(Vector2(60f, 1024F), Vector2(0f, Constants.GAME_HEIGHT / 2f), 40F, Constants.BORDER_SIDE_PHYSICS_TAG, false)

        spawner = Spawner(this)
        counterSpeed = TimeUtils.nanoTime()
    }

    fun update(delta: Float) {

        physicsWorld.update(delta)
        updateGravityString()
        updatePoints()
        background.update(delta)
        objects.forEach { it.update(delta) }
        spawner.update(delta)
        gravityHandler.update(delta)

        checkCoinColor()
        if (TimeUtils.timeSinceNanos(counterSpeed) > Constants.SPEEDUPDATE_TARGET_TIME) {
            pointMul += 2
            updateSpeed()
            counterSpeed = TimeUtils.nanoTime()
        }


    }

    fun updatePoints(){
        player.points += pointMul * 0.2f
    }

    fun checkCoinColor(){
        if(Constants.GRAVITY_INTERVAL / 1000000000 - TimeUtils.timeSinceNanos(gravityHandler.spawnTimer) / 1000000000f < 5f){
            coinTextColor = Color(1f,0.1f,0.1f,1f)
        } else {
            coinTextColor = Color(1f,1f,1f,1f)
        }
    }

    fun updateGravityString(){
        val value = (Constants.GRAVITY_INTERVAL / 1000000000f - TimeUtils.timeSinceNanos(gravityHandler.spawnTimer) / 1000000000f)
        if(value < 5) {
            gravString = value.toString().take(1) + "." + value.toString()[2]
            gravTextString = "Gravity Swaps!"
        } else {
            gravString = ""
            gravTextString = ""
        }
    }


    fun updateSpeed() {
        this.speed += 10f
        this.background.updateSpeed(speed)
        this.spawner.updateSpeed(speed)
    }
}

