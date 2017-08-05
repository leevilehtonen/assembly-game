package com.asm.game.core

import com.asm.game.AsmGdxGame
import com.asm.game.screens.GameScreen
import com.asm.game.utils.Constants
import com.asm.game.utils.GameColors
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.TimeUtils
import com.sun.corba.se.impl.orbutil.closure.Constant
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const


class GameRenderer(val mGame: AsmGdxGame, val mGameScreen: GameScreen, val mGameWorld: GameWorld){
    var debugRenderer: Box2DDebugRenderer = Box2DDebugRenderer()
    var debugMatrix: Matrix4 = mGameScreen.mCamera.combined.cpy().scale(Constants.BOX_TO_WORLD, Constants.BOX_TO_WORLD, 1F)


    fun render() {
        Gdx.gl.glClearColor(0F, 0F, 0F, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        Gdx.gl.glEnable(GL20.GL_BLEND)
        mGame.mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        mGame.mShapeRenderer.color = GameColors.GAME_BACKGROUND
        mGame.mShapeRenderer.rect(0F, 0F, Constants.GAME_WIDTH, Constants.GAME_HEIGHT)
        mGame.mShapeRenderer.end()

        mGame.mSpriteBatch.begin()


        mGameWorld.background.layers.forEach {it.sprites.forEach{it.draw(mGame.mSpriteBatch)}}
        mGameWorld.spawner.objects.forEach { it.sprite.draw(mGame.mSpriteBatch) }
        mGameWorld.objects.forEach { it.sprite.draw(mGame.mSpriteBatch) }


        mGameScreen.bitmapFont.setColor(mGameWorld.coinTextColor)
        mGameScreen.bitmapFont.draw(mGame.mSpriteBatch, mGameWorld.gravString, Constants.GAME_WIDTH / 2 + 150, Constants.GAME_HEIGHT / 2)
        mGameScreen.bitmapFont.setColor(1f,1f,1f,1f)
        mGameScreen.bitmapFontSmall.draw(mGame.mSpriteBatch, mGameWorld.gravTextString, Constants.GAME_WIDTH / 2 + 150, Constants.GAME_HEIGHT / 2 + 50f)
        mGameScreen.bitmapFont.draw(mGame.mSpriteBatch,"Gravity: " + mGameWorld.gravityHandler.currentGravity.toString(), 200f, Constants.GAME_HEIGHT - 10f )
        mGameScreen.bitmapFont.draw(mGame.mSpriteBatch, mGameWorld.player.coinString(), 40f, Constants.GAME_HEIGHT - 10)
        mGameScreen.bitmapFont.draw(mGame.mSpriteBatch, mGameWorld.player.pointString(), 500f, Constants.GAME_HEIGHT - 10)

        mGame.mSpriteBatch.end()

        mGame.mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        mGame.mShapeRenderer.color = GameColors.GRAVITY_BAR

        mGame.mShapeRenderer.rect( mGameWorld.gravityHandler.getX(), mGameWorld.gravityHandler.getY(), mGameWorld.gravityHandler.sizeX(), mGameWorld.gravityHandler.sizeY())
        mGame.mShapeRenderer.color = GameColors.GRAVITY_BAR_MID

        mGame.mShapeRenderer.rect( mGameWorld.gravityHandler.getX()- 30f, mGameWorld.gravityHandler.getY() - 10f, 60f,20f)

        mGame.mShapeRenderer.end()



        debugRenderer.render(mGameWorld.physicsWorld.world, debugMatrix)


    }

}