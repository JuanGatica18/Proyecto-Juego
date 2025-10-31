package com.mygdx.game.invasion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import java.util.Iterator;
import com.mygdx.game.invasion.entities.Bullet;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.InvasionGame;
import com.mygdx.game.invasion.entities.Enemy;
import com.mygdx.game.invasion.entities.PlayerShip;
import com.mygdx.game.invasion.managers.TextureManager;
import com.mygdx.game.invasion.managers.EnemySpawner;
import com.mygdx.game.invasion.ui.HUD;

public class GameScreen implements Screen {

    private final InvasionGame game;

    private PlayerShip player;
    private Array<Enemy> enemies;
    private Array<Bullet> playerBullets;
    private EnemySpawner enemySpawner;
    private HUD hud;

    public GameScreen(InvasionGame game) {
        this.game = game;

        this.player = new PlayerShip(Gdx.graphics.getWidth() / 2f, 50f);
        this.playerBullets = new Array<>();
        this.enemies = new Array<>();
        this.enemySpawner = new EnemySpawner();
        this.hud = new HUD();
    }

    @Override
    public void render(float delta) {
        // --- 1. LÓGICA (Update) ---

        player.update(delta, playerBullets);

        // Actualiza el spawner de enemigos
        enemySpawner.update(delta, enemies);

        // Actualiza enemigos
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }

        // Actualiza balas
        for (Bullet bullet : playerBullets) {
            bullet.update(delta);
        }

        // --- 2. LÓGICA DE COLISIÓN ---
        checkCollisions();

        // --- 3. LÓGICA DE LIMPIEZA ---
        removeDeadEntities();

        // --- 4. CHEQUEO DE GAME OVER ---
        if (player.isDead()) {
            game.setScreen(new GameOverScreen(game, hud.getScore()));
            dispose();
            return;
        }

        // --- 5. DIBUJADO (Render) ---
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);

        game.batch.begin();

        player.render(game.batch);
        for (Enemy enemy : enemies) {
            enemy.render(game.batch);
        }
        for (Bullet bullet : playerBullets) {
            bullet.render(game.batch);
        }

        // Dibuja el HUD
        hud.render(game.batch, player.getLives());

        game.batch.end();
    }

    private void checkCollisions() {
        // Colisión: Balas del Jugador vs Enemigos
        for (Bullet bullet : playerBullets) {
            for (Enemy enemy : enemies) {
                if (bullet.getBounds().overlaps(enemy.getBounds())) {
                    enemy.takeDamage(100);
                    bullet.setDead(true);

                    // Si el enemigo murió, suma puntos
                    if (enemy.isDead()) {
                        hud.addScore(enemy.getScoreValue());
                    }
                }
            }
        }

        // Colisión: Enemigos vs Jugador (solo si no es invencible)
        if (!player.isInvincible()) {
            for (Enemy enemy : enemies) {
                if (enemy.getBounds().overlaps(player.getBounds())) {
                    player.takeHit();
                    enemy.setDead(true);
                }
            }
        }
    }

    private void removeDeadEntities() {
        // Elimina balas muertas
        for (Iterator<Bullet> iter = playerBullets.iterator(); iter.hasNext(); ) {
            if (iter.next().isDead()) {
                iter.remove();
            }
        }

        // Elimina enemigos muertos
        for (Iterator<Enemy> iter = enemies.iterator(); iter.hasNext(); ) {
            if (iter.next().isDead()) {
                iter.remove();
            }
        }
    }

    @Override
    public void dispose() {
        // NO disponemos del TextureManager aquí porque es un Singleton
        // que se usa en múltiples pantallas
        hud.dispose();
    }

    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
}