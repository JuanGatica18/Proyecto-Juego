package com.mygdx.game.invasion.entities;

// GM1.4: Hijo 1
public class FastEnemy extends Enemy {
    public FastEnemy(float x, float y) {
        super(x, y, 50); // Poca vida
    }

    @Override
    public void update(float delta) {
        y -= 200 * delta; // Se mueve rápido
    }
}
