package com.mygdx.game.invasion.entities;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.invasion.managers.TextureManager;

public class ZigZagEnemy extends Enemy {
    private float horizontalSpeed = 150f; // Velocidad horizontal
    private int direction = 1; // 1 = derecha, -1 = izquierda
    private float zigzagTimer = 0;
    private float zigzagInterval = 1f; // Cambia de dirección cada 1 segundo

    public ZigZagEnemy(float x, float y) {
        // 100 de vida, vale 20 puntos (enemigo medio)
        super(x, y, 100, TextureManager.getInstance().getTexture("zigzag_enemy"), 20);
    }

    @Override
    public void update(float delta) {
        // Movimiento vertical (baja)
        y -= 120 * delta;

        // Movimiento horizontal en zigzag
        x += horizontalSpeed * direction * delta;

        // Cambia de dirección cada cierto tiempo
        zigzagTimer += delta;
        if (zigzagTimer >= zigzagInterval) {
            direction *= -1; // Invierte la dirección
            zigzagTimer = 0;
        }

        // Rebota en los bordes de la pantalla
        if (x < 0) {
            x = 0;
            direction = 1;
        } else if (x > Gdx.graphics.getWidth() - texture.getWidth()) {
            x = Gdx.graphics.getWidth() - texture.getWidth();
            direction = -1;
        }

        updateBounds(); // Actualiza el hitbox

        // Se marca como muerto si sale de la pantalla
        if (isOutOfScreen()) {
            setDead(true);
        }
    }
}