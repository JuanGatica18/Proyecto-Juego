package com.mygdx.game.invasion.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {

    private BitmapFont font;
    private int score;

    public HUD() {
        this.font = new BitmapFont();
        this.font.getData().setScale(2f); // Hace el texto más grande
        this.score = 0;
    }

    public void render(SpriteBatch batch, int lives) {
        // Dibuja las vidas en la esquina superior izquierda
        font.draw(batch, "VIDAS: " + lives, 20, Gdx.graphics.getHeight() - 20);

        // Dibuja el puntaje en la esquina superior derecha
        font.draw(batch, "PUNTOS: " + score, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 20);
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public void dispose() {
        font.dispose();
    }
}