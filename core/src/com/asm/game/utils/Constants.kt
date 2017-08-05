package com.asm.game.utils

object Constants {
    const val BOX_TO_WORLD: Float = 100F
    const val WORLD_TO_BOX: Float = 1F / 100F
    const val GAME_HEIGHT = 576f
    const val GAME_WIDTH = 1024f
    const val DEFAULT_SPEED = 400f
    const val JUMP_AMOUNT = 750f
    const val ROTATE_SPEED = 1000f
    const val PLAYER_PHYSICS_TAG = 0x0001
    const val BORDER_BOTTOM_PHYSICS_TAG = 0x0002
    const val BORDER_TOP_PHYSICS_TAG = 0x0003
    const val BORDER_SIDE_PHYSICS_TAG = 0x0004
    const val OBSTACLE_PHYSICS_TAG = 0x0005
    const val SPAWNER_TARGET_TIME = 1500000000L
    const val SPEEDUPDATE_TARGET_TIME = 5000000000L
    const val GRAVITY_INTERVAL = 5000000000L
}