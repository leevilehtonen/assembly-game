package com.asm.game.objects

import com.asm.game.AsmGdxGame
import com.asm.game.utils.Constants
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxArrayOf

class Background(val mGame: AsmGdxGame, val speed: Float) {
    val layers = gdxArrayOf<Layer>()

    init {
        val foreTexture = TextureRegion(mGame.mAssetLoader.mBackgroundAtlas.findRegion("ForeHills"))
        val forestTexture = TextureRegion(mGame.mAssetLoader.mBackgroundAtlas.findRegion("Forest"))
        val floorTexture = TextureRegion(mGame.mAssetLoader.mBackgroundAtlas.findRegion("Floor"))
        val roofTexture = TextureRegion(mGame.mAssetLoader.mBackgroundAtlas.findRegion("Roof"))

        val foreY = -20f
        val forestY = 20f
        val floorY = -100f
        val roofY = Constants.GAME_HEIGHT - roofTexture.regionHeight + 100f

        val foreLayer = Layer(0.5f,  speed, foreTexture, foreY)
        val forestLayer = Layer(0.25f, speed, forestTexture, forestY)
        val floorLayer = Layer(1f, speed, floorTexture, floorY)
        val roofLayer = Layer(1f, speed, roofTexture, roofY)

        addLayer(forestLayer)
        addLayer(foreLayer)
        addLayer(floorLayer)
        addLayer(roofLayer)

    }


    fun addLayer(layer: Layer) {
        layers.add(layer)
    }

    fun update(delta: Float) {
        layers.forEach {
            it.update(delta)
        }
    }

    fun updateSpeed(speed: Float) {
        layers.forEach {
            it.speed = speed
        }
    }

}