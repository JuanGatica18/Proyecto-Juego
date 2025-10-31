package com.mygdx.game.invasion.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PowerUp {

    public enum PowerUpType {
        DOUBLE_SHOOT
    }

    private float x, y;
    private float speed = 150f;
    private Texture texture;
    private Rectangle bounds;
    private boolean isDead = false;
    private PowerUpType type;

    public PowerUp(float x, float y, PowerUpType type, Texture texture) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.texture = texture;
        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta) {
        // El power-up cae lentamente
        y -= speed * delta;

        // Se elimina si sale de la pantalla
        if (y < -texture.getHeight()) {
            isDead = true;
        }

        bounds.set(x, y, texture.getWidth(), texture.getHeight());
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

    public PowerUpType getType() {
        return type;
    }
}