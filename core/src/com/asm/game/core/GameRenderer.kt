package com.asm.game.core

import com.asm.game.AsmGdxGame
import com.asm.game.objects.CoinAnimation
import com.asm.game.screens.GameScreen
import com.asm.game.utils.Constants
import com.asm.game.utils.GameColors
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.TimeUtils
import com.sun.corba.se.impl.orbutil.closure.Constant
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const


class GameRenderer(val mGame: AsmGdxGame, val mGameScreen: GameScreen, val mGameWorld: GameWorld) {
    var debugRenderer: Box2DDebugRenderer = Box2DDebugRenderer()
    var debugMatrix: Matrix4 = mGameScreen.mCamera.combined.cpy().scale(Constants.BOX_TO_WORLD, Constants.BOX_TO_WORLD, 1F)
    val coinAnim: CoinAnimation = CoinAnimation(mGame.mAssetLoader)
    var stateTime: Float = 0f
    lateinit var coinSprite: Sprite

    init {
        coinSprite = Sprite(coinAnim.coinAnimation.getKeyFrame(0f))
        coinSprite.setPosition(35f, Constants.GAME_HEIGHT - 44f)
        coinSprite.setSize(42f, 42f)
    }

    fun render(delta: Float) {
        Gdx.gl.glClearColor(0F, 0F, 0F, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        Gdx.gl.glEnable(GL20.GL_BLEND)
        mGame.mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        mGame.mShapeRenderer.color = GameColors.GAME_BACKGROUND
        mGame.mShapeRenderer.rect(0F, 0F, Constants.GAME_WIDTH, Constants.GAME_HEIGHT)
        mGame.mShapeRenderer.end()

    renderDebug()
        stateTime += delta
        coinSprite.setRegion(coinAnim.coinAnimation.getKeyFrame(stateTime, true))

        mGame.mSpriteBatch.begin()

        mGameWorld.background.layers.forEach { it.sprites.forEach { it.draw(mGame.mSpriteBatch) } }
        mGameWorld.spawner.objects.forEach { it.sprite.draw(mGame.mSpriteBatch) }
        mGameWorld.objects.forEach { it.sprite.draw(mGame.mSpriteBatch) }


        renderGravityWarning()

        renderCoins()
        renderPoints()

        coinSprite.draw(mGame.mSpriteBatch)
        println(coinSprite.x.toString() + "    " + coinSprite.y)
        mGame.mSpriteBatch.end()

        renderGravity()

        //debugRenderer.render(mGameWorld.physicsWorld.world, debugMatrix)


    }

    private fun renderPoints() {
        mGameScreen.bitmapFont.draw(mGame.mSpriteBatch, mGameWorld.player.pointString(), Constants.GAME_WIDTH - this.calculateWidth(mGameWorld.player.pointString()), Constants.GAME_HEIGHT - 10)

    }

    private fun renderDebug(){
        mGame.mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        mGame.mShapeRenderer.color = Color(1f,0f,0f,1f)
        mGame.mShapeRenderer.rect(Constants.GAME_WIDTH / 2, 0f,20f,Constants.GAME_HEIGHT)
        mGame.mShapeRenderer.rect(Constants.GAME_WIDTH / 5, 0f,20f,Constants.GAME_HEIGHT)
        mGame.mShapeRenderer.end()
    }

    private fun renderCoins() {
        mGameScreen.bitmapFont.draw(mGame.mSpriteBatch, "×" + mGameWorld.player.coinString(), 70f, Constants.GAME_HEIGHT - 7)
    }

    private fun renderGravityWarning() {
        mGameScreen.bitmapFontBig.setColor(mGameWorld.coinTextColor)
        mGameScreen.bitmapFontSmall.setColor(mGameWorld.coinTextColor)

        mGameScreen.bitmapFontBig.draw(mGame.mSpriteBatch, mGameWorld.gravString, Constants.GAME_WIDTH / 2 - 50, Constants.GAME_HEIGHT / 2 + 140)
        mGameScreen.bitmapFontSmall.draw(mGame.mSpriteBatch, mGameWorld.gravTextString, Constants.GAME_WIDTH / 2 -50, Constants.GAME_HEIGHT / 2 + 160f)
        mGameScreen.bitmapFontBig.setColor(Color(1f,1f,1f,1f))
        mGameScreen.bitmapFontSmall.setColor(Color(1f,1f,1f,1f))
    }

    fun renderGravity() {
        mGame.mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        mGame.mShapeRenderer.color = GameColors.GRAVITY_BAR

        mGame.mShapeRenderer.rect(mGameWorld.gravityHandler.getX(), mGameWorld.gravityHandler.getY(), mGameWorld.gravityHandler.sizeX(), mGameWorld.gravityHandler.sizeY())
        mGame.mShapeRenderer.color = GameColors.GRAVITY_BAR_MID

        mGame.mShapeRenderer.rect(mGameWorld.gravityHandler.getX() + mGameWorld.gravityHandler.sizeX(), mGameWorld.gravityHandler.getY() - 15, 60f, 30f)
        mGame.mShapeRenderer.end()

        mGame.mSpriteBatch.begin()
        mGameScreen.bitmapFontSmall.draw(mGame.mSpriteBatch, mGameWorld.gravityHandler.currentGravity.toString(), mGameWorld.gravityHandler.getX() + mGameWorld.gravityHandler.sizeX() + 10, mGameWorld.gravityHandler.getY() + 10f)
        mGame.mSpriteBatch.end()
    }

    fun calculateWidth(string: String): Float {
        val width = 20f
        return string.length * width
    }

}