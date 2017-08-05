package com.asm.game.objects

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion

open abstract class GameObject(texture: TextureRegion) : Updateable {
    var sprite: Sprite = Sprite(texture)
}