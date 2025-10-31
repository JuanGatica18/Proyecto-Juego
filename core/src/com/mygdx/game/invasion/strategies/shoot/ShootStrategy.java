package com.mygdx.game.invasion.strategies.shoot;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.invasion.entities.Bullet; // Importa la bala

// GM1.5: Interfaz
public interface ShootStrategy {
    // ¡AHORA PASAMOS LA LISTA DE BALAS!
    void shoot(float x, float y, Array<Bullet> bullets);
}