package com.mygdx.game.invasion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.InvasionGame;

public class GameOverScreen implements Screen {

    private final InvasionGame game;
    private final int finalScore;
    private BitmapFont bigFont;

    public GameOverScreen(InvasionGame game, int finalScore) {
        this.game = game;
        this.finalScore = finalScore;

        // Fuente más grande para el Game Over
        this.bigFont = new BitmapFont();
        this.bigFont.getData().setScale(3f);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0, 0, 1);

        game.batch.begin();

        // Título GAME OVER grande
        bigFont.draw(game.batch, "GAME OVER",
                Gdx.graphics.getWidth() / 2f - 150,
                Gdx.graphics.getHeight() / 2f + 100);

        // Puntaje final
        game.font.getData().setScale(2f);
        game.font.draw(game.batch, "PUNTAJE FINAL: " + finalScore,
                Gdx.graphics.getWidth() / 2f - 150,
                Gdx.graphics.getHeight() / 2f);

        // Instrucciones
        game.font.getData().setScale(1.5f);
        game.font.draw(game.batch, "Presiona ENTER para volver al menu",
                Gdx.graphics.getWidth() / 2f - 200,
                Gdx.graphics.getHeight() / 2f - 100);

        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            dispose(); // Primero limpia esta pantalla
            game.setScreen(new MenuScreen(game)); // Luego cambia de pantalla
        }
    }

    @Override
    public void dispose() {
        bigFont.dispose();
    }

    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
}