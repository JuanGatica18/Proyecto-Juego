package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.invasion.screens.MenuScreen; // Importa tu pantalla

public class InvasionGame extends Game {

    // Objetos compartidos por todas las pantallas
    public SpriteBatch batch;
    public BitmapFont font; // Fuente para texto rápido

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // Carga la fuente por defecto

        // Inicia el juego y establece la pantalla de Menú
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        // Delega el dibujado a la pantalla activa (MenuScreen, GameScreen, etc.)
        super.render();
    }

    @Override
    public void dispose() {
        // Limpia los recursos al cerrar
        batch.dispose();
        font.dispose();
    }
}