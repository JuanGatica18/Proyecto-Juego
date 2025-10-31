package com.mygdx.game.invasion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.InvasionGame;
import com.mygdx.game.invasion.entities.Enemy;
import com.mygdx.game.invasion.entities.FastEnemy;
import com.mygdx.game.invasion.entities.PlayerShip;
import com.mygdx.game.invasion.entities.TankEnemy;

public class GameScreen implements Screen {

    private final InvasionGame game;
    private float gameOverTimer = 15; // ¡Dura 15 segundos!

    // --- ¡AQUÍ ESTÁ LA MAGIA! ---
    private PlayerShip player; // Tu nave

    // GM1.4 Contexto: Una lista que usa la clase abstracta
    private Array<Enemy> enemies;

    // Para dibujar sin texturas
    private ShapeRenderer shapeRenderer;

    public GameScreen(InvasionGame game) {
        this.game = game;

        // Para dibujar formas (rectángulos)
        this.shapeRenderer = new ShapeRenderer();

        // 1. Crea la nave (GM1.5 Contexto)
        this.player = new PlayerShip(Gdx.graphics.getWidth() / 2f, 50f);

        // 2. Crea los enemigos (GM1.4 Contexto)
        this.enemies = new Array<>();
        this.enemies.add(new FastEnemy(100, 300));
        this.enemies.add(new TankEnemy(250, 350));
        this.enemies.add(new FastEnemy(400, 300));
    }

    @Override
    public void render(float delta) {
        // --- 1. LÓGICA (Update) ---

        // Actualiza el jugador (movimiento, disparos)
        player.update(delta);

        // Actualiza todos los enemigos (Principio de Liskov)
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }

        // Simulación de derrota (igual que antes, pero más largo)
        gameOverTimer -= delta;
        if (gameOverTimer <= 0) {
            game.setScreen(new GameOverScreen(game));
            dispose();
            return;
        }

        // --- 2. DIBUJADO (Render) ---
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1); // Fondo gris

        // Dibuja las formas
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Dibuja la nave (un rectángulo verde)
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(player.getX() - 15, player.getY() - 15, 30, 30);

        // Dibuja los enemigos (rojos y amarillos)
        for (Enemy enemy : enemies) {
            if (enemy instanceof FastEnemy) {
                shapeRenderer.setColor(Color.YELLOW);
            } else if (enemy instanceof TankEnemy) {
                shapeRenderer.setColor(Color.RED);
            }
            shapeRenderer.rect(enemy.getX() - 15, enemy.getY() - 15, 30, 30);
        }

        shapeRenderer.end();

        // Dibuja texto (contador)
        game.batch.begin();
        game.font.draw(game.batch, "¡SOBREVIVE!", 10, Gdx.graphics.getHeight() - 10);
        game.font.draw(game.batch, "Tiempo restante: " + (int)gameOverTimer, 10, Gdx.graphics.getHeight() - 30);
        game.batch.end();
    }

    @Override
    public void dispose() {
        // Limpia el ShapeRenderer
        shapeRenderer.dispose();
    }

    // --- Métodos de Screen vacíos ---
    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
}