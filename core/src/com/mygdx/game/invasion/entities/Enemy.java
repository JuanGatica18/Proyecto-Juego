package com.mygdx.game.invasion.entities;

// GM1.4: Clase Abstracta
public abstract class Enemy {

    // GM1.6: Encapsulamiento
    protected float x, y;
    protected int health;
    protected boolean isDead = false;

    public Enemy(float x, float y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
    }

    // GM1.4: Método abstracto
    public abstract void update(float delta);

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) this.isDead = true;
    }

    public boolean isDead() { return isDead; }
    public float getX() { return x; }
    public float getY() { return y; }
}