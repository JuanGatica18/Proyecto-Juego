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

    private ShootStrategy currentWeapon;

    public PlayerShip(float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = TextureManager.getInstance().getTexture("player_ship");
        this.currentWeapon = new SingleShoot();

        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    // CORREGIDO: Ahora recibe la lista de balas como parámetro
    public void update(float delta, Array<Bullet> bullets) {
        // Movimiento libre (arriba, abajo, izquierda, derecha)
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

        // Limita la nave a los bordes de la pantalla
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth() - texture.getWidth()) x = Gdx.graphics.getWidth() - texture.getWidth();
        if (y < 0) y = 0;
        if (y > Gdx.graphics.getHeight() - texture.getHeight()) y = Gdx.graphics.getHeight() - texture.getHeight();

        bounds.setPosition(x, y);

        // Disparar - ahora pasa correctamente la lista de balas
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            fire(bullets);
        }
    }

    private void fire(Array<Bullet> bullets) {
        // Dispara desde el centro superior
        float bulletX = this.x + texture.getWidth() / 2f;
        float bulletY = this.y + texture.getHeight();

        // Llama a la estrategia y le pasa la lista
        currentWeapon.shoot(bulletX, bulletY, bullets);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void takeHit() {
        this.lives--;
    }

    public int getLives() {
        return lives;
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public void setWeapon(ShootStrategy newWeapon) {
        this.currentWeapon = newWeapon;
    }

    public float getX() { return x; }
    public float getY() { return y; }
}