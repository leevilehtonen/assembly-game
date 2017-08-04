package com.asm.game.utils

object Constants {
    const val BOX_TO_WORLD: Float = 250F
    const val WORLD_TO_BOX: Float = 1F / 250F
    const val GAME_HEIGHT = 576f
    const val GAME_WIDTH = 1024f
    const val DEFAULT_SPEED = 400f
    const val JUMP_AMOUNT = 20f
    const val ROTATE_SPEED = 500f
    const val PLAYER_PHYSICS_TAG = 0x0001
    const val BORDER_BOTTOM_PHYSICS_TAG = 0x0002
    const val BORDER_TOP_PHYSICS_TAG = 0x0003
}