package com.mygdx.game.invasion.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy {

    protected Rectangle bounds;
    protected float x, y;
    protected int health;
    protected int maxHealth;
    protected boolean isDead = false;
    protected Texture texture;
    protected int scoreValue; // Puntos que otorga al morir

    public Enemy(float x, float y, int health, Texture texture, int scoreValue) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.maxHealth = health;
        this.texture = texture;
        this.scoreValue = scoreValue;

        // Hitbox más pequeño (70% del tamaño de la textura)
        float hitboxReduction = 0.3f;
        float width = texture.getWidth() * (1 - hitboxReduction);
        float height = texture.getHeight() * (1 - hitboxReduction);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public abstract void update(float delta);

    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y);
        }
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) this.isDead = true;
    }

    // Actualiza el hitbox centrado en la textura
    protected void updateBounds() {
        float hitboxReduction = 0.3f;
        float width = texture.getWidth() * (1 - hitboxReduction);
        float height = texture.getHeight() * (1 - hitboxReduction);
        float centerX = x + (texture.getWidth() - width) / 2;
        float centerY = y + (texture.getHeight() - height) / 2;
        bounds.set(centerX, centerY, width, height);
    }

    // Verifica si salió de la pantalla (abajo)
    protected boolean isOutOfScreen() {
        return y < -texture.getHeight();
    }

    public boolean isDead() { return isDead; }
    public void setDead(boolean dead) { this.isDead = dead; }
    public float getX() { return x; }
    public float getY() { return y; }
    public Rectangle getBounds() { return bounds; }
    public int getScoreValue() { return scoreValue; }
}