package com.asm.game.utils

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader.TextureAtlasParameter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas


class AssetLoader {
    var mAssetManager: AssetManager = AssetManager()
    lateinit var mBackgroundAtlas: TextureAtlas
    lateinit var mWalkAnimationAtlas: TextureAtlas


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

    fun dispose() {
        unloadBackground()
        unloadAnimations()
        mAssetManager.dispose()
    }
}