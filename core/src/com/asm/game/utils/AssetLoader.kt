package com.asm.game.utils

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader.TextureAtlasParameter
import com.badlogic.gdx.graphics.g2d.TextureAtlas


class AssetLoader {
    var mAssetManager: AssetManager = AssetManager()
    lateinit var mBackgroundAtlas: TextureAtlas

    fun loadBackground() {

        val textureAtlasParameter = TextureAtlasParameter(false)
        if (!mAssetManager.isLoaded("Background/Background.pack")) {
            mAssetManager.load("Background/Background.pack", TextureAtlas::class.java, textureAtlasParameter)
            mAssetManager.finishLoading()
        }
        mBackgroundAtlas = mAssetManager.get("Background/Background.pack", TextureAtlas::class.java)

    }

    fun unloadBackground() {
        if (mAssetManager.isLoaded("Background/Background.pack", TextureAtlas::class.java)) {
            mAssetManager.unload("Background/Background.pack")
            mBackgroundAtlas.dispose()
        }
    }

    fun dispose() {
        unloadBackground()
        mAssetManager.dispose()
    }
}