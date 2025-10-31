package com.mygdx.game.invasion.entities;

import com.mygdx.game.invasion.strategies.shoot.ShootStrategy;
import com.mygdx.game.invasion.strategies.shoot.SingleShoot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

// GM1.5 Contexto y GM1.6 Encapsulamiento
public class PlayerShip {

    // GM1.6: Atributos privados
    private float x, y;
    private float speed = 300f; // Píxeles por segundo

    // GM1.5: El contexto usa la INTERFAZ, no la clase concreta
    private ShootStrategy currentWeapon;

    public PlayerShip(float x, float y) {
        this.x = x;
        this.y = y;
        // Arma inicial
        this.currentWeapon = new SingleShoot();
    }

    public void update(float delta) {
        // Movimiento simple (y encapsulado)
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * delta;
        }

        // Disparar
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            fire();
        }
    }

    // El contexto delega la acción a la estrategia
    private void fire() {
        // "this.x", "this.y" son la posición de la nave
        currentWeapon.shoot(this.x, this.y);
    }

    // Método para cambiar el arma (ej. un power-up)
    public void setWeapon(ShootStrategy newWeapon) {
        this.currentWeapon = newWeapon;
    }

    // Getters para que GameScreen pueda dibujarlo
    public float getX() { return x; }
    public float getY() { return y; }
}