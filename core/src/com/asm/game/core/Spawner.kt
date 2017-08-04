package com.asm.game.core

import com.asm.game.objects.PhysicsGameObject
import ktx.collections.gdxListOf

class Spawner(interval: Int) {

    //Interval, in which obstacles are spawned
    val interval = 3500
    val spawned = gdxListOf<PhysicsGameObject>()


}