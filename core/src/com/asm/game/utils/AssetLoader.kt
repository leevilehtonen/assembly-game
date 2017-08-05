package com.asm.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader.TextureAtlasParameter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator


class AssetLoader {
    var mAssetManager: AssetManager = AssetManager()
    lateinit var mBackgroundAtlas: TextureAtlas
    lateinit var mWalkAnimationAtlas: TextureAtlas
    lateinit var mCoinAnimationAtlas: TextureAtlas
    lateinit var fontBig: BitmapFont
    lateinit var fontSmall: BitmapFont

    fun loadFonts(){
        val freeTypeFontGenerator = FreeTypeFontGenerator(Gdx.files.internal("Fonts/font.ttf"))
        val freeTypeFontParameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        freeTypeFontParameter.magFilter = Texture.TextureFilter.Linear
        freeTypeFontParameter.minFilter = Texture.TextureFilter.Linear
        freeTypeFontParameter.size = 40
        fontBig = freeTypeFontGenerator.generateFont(freeTypeFontParameter)
        freeTypeFontParameter.size = 24
        fontSmall = freeTypeFontGenerator.generateFont(freeTypeFontParameter)
        freeTypeFontGenerator.dispose()
    }

    fun unloadFonts(){
        fontBig.dispose()
        fontSmall.dispose()
    }
    fun loadBackground() {

        val textureAtlasParameter = TextureAtlasParameter(false)
        if (!mAssetManager.isLoaded("Background/Background.pack")) {
            mAssetManager.load("Background/Background.pack", TextureAtlas::class.java, textureAtlasParameter)
            mAssetManager.finishLoading()
        }
        mBackgroundAtlas = mAssetManager.get("Background/Background.pack", TextureAtlas::class.java)

    }

    fun loadWalkAnimation() {
        val textureAtlasParameter: TextureAtlasParameter = TextureAtlasParameter(false)
        if (!mAssetManager.isLoaded("GameScreen/WalkAnimation.pack")) {
            mAssetManager.load("GameScreen/WalkAnimation.pack", TextureAtlas::class.java, textureAtlasParameter)
            mAssetManager.finishLoading()
        }
        mWalkAnimationAtlas = mAssetManager.get("GameScreen/WalkAnimation.pack", TextureAtlas::class.java)

        mWalkAnimationAtlas.textures.forEach {
            it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }
    }

    fun loadCoinAnimation() {
        val textureAtlasParameter: TextureAtlasParameter = TextureAtlasParameter(false)
        if (!mAssetManager.isLoaded("GameScreen/CoinAnimation.pack")) {
            mAssetManager.load("GameScreen/CoinAnimation.pack", TextureAtlas::class.java, textureAtlasParameter)
            mAssetManager.finishLoading()
        }
        mCoinAnimationAtlas = mAssetManager.get("GameScreen/CoinAnimation.pack", TextureAtlas::class.java)

        mCoinAnimationAtlas.textures.forEach {
            it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }
    }

    fun unloadBackground() {
        if (mAssetManager.isLoaded("Background/Background.pack", TextureAtlas::class.java)) {
            mAssetManager.unload("Background/Background.pack")
            mBackgroundAtlas.dispose()
        }
    }

    fun unloadAnimations() {
        if (mAssetManager.isLoaded("GameScreen/WalkAnimation.pack", TextureAtlas::class.java)) {
            mAssetManager.unload("GameScreen/WalkAnimation.pack")
            mWalkAnimationAtlas.dispose()
        }
    }

    fun loadAnimations() {
        if (mAssetManager.isLoaded("GameScreen/CoinAnimation.pack", TextureAtlas::class.java)) {
            mAssetManager.unload("GameScreen/CoinAnimation.pack")
            mWalkAnimationAtlas.dispose()
        }
    }

    fun dispose() {
        unloadBackground()
        unloadAnimations()
        unloadFonts()
        mAssetManager.dispose()
    }
}