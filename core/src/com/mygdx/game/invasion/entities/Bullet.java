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
    private Rectangle bounds;
    private boolean isDead = false;

    public Bullet(float x, float y) {
        this.texture = TextureManager.getInstance().getTexture("player_bullet");
        this.x = x - texture.getWidth() / 2f;
        this.y = y;

        // Hitbox un poco más pequeño para colisiones más precisas
        float hitboxReduction = 0.2f;
        float width = texture.getWidth() * (1 - hitboxReduction);
        float height = texture.getHeight() * (1 - hitboxReduction);
        this.bounds = new Rectangle(this.x, this.y, width, height);
    }

    public void update(float delta) {
        y += speed * delta;

        if (y > Gdx.graphics.getHeight()) {
            isDead = true;
        }

        // Actualiza hitbox centrado
        float hitboxReduction = 0.2f;
        float width = texture.getWidth() * (1 - hitboxReduction);
        float height = texture.getHeight() * (1 - hitboxReduction);
        float centerX = x + (texture.getWidth() - width) / 2;
        float centerY = y + (texture.getHeight() - height) / 2;
        bounds.set(centerX, centerY, width, height);
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