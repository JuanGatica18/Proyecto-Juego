package com.mygdx.game.invasion.entities;

// GM1.4: Hijo 2
public class TankEnemy extends Enemy {
    public TankEnemy(float x, float y) {
        super(x, y, 300); // Mucha vida
    }

    @Override
    public void update(float delta) {
        y -= 50 * delta; // Se mueve lento
    }
}