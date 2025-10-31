package com.mygdx.game.invasion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.InvasionGame;

public class GameOverScreen implements Screen {

    private final InvasionGame game;

    public GameOverScreen(InvasionGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        // Limpia la pantalla
        ScreenUtils.clear(0.5f, 0, 0, 1); // Rojo oscuro

        // Dibuja el texto
        game.batch.begin();
        game.font.draw(game.batch, "GAME OVER", 100, 150);
        game.font.draw(game.batch, "Presiona ENTER para volver al menu", 100, 100);
        game.batch.end();

        // Si pulsa ENTER, vuelve al menú
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }

    // --- Métodos de Screen vacíos ---
    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() { }
}