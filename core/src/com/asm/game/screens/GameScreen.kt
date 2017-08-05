package com.asm.game.screens

import com.asm.game.AsmGdxGame
import com.asm.game.core.GameRenderer
import com.asm.game.core.GameWorld
import com.asm.game.utils.Constants
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeType
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen

class GameScreen(val mGame: AsmGdxGame) : KtxScreen {

    lateinit var mGameRenderer:GameRenderer
    lateinit var mGameWorld:GameWorld
    lateinit var mCamera: OrthographicCamera
    lateinit var mViewport: FitViewport
    lateinit var mStage: Stage
    lateinit var bitmapFont: BitmapFont
    lateinit var bitmapFontSmall: BitmapFont

    init {
        initScreen()
        initCore()
    }


    private fun initCore() {
        mGameWorld = GameWorld(mGame, this)
        mGameRenderer = GameRenderer(mGame, this, mGameWorld)
    }

    private fun initScreen() {
        mCamera = OrthographicCamera()
        mCamera.setToOrtho(false)
        mGame.mSpriteBatch.projectionMatrix = mCamera.combined
        mGame.mShapeRenderer.projectionMatrix = mCamera.combined
        mViewport = FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, mCamera)
        bitmapFont = mGame.mAssetLoader.fontBig
        bitmapFontSmall = mGame.mAssetLoader.fontSmall
        mStage = Stage(mViewport)
        Gdx.input.inputProcessor = mStage
    }

    override fun dispose() {
        super.dispose()
    }

    override fun hide() {
        super.hide()
    }

    override fun pause() {
        super.pause()
    }

    override fun render(delta: Float) {
        super.render(delta)
        if (mGame.mAssetLoader.mAssetManager.update()) {
            mGameWorld.update(delta)
            mGameRenderer.render(delta)

            mStage.draw()
            mStage.act()
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }

    override fun resume() {
        super.resume()
    }

    override fun show() {
        super.show()
    }
}