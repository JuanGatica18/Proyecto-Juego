package com.mygdx.game.invasion.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.invasion.entities.Enemy;
import com.mygdx.game.invasion.entities.FastEnemy;
import com.mygdx.game.invasion.entities.TankEnemy;
import com.mygdx.game.invasion.entities.ZigZagEnemy;
import java.util.Random;

public class EnemySpawner {

    private float spawnTimer;
    private Random random;
    private DifficultyManager difficultyManager;

    public EnemySpawner(DifficultyManager difficultyManager) {
        this.random = new Random();
        this.spawnTimer = 0;
        this.difficultyManager = difficultyManager;
    }

    public void update(float delta, Array<Enemy> enemies) {
        spawnTimer += delta;

        float spawnInterval = difficultyManager.getSpawnInterval();
        int maxEnemies = difficultyManager.getMaxEnemies();

        // Si es tiempo de spawn y no hay demasiados enemigos
        if (spawnTimer >= spawnInterval && enemies.size < maxEnemies) {
            spawnEnemy(enemies);
            spawnTimer = 0;
        }
    }

    private void spawnEnemy(Array<Enemy> enemies) {
        // Posición X aleatoria en la pantalla
        float x = random.nextInt(Gdx.graphics.getWidth() - 64);

        // Aparece arriba de la pantalla
        float y = Gdx.graphics.getHeight() + 50;

        float rand = random.nextFloat();
        float zigzagChance = difficultyManager.getZigZagChance();
        float tankChance = difficultyManager.getTankChance();

        // Distribución de enemigos según la dificultad
        if (rand < zigzagChance) {
            enemies.add(new ZigZagEnemy(x, y));
        } else if (rand < zigzagChance + tankChance) {
            enemies.add(new TankEnemy(x, y));
        } else {
            enemies.add(new FastEnemy(x, y));
        }
    }
}