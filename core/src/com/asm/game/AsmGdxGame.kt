package com.asm.game

import com.asm.game.screens.GameScreen
import com.asm.game.utils.AssetLoader
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.KtxGame

class AsmGdxGame : KtxGame<Screen>() {

    val GAME_WIDTH: Float = 1024F
    val GAME_HEIGHT: Float = 576F

    lateinit var mSpriteBatch: SpriteBatch
    lateinit var mShapeRenderer: ShapeRenderer
    lateinit var mAssetLoader: AssetLoader

    override fun create() {
        mSpriteBatch = SpriteBatch()
        mShapeRenderer = ShapeRenderer()
        mAssetLoader = AssetLoader()
        // addScreen(SplashScreen(this))
        mAssetLoader.loadBackground()
        mAssetLoader.loadWalkAnimation()
        mAssetLoader.loadFonts()
        mAssetLoader.loadCoinAnimation()
        addScreen(GameScreen(this))
        setScreen<GameScreen>()
    }

    override fun dispose() {
        super.dispose()
        mAssetLoader.dispose()
    }
}

