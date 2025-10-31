package com.mygdx.game.invasion.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// GM1.4: Clase Abstracta - Padre de FastEnemy y TankEnemy
public abstract class Enemy {

    protected Rectangle bounds;
    protected float x, y;
    protected int health;
    protected boolean isDead = false;
    protected Texture texture;

    public Enemy(float x, float y, int health, Texture texture) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.texture = texture;
        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    // Método abstracto que cada hijo debe implementar
    public abstract void update(float delta);

    // Método para dibujar el enemigo
    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y);
        }
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) this.isDead = true;
    }

    // Actualiza el rectángulo de colisión (llamado por hijos)
    protected void updateBounds() {
        bounds.setPosition(x, y);
    }

    public boolean isDead() { return isDead; }
    public void setDead(boolean dead) { this.isDead = dead; }
    public float getX() { return x; }
    public float getY() { return y; }
    public Rectangle getBounds() { return bounds; }
}