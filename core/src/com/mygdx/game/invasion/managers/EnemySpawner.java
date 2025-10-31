package com.mygdx.game.invasion.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.invasion.entities.Enemy;
import com.mygdx.game.invasion.entities.FastEnemy;
import com.mygdx.game.invasion.entities.TankEnemy;
import java.util.Random;

public class EnemySpawner {

    private float spawnTimer;
    private float spawnInterval = 2f; // Cada 2 segundos aparece un enemigo
    private Random random;
    private int maxEnemies = 10; // Máximo de enemigos en pantalla

    public EnemySpawner() {
        this.random = new Random();
        this.spawnTimer = 0;
    }

    public void update(float delta, Array<Enemy> enemies) {
        spawnTimer += delta;

        // Si es tiempo de spawn y no hay demasiados enemigos
        if (spawnTimer >= spawnInterval && enemies.size < maxEnemies) {
            spawnEnemy(enemies);
            spawnTimer = 0;

            // Aumenta la dificultad con el tiempo (spawn más rápido)
            if (spawnInterval > 0.5f) {
                spawnInterval -= 0.01f;
            }
        }
    }

    private void spawnEnemy(Array<Enemy> enemies) {
        // Posición X aleatoria en la pantalla
        float x = random.nextInt(Gdx.graphics.getWidth() - 64);

        // Aparece arriba de la pantalla
        float y = Gdx.graphics.getHeight() + 50;

        // 70% probabilidad de FastEnemy, 30% de TankEnemy
        if (random.nextFloat() < 0.7f) {
            enemies.add(new FastEnemy(x, y));
        } else {
            enemies.add(new TankEnemy(x, y));
        }
    }

    public void setSpawnInterval(float interval) {
        this.spawnInterval = interval;
    }

    public void setMaxEnemies(int max) {
        this.maxEnemies = max;
    }
}