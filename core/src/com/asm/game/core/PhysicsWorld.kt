package com.asm.game.core

import com.asm.game.objects.PhysicsGameObject
import com.asm.game.utils.Constants
import com.asm.game.utils.PlayerState
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ktx.box2d.body
import ktx.box2d.createWorld
import ktx.collections.gdxListOf


class PhysicsWorld(val mGameWorld: GameWorld) {
    lateinit var world: World

    init {
        initPhysicsWorld()
    }

    private fun initPhysicsWorld() {
        world = createWorld(Vector2(0F, -10F), false)
        world.setContactListener(object : ContactListener {
            override fun endContact(contact: Contact) {

            }

            override fun beginContact(contact: Contact) {
                val fixtureA = contact.fixtureA
                val fixtureB = contact.fixtureB
                if (fixtureA.userData == null || fixtureB.userData == null) {

                } else {
                    println(fixtureA.userData.toString() + "     " + fixtureB.userData)
                }
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
                } else if (fixtureA.filterData.categoryBits.toInt() == Constants.COIN_PHYSICS_TAG &&
                        fixtureB.filterData.categoryBits.toInt() == Constants.COIN_PHYSICS_TAG) {
                    mGameWorld.player.addCoin()

                } else if (fixtureB.filterData.categoryBits.toInt() == Constants.COIN_PHYSICS_TAG &&
                        fixtureA.filterData.categoryBits.toInt() == Constants.COIN_PHYSICS_TAG) {
                    mGameWorld.player.addCoin()

                }
            }

            override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
            }

            override fun postSolve(contact: Contact, impulse: ContactImpulse) {
                if ((contact.fixtureA.filterData.categoryBits.toInt() == Constants.PLAYER_PHYSICS_TAG && contact.fixtureB.filterData.categoryBits.toInt() == Constants.BORDER_SIDE_PHYSICS_TAG) || (contact.fixtureA.filterData.categoryBits.toInt() == Constants.BORDER_SIDE_PHYSICS_TAG && contact.fixtureB.filterData.categoryBits.toInt() == Constants.PLAYER_PHYSICS_TAG)) {
                    impulse.normalImpulses.forEach {
                        if (it > 100f) {
                           // println("Hävisit")
                            return
                        }
                    }
                }
            }

        })
    }


    fun update(delta: Float) {
        world.step(1 / 60f, 6, 2)

    }

    fun createPlayer(position: Vector2, density: Float, category: Int): Body {
        return world.body(BodyDef.BodyType.DynamicBody) {
            this.position.set(position.x * Constants.WORLD_TO_BOX, position.y * Constants.WORLD_TO_BOX)
            this.allowSleep = false
            polygon(
                    createPolyVector(Vector2(-20f, -45f)),
                    createPolyVector(Vector2(20f, -45f)),
                    createPolyVector(Vector2(20f, 45f)),
                    createPolyVector(Vector2(-20f, 45f)),
                    createPolyVector(Vector2(-30f, -5f)),
                    createPolyVector(Vector2(30f, -5f))
            ) {
                this.density = density
                this.filter.categoryBits = category.toShort()
            }

        }
    }

    fun createPolyVector(vector: Vector2): Vector2 {
        return vector.cpy().set(vector.x * Constants.WORLD_TO_BOX, vector.y * Constants.WORLD_TO_BOX)
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