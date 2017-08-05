package com.asm.game.objects

import com.asm.game.utils.AssetLoader
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxArrayOf

class CoinAnimation(val assetLoader: AssetLoader) {
    lateinit var coinAnimation: Animation<TextureRegion>
    var coinFrames = gdxArrayOf<TextureRegion>()
    val frameDuration: Float = 1 / 60f

    init {
        createAnimation()
    }

    private fun createAnimation() {
        assetLoader.mCoinAnimationAtlas.regions.forEach {
            coinFrames.add(it)
        }
        coinAnimation = Animation(frameDuration, coinFrames)
    }
}