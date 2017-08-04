package com.asm.game.screens

import com.asm.game.AsmGdxGame
import com.asm.game.utils.Constants
import com.asm.game.utils.GameColors
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen


class SplashScreen(val mGame: AsmGdxGame) : KtxScreen {

    lateinit var mCamera: OrthographicCamera
    lateinit var mViewport: FitViewport
    lateinit var mStage: Stage

    init {
        initScreen()
        createScene()
    }

    fun initScreen() {
        mCamera = OrthographicCamera()
        mCamera.setToOrtho(false)
        mGame.mSpriteBatch.projectionMatrix = mCamera.combined
        mGame.mShapeRenderer.projectionMatrix = mCamera.combined
        mViewport = FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, mCamera)
        mStage = Stage(mViewport)
    }

    fun createScene() {
        var mSplashLogoTexture: Texture = Texture(Gdx.files.internal("SplashScreen/logo.png"))
        mSplashLogoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        var mSplashLogoImage: Image = Image(mSplashLogoTexture)
        mSplashLogoImage.setSize(Constants.GAME_HEIGHT * 0.4f, Constants.GAME_HEIGHT * 0.4f)
        mSplashLogoImage.setPosition((Constants.GAME_WIDTH - mSplashLogoImage.width) / 2, (Constants.GAME_HEIGHT - mSplashLogoImage.height) / 2)

        val mAlphaInit: AlphaAction = AlphaAction()
        mAlphaInit.alpha = 0F

        val mAlphaInAction: AlphaAction = AlphaAction()
        mAlphaInAction.alpha = 1f
        mAlphaInAction.duration = 2f
        mAlphaInAction.interpolation = Interpolation.fade

        val mDelayAction: DelayAction = DelayAction()
        mDelayAction.duration = 0.1f

        val mAlphaOutAction: AlphaAction = AlphaAction()
        mAlphaOutAction.alpha = 0.1f
        mAlphaOutAction.duration = 1f
        mAlphaOutAction.interpolation = Interpolation.fade

        val mChangeScreenAction: Action = object : Action() {
            override fun act(delta: Float): Boolean {
                mGame.addScreen(StartScreen(mGame))
                mGame.setScreen<StartScreen>()
                return true
            }
        }

        val mSequenceAction: SequenceAction = SequenceAction(mAlphaInit, mAlphaInAction, mDelayAction, mAlphaOutAction, mChangeScreenAction)
        mSplashLogoImage.addAction(mSequenceAction)
        mStage.addActor(mSplashLogoImage)
    }

    override fun dispose() {
        super.dispose()
        mStage.dispose()

    }

    override fun render(delta: Float) {
        super.render(delta)

        Gdx.gl.glClearColor(0F, 0F, 0F, 1F)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)


        mGame.mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        mGame.mShapeRenderer.color = GameColors.SPLASH_BACKGROUND
        mGame.mShapeRenderer.rect(0F, 0F, Constants.GAME_WIDTH, Constants.GAME_HEIGHT)
        mGame.mShapeRenderer.end()

        mStage.act(delta)
        mStage.draw()
    }

    override fun resume() {
        super.resume()
    }

    override fun show() {
        super.show()
    }
}