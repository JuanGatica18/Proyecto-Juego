package com.mygdx.game.invasion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import java.util.Iterator;
import java.util.Random;
import com.mygdx.game.invasion.entities.Bullet;
import com.mygdx.game.invasion.entities.PowerUp;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.InvasionGame;
import com.mygdx.game.invasion.entities.Enemy;
import com.mygdx.game.invasion.entities.PlayerShip;
import com.mygdx.game.invasion.managers.TextureManager;
import com.mygdx.game.invasion.managers.EnemySpawner;
import com.mygdx.game.invasion.managers.DifficultyManager;
import com.mygdx.game.invasion.strategies.shoot.DoubleShoot;
import com.mygdx.game.invasion.ui.HUD;

public class GameScreen implements Screen {

    private final InvasionGame game;

    private PlayerShip player;
    private Array<Enemy> enemies;
    private Array<Bullet> playerBullets;
    private Array<PowerUp> powerUps;
    private EnemySpawner enemySpawner;
    private DifficultyManager difficultyManager;
    private HUD hud;
    private Random random;

    private float powerUpDuration = 10f; // Duración del power-up en segundos
    private float powerUpTimer = 0;
    private boolean hasPowerUp = false;

    public GameScreen(InvasionGame game) {
        this.game = game;
        this.random = new Random();

        this.player = new PlayerShip(Gdx.graphics.getWidth() / 2f, 50f);
        this.playerBullets = new Array<>();
        this.enemies = new Array<>();
        this.powerUps = new Array<>();
        this.difficultyManager = new DifficultyManager();
        this.enemySpawner = new EnemySpawner(difficultyManager);
        this.hud = new HUD();
    }

    @Override
    public void render(float delta) {
        // --- 1. LÓGICA (Update) ---

        player.update(delta, playerBullets);

        // Actualiza el sistema de dificultad
        difficultyManager.update(delta);

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

        // Actualiza power-ups
        for (PowerUp powerUp : powerUps) {
            powerUp.update(delta);
        }

        // Actualiza el timer del power-up
        if (hasPowerUp) {
            powerUpTimer += delta;
            if (powerUpTimer >= powerUpDuration) {
                hasPowerUp = false;
                powerUpTimer = 0;
                player.setWeapon(new com.mygdx.game.invasion.strategies.shoot.SingleShoot());
            }
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
        for (PowerUp powerUp : powerUps) {
            powerUp.render(game.batch);
        }

        // Dibuja el HUD con nivel e información de power-up
        hud.render(game.batch, player.getLives(), difficultyManager.getCurrentLevel(), hasPowerUp, powerUpTimer, powerUpDuration);

        game.batch.end();
    }

    private void checkCollisions() {
        // Colisión: Balas del Jugador vs Enemigos
        for (Bullet bullet : playerBullets) {
            for (Enemy enemy : enemies) {
                if (bullet.getBounds().overlaps(enemy.getBounds())) {
                    enemy.takeDamage(100);
                    bullet.setDead(true);

                    // Si el enemigo murió, suma puntos y posibilidad de soltar power-up
                    if (enemy.isDead()) {
                        hud.addScore(enemy.getScoreValue());

                        // 15% de probabilidad de soltar power-up
                        if (random.nextFloat() < 0.15f) {
                            dropPowerUp(enemy.getX(), enemy.getY());
                        }
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

        // Colisión: Jugador vs PowerUps
        for (PowerUp powerUp : powerUps) {
            if (powerUp.getBounds().overlaps(player.getBounds())) {
                collectPowerUp(powerUp);
                powerUp.setDead(true);
            }
        }
    }

    private void dropPowerUp(float x, float y) {
        PowerUp powerUp = new PowerUp(
                x,
                y,
                PowerUp.PowerUpType.DOUBLE_SHOOT,
                TextureManager.getInstance().getTexture("powerup_double")
        );
        powerUps.add(powerUp);
    }

    private void collectPowerUp(PowerUp powerUp) {
        if (powerUp.getType() == PowerUp.PowerUpType.DOUBLE_SHOOT) {
            player.setWeapon(new DoubleShoot());
            hasPowerUp = true;
            powerUpTimer = 0;
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

        // Elimina power-ups muertos
        for (Iterator<PowerUp> iter = powerUps.iterator(); iter.hasNext(); ) {
            if (iter.next().isDead()) {
                iter.remove();
            }
        }
    }

    @Override
    public void dispose() {
        hud.dispose();
    }

    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
}