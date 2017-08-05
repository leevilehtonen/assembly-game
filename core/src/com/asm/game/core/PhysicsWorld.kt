package com.asm.game.core

import com.asm.game.utils.Constants
import com.asm.game.utils.PlayerState
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
        world = createWorld(Vector2(0F, -10F), false)
        world.setContactListener(object : ContactListener {
            override fun endContact(contact: Contact) {

            }

            override fun beginContact(contact: Contact) {
                val fixtureA = contact.fixtureA
                val fixtureB = contact.fixtureB


                if ((fixtureA.filterData.categoryBits.toInt() == Constants.PLAYER_PHYSICS_TAG
                        && fixtureB.filterData.categoryBits.toInt() == Constants.BORDER_BOTTOM_PHYSICS_TAG)
                        || (fixtureB.filterData.categoryBits.toInt() == Constants.PLAYER_PHYSICS_TAG
                        && fixtureA.filterData.categoryBits.toInt() == Constants.BORDER_BOTTOM_PHYSICS_TAG)) {
                    mGameWorld.player.playerState = PlayerState.POSITION_BOTTOM
                } else if ((fixtureA.filterData.categoryBits.toInt() == Constants.PLAYER_PHYSICS_TAG
                        && fixtureB.filterData.categoryBits.toInt() == Constants.BORDER_TOP_PHYSICS_TAG)
                        || (fixtureB.filterData.categoryBits.toInt() == Constants.PLAYER_PHYSICS_TAG
                        && fixtureA.filterData.categoryBits.toInt() == Constants.BORDER_TOP_PHYSICS_TAG)) {
                    mGameWorld.player.playerState = PlayerState.POSITION_TOP
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

    fun createPlayer() {
    }

    fun createDynamicBody(size: Vector2, position: Vector2, density: Float, category: Int): Body {
        return world.body(BodyDef.BodyType.DynamicBody) {
            this.position.set(position.x * Constants.WORLD_TO_BOX, position.y * Constants.WORLD_TO_BOX)
            this.allowSleep = false
            box(width = size.x * Constants.WORLD_TO_BOX, height = size.y * Constants.WORLD_TO_BOX) {
                this.density = density
                this.filter.categoryBits = category.toShort()
            }
        }
    }

    fun createStaticBody(size: Vector2, position: Vector2, density: Float, category: Int): Body {
        return world.body(BodyDef.BodyType.StaticBody) {
            this.position.set(position.x * Constants.WORLD_TO_BOX, position.y * Constants.WORLD_TO_BOX)
            this.allowSleep = false
            box(width = size.x * Constants.WORLD_TO_BOX, height = size.y * Constants.WORLD_TO_BOX) {
                this.density = density
                this.filter.categoryBits = category.toShort()
            }
        }
    }

}