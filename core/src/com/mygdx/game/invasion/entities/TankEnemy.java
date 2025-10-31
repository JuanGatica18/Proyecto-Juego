package com.mygdx.game.invasion.entities;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.invasion.managers.TextureManager;

public class TankEnemy extends Enemy {
    public TankEnemy(float x, float y) {
        // 300 de vida, vale 30 puntos (más difícil de matar)
        super(x, y, 300, TextureManager.getInstance().getTexture("tank_enemy"), 30);
    }

    @Override
    public void update(float delta) {
        y -= 50 * delta; // Velocidad lenta
        updateBounds(); // Actualiza el hitbox

        // Se marca como muerto si sale de la pantalla
        if (isOutOfScreen()) {
            setDead(true);
        }
    }
}