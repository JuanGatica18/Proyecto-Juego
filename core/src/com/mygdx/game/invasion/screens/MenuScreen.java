package com.mygdx.game.invasion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.InvasionGame; // Importa tu juego principal

public class MenuScreen implements Screen {

    private final InvasionGame game;

    public MenuScreen(InvasionGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        // Limpia la pantalla
        ScreenUtils.clear(0, 0, 0.2f, 1); // Azul oscuro

        // Dibuja el texto
        game.batch.begin();
        game.font.draw(game.batch, "Proyecto Invasion", 100, 150);
        game.font.draw(game.batch, "Presiona ENTER para jugar", 100, 100);
        game.batch.end();

        // Si pulsa ENTER, cambia a la pantalla de juego
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
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