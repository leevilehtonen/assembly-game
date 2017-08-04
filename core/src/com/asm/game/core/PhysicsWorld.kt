package com.asm.game.core

import com.asm.game.utils.Constants
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.box2d.body
import ktx.box2d.createWorld


class PhysicsWorld {
    var world: World = createWorld(Vector2(0F, -10F), true)


    fun update(delta: Float) {
        world.step(1 / 60f, 6, 2)
    }

    fun createDynamicBody(size: Vector2, position: Vector2, density: Float): Body {
        return world.body(BodyDef.BodyType.DynamicBody) {
            this.position.set(position.x * Constants.WORLD_TO_BOX, position.y * Constants.WORLD_TO_BOX)
            box(width = size.x * Constants.WORLD_TO_BOX, height = size.y * Constants.WORLD_TO_BOX) {
                this.density = density
            }
        }
    }

    fun createStaticBody(size: Vector2, position: Vector2, density: Float): Body {
        return world.body(BodyDef.BodyType.StaticBody) {
            this.position.set(position.x * Constants.WORLD_TO_BOX, position.y * Constants.WORLD_TO_BOX)
            box(width = size.x * Constants.WORLD_TO_BOX, height = size.y * Constants.WORLD_TO_BOX) {
                this.density = density
            }
        }
    }

}