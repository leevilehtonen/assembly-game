package com.asm.game.screens

import com.asm.game.AsmGdxGame
import com.asm.game.utils.Constants
import com.asm.game.utils.GameColors
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen


class StartScreen(var mGame: AsmGdxGame) : KtxScreen {

    lateinit var mCamera: OrthographicCamera
    lateinit var mViewport: FitViewport
    lateinit var mStage: Stage
    lateinit var mTable: Table

    init {
        initScreen()
        createScene()
    }

    private fun initScreen() {
        mCamera = OrthographicCamera()
        mCamera.setToOrtho(false)
        mGame.mSpriteBatch.projectionMatrix = mCamera.combined
        mGame.mShapeRenderer.projectionMatrix = mCamera.combined
        mViewport = FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, mCamera)
        mStage = Stage(mViewport)
        Gdx.input.inputProcessor = mStage
        mTable = Table()
        mTable.setPosition(Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT / 2)
    }

    fun createScene() {
        var mStartLogoTexture: Texture = Texture(Gdx.files.internal("StartScreen/logo.png"))
        mStartLogoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        var mStartLogoImage: Image = Image(mStartLogoTexture)
        mTable.add(mStartLogoImage).size(mStartLogoImage.width, mStartLogoImage.height).center().colspan(3)
        mTable.row()


        var playButtonStyle: Button.ButtonStyle = Button.ButtonStyle()
        var aboutButtonStyle: Button.ButtonStyle = Button.ButtonStyle()
        var quitButtonStyle: Button.ButtonStyle = Button.ButtonStyle()
        playButtonStyle.up = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("StartScreen/PlayButton.png"))))
        aboutButtonStyle.up = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("StartScreen/AboutButton.png"))))
        quitButtonStyle.up = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("StartScreen/QuitButton.png"))))
        var playBtn: Button = Button(playButtonStyle)
        var aboutBtn: Button = Button(aboutButtonStyle)
        var quitBtn: Button = Button(quitButtonStyle)


        playBtn.addListener(object : ClickListener() {

            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("Play clicked")
                mGame.addScreen(GameScreen(mGame))
                mGame.setScreen<GameScreen>()
                super.clicked(event, x, y)
            }
        })

        aboutBtn.addListener(object : ClickListener() {

            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("About clicked")
                super.clicked(event, x, y)
            }
        })

        quitBtn.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Gdx.app.exit()
                super.clicked(event, x, y)
            }
        })

        mTable.add(playBtn).pad(10F)
        mTable.add(aboutBtn).pad(10F)
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


        mGame.mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        mGame.mShapeRenderer.color = GameColors.START_BACKGROUND
        mGame.mShapeRenderer.rect(0F, 0F, Constants.GAME_WIDTH, Constants.GAME_HEIGHT)
        mGame.mShapeRenderer.end()

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
        super.show()
    }
}