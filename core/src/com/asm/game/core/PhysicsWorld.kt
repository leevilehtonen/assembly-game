package com.asm.game.core

import com.asm.game.utils.Constants
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ktx.box2d.body
import ktx.box2d.createWorld


class PhysicsWorld(val mGameWorld: GameWorld) {
    lateinit var world: World

    init {
        initPhyicsWorld()
    }

    private fun initPhyicsWorld() {
        world = createWorld(Vector2(0F, -25F), false)
        world.setContactListener(object : ContactListener {
            override fun endContact(contact: Contact) {
            }

            override fun beginContact(contact: Contact) {
                val fixtureA = contact.fixtureA
                val fixtureB = contact.fixtureB

                println("Contact")

                if ((fixtureA.filterData.categoryBits.toInt() == Constants.PLAYER_PHYSICS_TAG
                        && fixtureB.filterData.categoryBits.toInt() == Constants.BORDER_PHYSICS_TAG)
                        || (fixtureB.filterData.categoryBits.toInt() == Constants.PLAYER_PHYSICS_TAG
                        && fixtureA.filterData.categoryBits.toInt() == Constants.BORDER_PHYSICS_TAG)) {

                    println("Player touched ground")
                    mGameWorld.player.flipped = false
                    mGameWorld.player.jumped = false
                }
            }

            override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
            }

            override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
            }

        })
    }


    fun update(delta: Float) {
        world.step(1 / 60f, 6, 2)

    }

    fun createDynamicBody(size: Vector2, position: Vector2, density: Float, category: Int): Body {
        return world.body(BodyDef.BodyType.DynamicBody) {
            this.position.set(position.x * Constants.WORLD_TO_BOX, position.y * Constants.WORLD_TO_BOX)
            box(width = size.x * Constants.WORLD_TO_BOX, height = size.y * Constants.WORLD_TO_BOX) {
                this.density = density
                this.filter.categoryBits = category.toShort()
            }
        }
    }

    fun createStaticBody(size: Vector2, position: Vector2, density: Float, category: Int): Body {
        return world.body(BodyDef.BodyType.StaticBody) {
            this.position.set(position.x * Constants.WORLD_TO_BOX, position.y * Constants.WORLD_TO_BOX)
            box(width = size.x * Constants.WORLD_TO_BOX, height = size.y * Constants.WORLD_TO_BOX) {
                this.density = density
                this.filter.categoryBits = category.toShort()
            }
        }
    }

}