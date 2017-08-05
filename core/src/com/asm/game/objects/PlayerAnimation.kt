package com.asm.game.objects

import com.asm.game.utils.AssetLoader
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxArrayOf

class PlayerAnimation(val assetLoader: AssetLoader) {
    lateinit var walkAnimation: Animation<TextureRegion>
    var walkFrames = gdxArrayOf<TextureRegion>()
    val frameDuration: Float = 1 / 24f

    init {
        createAnimation()
    }

    private fun createAnimation() {
        assetLoader.mWalkAnimationAtlas.regions.forEach {
            walkFrames.add(it)
        }
        walkAnimation = Animation(frameDuration, walkFrames)
    }
}