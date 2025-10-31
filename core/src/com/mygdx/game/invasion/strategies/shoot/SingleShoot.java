package com.mygdx.game.invasion.strategies.shoot;

// GM1.5: Implementación 1
public class SingleShoot implements ShootStrategy {
    @Override
    public void shoot(float x, float y) {
        // (Aquí iría la lógica de crear la bala)
        System.out.println("Disparo UNICO en " + x + "," + y);
    }
}