package com.mygdx.game.invasion.entities;

import com.mygdx.game.invasion.managers.TextureManager;
import com.mygdx.game.invasion.strategies.shoot.ShootStrategy;
import com.mygdx.game.invasion.strategies.shoot.SingleShoot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class PlayerShip {

    private float x, y;
    private int lives = 3;
    private Rectangle bounds;
    private float speed = 300f;
    private Texture texture;

    // Sistema de invencibilidad
    private boolean invincible = false;
    private float invincibilityTimer = 0;
    private float invincibilityDuration = 2f; // 2 segundos de invencibilidad
    private float blinkTimer = 0;

    private ShootStrategy currentWeapon;

    public PlayerShip(float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = TextureManager.getInstance().getTexture("player_ship");
        this.currentWeapon = new SingleShoot();

        // Colisión más pequeña que la textura (hitbox más preciso)
        float hitboxReduction = 0.3f; // 30% más pequeño
        float width = texture.getWidth() * (1 - hitboxReduction);
        float height = texture.getHeight() * (1 - hitboxReduction);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void update(float delta, Array<Bullet> bullets) {
        // Actualiza invencibilidad
        if (invincible) {
            invincibilityTimer += delta;
            blinkTimer += delta;

            if (invincibilityTimer >= invincibilityDuration) {
                invincible = false;
                invincibilityTimer = 0;
            }
        }

        // Movimiento libre
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= speed * delta;
        }

        // Limita la nave a los bordes
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth() - texture.getWidth()) x = Gdx.graphics.getWidth() - texture.getWidth();
        if (y < 0) y = 0;
        if (y > Gdx.graphics.getHeight() - texture.getHeight()) y = Gdx.graphics.getHeight() - texture.getHeight();

        // Actualiza hitbox (centrada en la nave)
        float hitboxReduction = 0.3f;
        float width = texture.getWidth() * (1 - hitboxReduction);
        float height = texture.getHeight() * (1 - hitboxReduction);
        float centerX = x + (texture.getWidth() - width) / 2;
        float centerY = y + (texture.getHeight() - height) / 2;
        bounds.set(centerX, centerY, width, height);

        // Disparar
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            fire(bullets);
        }
    }

    private void fire(Array<Bullet> bullets) {
        float bulletX = this.x + texture.getWidth() / 2f;
        float bulletY = this.y + texture.getHeight();
        currentWeapon.shoot(bulletX, bulletY, bullets);
    }

    public void render(SpriteBatch batch) {
        // Efecto de parpadeo cuando es invencible
        if (invincible) {
            if ((int)(blinkTimer * 10) % 2 == 0) { // Parpadea cada 0.1 segundos
                batch.draw(texture, x, y);
            }
        } else {
            batch.draw(texture, x, y);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void takeHit() {
        if (!invincible) {
            this.lives--;
            if (lives > 0) {
                // Activa invencibilidad después de perder una vida
                invincible = true;
                invincibilityTimer = 0;
                blinkTimer = 0;
            }
        }
    }

    public int getLives() {
        return lives;
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setWeapon(ShootStrategy newWeapon) {
        this.currentWeapon = newWeapon;
    }

    public float getX() { return x; }
    public float getY() { return y; }
}