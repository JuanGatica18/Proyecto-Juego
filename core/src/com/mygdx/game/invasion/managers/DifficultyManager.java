package com.mygdx.game.invasion.managers;

public class DifficultyManager {

    private float gameTime = 0;
    private int currentLevel = 1;
    private float levelDuration = 30f; // Cada 30 segundos sube de nivel

    // Valores base
    private float baseSpawnInterval = 2.0f;
    private int baseMaxEnemies = 8;

    // Límites para que no sea imposible
    private final float minSpawnInterval = 0.7f;
    private final int maxEnemiesLimit = 15;

    public void update(float delta) {
        gameTime += delta;

        // Calcula el nivel actual
        int newLevel = (int)(gameTime / levelDuration) + 1;
        if (newLevel > currentLevel) {
            currentLevel = newLevel;
        }
    }

    public float getSpawnInterval() {
        // Reduce el intervalo de spawn gradualmente
        float interval = baseSpawnInterval - (currentLevel - 1) * 0.15f;
        return Math.max(interval, minSpawnInterval);
    }

    public int getMaxEnemies() {
        // Aumenta el número máximo de enemigos
        int max = baseMaxEnemies + (currentLevel - 1);
        return Math.min(max, maxEnemiesLimit);
    }

    public float getZigZagChance() {
        // Probabilidad de que aparezca un ZigZag (aumenta con el nivel)
        return Math.min(0.15f + currentLevel * 0.02f, 0.35f);
    }

    public float getTankChance() {
        // Probabilidad de Tank Enemy
        return Math.min(0.25f + currentLevel * 0.01f, 0.40f);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public float getGameTime() {
        return gameTime;
    }
}