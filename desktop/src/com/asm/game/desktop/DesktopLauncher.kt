package com.asm.game.desktop

import com.asm.game.AsmGdxGame
import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration


object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = 1024
        config.height = 576
        config.vSyncEnabled = false
        config.backgroundFPS = 60
        config.foregroundFPS = 60
        config.useGL30 = true
        config.resizable = false
        config.title = "Assembly Game"
        config.addIcon("General/logoIcon.png", Files.FileType.Internal)
        /**Java code**/
        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true")
        /**end.**/
        LwjglApplication(AsmGdxGame(), config)
    }
}
