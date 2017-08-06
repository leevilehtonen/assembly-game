package com.asm.game.screens

import com.asm.game.AsmGdxGame
import com.asm.game.objects.CoinAnimation
import com.asm.game.utils.Constants
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen


class GameOverScreen(var mGame: AsmGdxGame, var coins:Int, var score:Float) : KtxScreen {

    lateinit var mCamera: OrthographicCamera
    lateinit var mViewport: FitViewport
    lateinit var mStage: Stage
    lateinit var mTable: Table
    lateinit var bitmapFont: BitmapFont
    lateinit var bitmapFontSmall: BitmapFont
    lateinit var coinImage: Image

    val coinAnim: CoinAnimation = CoinAnimation(mGame.mAssetLoader)
    var stateTime: Float = 0f


    init {
        initScreen()
        createScene()
    }

    private fun initScreen() {
        mCamera = OrthographicCamera()
        mCamera.setToOrtho(false)
        mGame.mSpriteBatch.projectionMatrix = mCamera.combined
        mGame.mShapeRenderer.projectionMatrix = mCamera.combined
        bitmapFont = mGame.mAssetLoader.font
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

        var mStartLogoTexture: Texture = Texture(Gdx.files.internal("GameOverScreen/logo.png"))
        mStartLogoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        var mStartLogoImage: Image = Image(mStartLogoTexture)

        coinImage = Image(coinAnim.coinAnimation.getKeyFrame(0f))

        var headerCoinsText:Label = Label("Coins ", largeLabelStyle)
        var valueCoinsText:Label = Label(" × $coins + ", smallLabelStyle)
        var headerScoreText:Label = Label("Score ", largeLabelStyle)
        var valueScoreText:Label = Label(score.toInt().toString() +" = ", smallLabelStyle)
        var headerTotalText:Label = Label("Total", largeLabelStyle)
        var total:Int = coins.toInt() * 100 + score.toInt()
        var valueTotalText:Label = Label(total.toString(), smallLabelStyle)



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
        mTable.add(mStartLogoImage).size(mStartLogoImage.width * 0.5f, mStartLogoImage.height*0.5f).center().colspan(4)
        mTable.row()
        mTable.add(headerCoinsText).left().colspan(2)
        mTable.add(headerScoreText).left()
        mTable.add(headerTotalText).left()
        mTable.row()
        mTable.add(coinImage).size(64f).left()
        mTable.add(valueCoinsText).left()
        mTable.add(valueScoreText).left()
        mTable.add(valueTotalText).left()
        mTable.row()
        mTable.add(restartBtn).colspan(2).padRight(10f).padTop(32f).left()
        mTable.add(quitBtn).colspan(2).padLeft(10f).padTop(32f).left()
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

        stateTime += delta
        coinImage.drawable = TextureRegionDrawable(coinAnim.coinAnimation.getKeyFrame(stateTime, true))

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