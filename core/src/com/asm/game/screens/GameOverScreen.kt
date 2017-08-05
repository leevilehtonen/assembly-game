package com.asm.game.screens

import com.asm.game.AsmGdxGame
import com.asm.game.utils.Constants
import com.asm.game.utils.GameColors
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle




class GameOverScreen(var mGame: AsmGdxGame) : KtxScreen {

    lateinit var mCamera: OrthographicCamera
    lateinit var mViewport: FitViewport
    lateinit var mStage: Stage
    lateinit var mTable: Table
    lateinit var bitmapFont: BitmapFont
    lateinit var bitmapFontSmall: BitmapFont


    init {
        initScreen()
        createScene()
    }

    private fun initScreen() {
        mCamera = OrthographicCamera()
        mCamera.setToOrtho(false)
        mGame.mSpriteBatch.projectionMatrix = mCamera.combined
        mGame.mShapeRenderer.projectionMatrix = mCamera.combined
        bitmapFont = mGame.mAssetLoader.fontBig
        bitmapFontSmall = mGame.mAssetLoader.fontSmall
        mViewport = FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, mCamera)
        mStage = Stage(mViewport)
        Gdx.input.inputProcessor = mStage
        mTable = Table()
        mTable.setPosition(Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT / 2)
    }

    fun createScene() {
        var largeLabelStyle:LabelStyle = LabelStyle(bitmapFont, Color.WHITE)
        var smallLabelStyle:LabelStyle = LabelStyle(bitmapFontSmall, Color.WHITE)
        var restartButtonStyle: Button.ButtonStyle = Button.ButtonStyle()
        var quitButtonStyle: Button.ButtonStyle = Button.ButtonStyle()
        restartButtonStyle.up = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("GameOverScreen/RestartButton.png"))))
        quitButtonStyle.up = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("GameOverScreen/QuitButton.png"))))


        var coinsLabel:Label = Label("Coins: 0", smallLabelStyle)
        var scoreLabel:Label = Label("Score: 0", smallLabelStyle)



        var restartBtn: Button = Button(restartButtonStyle)
        var quitBtn: Button = Button(quitButtonStyle)

        restartBtn.addListener(object : ClickListener() {

            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                mGame.addScreen(GameScreen(mGame))
                mGame.setScreen<GameScreen>()
                super.clicked(event, x, y)
            }
        })


        quitBtn.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Gdx.app.exit()
                super.clicked(event, x, y)
            }
        })
        mTable.add(coinsLabel)
        mTable.add(scoreLabel)
        mTable.add(restartBtn).pad(10F)
        mTable.add(quitBtn).pad(10F)
        mStage.addActor(mTable)


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

        Gdx.gl.glClearColor(0F, 0F, 0F, 1F)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glEnable(GL30.GL_BLEND)

        mStage.draw()
        mStage.act()

    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }

    override fun resume() {
        super.resume()
    }

    override fun show() {
        mGame.removeScreen<GameScreen>()
        super.show()
    }
}