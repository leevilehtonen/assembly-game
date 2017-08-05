package com.asm.game.objects

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Body

open abstract class PhysicsGameObject(val body: Body, val texture: TextureRegion, var speed: Float) : GameObject(texture)
