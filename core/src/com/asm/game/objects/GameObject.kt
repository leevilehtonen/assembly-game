package com.asm.game.objects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

open abstract class GameObject(texture: Texture) : Updateable {
    var sprite: Sprite = Sprite(texture)
}