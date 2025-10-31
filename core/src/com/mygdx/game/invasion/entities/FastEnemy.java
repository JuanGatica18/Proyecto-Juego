package com.mygdx.game.invasion.entities;

import com.mygdx.game.invasion.managers.TextureManager; // Importa

public class FastEnemy extends Enemy {
    public FastEnemy(float x, float y) {
        // Llama al constructor del padre con la textura correcta
        super(x, y, 50, TextureManager.getInstance().getTexture("fast_enemy"));
    }

    @Override
    public void update(float delta) {
        y -= 200 * delta;
    }
}