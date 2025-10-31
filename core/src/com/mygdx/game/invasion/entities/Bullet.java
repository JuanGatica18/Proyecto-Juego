package com.mygdx.game.invasion.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.invasion.managers.TextureManager;

public class Bullet {

    private float x, y;
    private float speed = 500f;
    private Texture texture;
    private Rectangle bounds; // Para colisiones
    private boolean isDead = false; // Para saber cuándo eliminarla

    public Bullet(float x, float y) {
        this.texture = TextureManager.getInstance().getTexture("player_bullet");
        this.x = x - texture.getWidth() / 2f; // Centra la bala
        this.y = y;
        this.bounds = new Rectangle(this.x, this.y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta) {
        y += speed * delta; // Se mueve hacia arriba

        // Si se sale de la pantalla, marca para eliminar
        if (y > Gdx.graphics.getHeight()) {
            isDead = true;
        }

        // Actualiza el rectángulo de colisión
        bounds.setPosition(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}