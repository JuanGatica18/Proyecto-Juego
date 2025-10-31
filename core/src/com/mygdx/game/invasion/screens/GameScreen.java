package com.mygdx.game.invasion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import java.util.Iterator;
import com.mygdx.game.invasion.entities.Bullet;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer; // Ya no necesario para entidades, pero lo dejamos
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.InvasionGame;
import com.mygdx.game.invasion.entities.Enemy;
import com.mygdx.game.invasion.entities.FastEnemy;
import com.mygdx.game.invasion.entities.PlayerShip;
import com.mygdx.game.invasion.entities.TankEnemy;
import com.mygdx.game.invasion.managers.TextureManager; // ¡Nuevo import!

public class GameScreen implements Screen {

    private final InvasionGame game;

    private PlayerShip player;
    private Array<Enemy> enemies;
    private Array<Bullet> playerBullets; // ¡LISTA DE BALAS!

    // private ShapeRenderer shapeRenderer; // Ya no lo usaremos para entidades, puedes borrarlo si quieres

    public GameScreen(InvasionGame game) {
        this.game = game;
        TextureManager.getInstance();

        this.player = new PlayerShip(Gdx.graphics.getWidth() / 2f, 50f);
        this.playerBullets = new Array<>(); // ¡Crea la lista de balas!
        this.enemies = new Array<>();
        this.enemies.add(new FastEnemy(100, 400));
        this.enemies.add(new TankEnemy(250, 450));
        this.enemies.add(new FastEnemy(400, 400));
    }

    @Override
    public void render(float delta) {
        // --- 1. LÓGICA (Update) ---

        // Pasa la lista de balas al jugador para que pueda disparar
        player.update(delta, playerBullets);

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

        // --- 3. LÓGICA DE LIMPIEZA (Eliminar muertos) ---
        removeDeadEntities();

        // --- 4. CHEQUEO DE GAME OVER ---
        if (player.isDead()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
            return;
        }

        // --- 5. DIBUJADO (Render) ---
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);

        game.batch.begin();

        player.render(game.batch);
        for (Enemy enemy : enemies) { enemy.render(game.batch); }
        for (Bullet bullet : playerBullets) { bullet.render(game.batch); }

        // Dibuja el HUD (Vidas)
        game.font.draw(game.batch, "VIDAS: " + player.getLives(), 10, Gdx.graphics.getHeight() - 10);

        game.batch.end();
    }

    private void checkCollisions() {
        // Colisión: Balas del Jugador vs Enemigos
        for (Bullet bullet : playerBullets) {
            for (Enemy enemy : enemies) {
                if (bullet.getBounds().overlaps(enemy.getBounds())) {
                    enemy.takeDamage(100); // El enemigo recibe daño (100 para matarlos de 1 golpe)
                    bullet.setDead(true);    // La bala muere
                }
            }
        }

        // Colisión: Enemigos vs Jugador
        for (Enemy enemy : enemies) {
            if (enemy.getBounds().overlaps(player.getBounds())) {
                player.takeHit();     // El jugador pierde una vida
                enemy.setDead(true);  // El enemigo muere (como un kamikaze)

                // (Aquí podrías añadir invencibilidad temporal)
            }
        }
    }

    private void removeDeadEntities() {
        // Usa un Iterador para eliminar balas muertas (más seguro)
        for (Iterator<Bullet> iter = playerBullets.iterator(); iter.hasNext(); ) {
            if (iter.next().isDead()) {
                iter.remove();
            }
        }

        // Usa un Iterador para eliminar enemigos muertos
        for (Iterator<Enemy> iter = enemies.iterator(); iter.hasNext(); ) {
            if (iter.next().isDead()) {
                iter.remove();
            }
        }
    }
    
    @Override
    public void dispose() {
        // ¡Importante! Libera los recursos del TextureManager al final del juego
        TextureManager.getInstance().dispose();
        // Si ShapeRenderer seguía activo, también lo liberarías:
        // if (shapeRenderer != null) shapeRenderer.dispose();
    }

    // --- Métodos de Screen vacíos ---
    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
}