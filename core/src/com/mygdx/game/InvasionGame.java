package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.invasion.screens.MenuScreen;
import com.mygdx.game.invasion.managers.TextureManager;

public class InvasionGame extends Game {

    // Objetos compartidos por todas las pantallas
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Inicializa el TextureManager una sola vez
        TextureManager.getInstance();

        // Inicia el juego y establece la pantalla de Menú
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        // Delega el dibujado a la pantalla activa
        super.render();
    }

    @Override
    public void dispose() {
        // Limpia los recursos al cerrar el juego COMPLETAMENTE
        batch.dispose();
        font.dispose();

        // AHORA SÍ disponemos el TextureManager al cerrar todo el juego
        TextureManager.getInstance().dispose();
    }
}