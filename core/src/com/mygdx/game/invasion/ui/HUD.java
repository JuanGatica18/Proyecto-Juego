package com.mygdx.game.invasion.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {

    private BitmapFont font;
    private BitmapFont smallFont;
    private int score;

    public HUD() {
        this.font = new BitmapFont();
        this.font.getData().setScale(2f);
        this.font.setColor(Color.WHITE);

        this.smallFont = new BitmapFont();
        this.smallFont.getData().setScale(1.5f);
        this.smallFont.setColor(Color.CYAN);

        this.score = 0;
    }

    public void render(SpriteBatch batch, int lives, int level, boolean hasPowerUp, float powerUpTimer, float powerUpDuration) {
        // Dibuja las vidas en la esquina superior izquierda
        font.draw(batch, "VIDAS: " + lives, 20, Gdx.graphics.getHeight() - 20);

        // Dibuja el puntaje
        font.draw(batch, "PUNTOS: " + score, 20, Gdx.graphics.getHeight() - 60);

        // Dibuja el nivel
        font.setColor(Color.YELLOW);
        font.draw(batch, "NIVEL: " + level, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 20);
        font.setColor(Color.WHITE);

        // Dibuja información del power-up si está activo
        if (hasPowerUp) {
            float timeRemaining = powerUpDuration - powerUpTimer;
            smallFont.setColor(Color.GREEN);
            smallFont.draw(batch, "DOBLE DISPARO: " + String.format("%.1f", timeRemaining) + "s",
                    Gdx.graphics.getWidth() / 2f - 120, Gdx.graphics.getHeight() - 20);
            smallFont.setColor(Color.CYAN);
        }
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public void dispose() {
        font.dispose();
        smallFont.dispose();
    }
}