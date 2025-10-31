package com.mygdx.game.invasion.entities;

import com.mygdx.game.invasion.managers.TextureManager; // Importa

public class TankEnemy extends Enemy {
    public TankEnemy(float x, float y) {
        // Llama al constructor del padre con la textura correcta
        super(x, y, 300, TextureManager.getInstance().getTexture("tank_enemy"));
    }

    @Override
    public void update(float delta) {
        y -= 50 * delta;
    }
}