package com.asm.game

import com.asm.game.screens.GameScreen
import com.asm.game.screens.SplashScreen
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.KtxGame

class AsmGdxGame : KtxGame<Screen>() {

    val GAME_WIDTH: Float = 1024F
    val GAME_HEIGHT: Float = 576F

    lateinit var mSpriteBatch: SpriteBatch
    lateinit var mShapeRenderer: ShapeRenderer

    override fun create() {
        mSpriteBatch = SpriteBatch()
        mShapeRenderer = ShapeRenderer()
        addScreen(SplashScreen(this))
        addScreen(GameScreen(this))
        setScreen<GameScreen>()
    }

}

