package com.mygdx.game.invasion.entities;

import com.mygdx.game.invasion.managers.TextureManager; // Nuevo import
import com.mygdx.game.invasion.strategies.shoot.ShootStrategy;
import com.mygdx.game.invasion.strategies.shoot.SingleShoot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture; // Importa Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch; // Para dibujar
import com.badlogic.gdx.math.Rectangle; // Importa Rectangle
import com.badlogic.gdx.utils.Array;    // Importa Array
import com.mygdx.game.invasion.entities.Bullet; // Importa Bullet

public class PlayerShip {

    private float x, y;
    private int lives = 3; // <-- ¡VIDAS!
    private Rectangle bounds; // <-- Para colisiones
    private float speed = 300f; // Píxeles por segundo
    private Texture texture; // ¡Ahora tiene una textura!

    private ShootStrategy currentWeapon;

    public PlayerShip(float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = TextureManager.getInstance().getTexture("player_ship"); // Carga la textura
        this.currentWeapon = new SingleShoot();

        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta) {
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

        // Disparar
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            fire(bullets); // Pasa la lista de balas al método fire
        }
    }

    private void fire(Array<Bullet> bullets) {
        // Dispara desde el centro superior
        float bulletX = this.x + texture.getWidth() / 2f;
        float bulletY = this.y + texture.getHeight();

        // Llama a la estrategia y le pasa la lista
        currentWeapon.shoot(bulletX, bulletY, bullets);
    }

    // ¡Nuevo metodo para dibujar la nave!
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