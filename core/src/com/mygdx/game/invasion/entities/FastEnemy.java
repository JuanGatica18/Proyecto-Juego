package com.mygdx.game.invasion.entities;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.invasion.managers.TextureManager;

public class FastEnemy extends Enemy {
    public FastEnemy(float x, float y) {
        // 50 de vida, vale 10 puntos
        super(x, y, 50, TextureManager.getInstance().getTexture("fast_enemy"), 10);
    }

    @Override
    public void update(float delta) {
        y -= 200 * delta; // Velocidad rápida
        updateBounds(); // Actualiza el hitbox

        // Se marca como muerto si sale de la pantalla
        if (isOutOfScreen()) {
            setDead(true);
        }
    }
}