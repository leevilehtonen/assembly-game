package com.asm.game.objects

import com.asm.game.utils.Constants
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import ktx.collections.gdxArrayOf

class Background(val speed: Float) {
    val layers = gdxArrayOf<Layer>()

    init {
        val foreTexture = Texture(Gdx.files.internal("Background/ForeHills.png"))
        val forestTexture = Texture(Gdx.files.internal("Background/Forest.png"))
        val floorTexture = Texture(Gdx.files.internal("Background/Floor.png"))
        val roofTexture = Texture(Gdx.files.internal("Background/Roof.png"))

        foreTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        foreTexture.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear)

        forestTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        forestTexture.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear)

        floorTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        floorTexture.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear)

        roofTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        roofTexture.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear)


        val foreY = 80f
        val forestY = 120f
        val floorY = 0f
        val roofY = Constants.GAME_HEIGHT - roofTexture.height


        val foreLayer = Layer(0.5f * speed, foreTexture, foreY)
        val forestLayer = Layer(0.25f * speed, forestTexture, forestY)
        val floorLayer = Layer(speed, floorTexture, floorY)
        val roofLayer = Layer(speed, roofTexture, roofY)

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

}