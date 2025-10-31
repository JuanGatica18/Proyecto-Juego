package com.mygdx.game.invasion.entities;

import com.badlogic.gdx.graphics.Texture; // Importa Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch; // Para dibujar
import com.badlogic.gdx.math.Rectangle;

// GM1.4: Clase Abstracta
public abstract class Enemy {

    protected Rectangle bounds;
    protected float x, y;
    protected int health;
    protected boolean isDead = false;
    protected Texture texture; // ¡Ahora tiene una textura!

    public Enemy(float x, float y, int health, Texture texture) { // Constructor con textura
        this.x = x;
        this.y = y;
        this.health = health;
        this.texture = texture;
        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    @Override
    public abstract void update(float delta);

    public void update(float delta) {
        // (Los hijos implementarán el movimiento)
        // Actualiza el rectángulo de colisión
        bounds.setPosition(x, y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    // ¡Nuevo método para dibujar el enemigo!
    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y);
        }
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) this.isDead = true;
    }

    public boolean isDead() { return isDead; }
    public float getX() { return x; }
    public float getY() { return y; }
}