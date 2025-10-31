package com.mygdx.game.invasion.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import java.util.Map;

public class TextureManager {

    private static TextureManager instance;
    private Map<String, Texture> textures;
    private boolean disposed = false;

    private TextureManager() {
        textures = new HashMap<>();
        loadTextures();
    }

    public static TextureManager getInstance() {
        if (instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }

    private void loadTextures() {
        try {
            textures.put("player_ship", new Texture("nave.png"));
            textures.put("fast_enemy", new Texture("enemigo_rapido.png"));
            textures.put("tank_enemy", new Texture("enemigo_tanque.png"));
            textures.put("zigzag_enemy", new Texture("enemigo_zigzag.png"));
            textures.put("powerup_double", new Texture("powerup_doble.png"));
            textures.put("player_bullet", new Texture("bala.png"));
        } catch (Exception e) {
            Gdx.app.error("TextureManager", "Error loading textures: " + e.getMessage());
        }
    }

    public Texture getTexture(String name) {
        if (disposed) {
            Gdx.app.error("TextureManager", "Trying to get texture after dispose!");
            return null;
        }

        if (!textures.containsKey(name)) {
            Gdx.app.error("TextureManager", "Texture not found: " + name);
            return null;
        }
        return textures.get(name);
    }

    public void dispose() {
        if (!disposed) {
            for (Texture texture : textures.values()) {
                if (texture != null) {
                    texture.dispose();
                }
            }
            textures.clear();
            disposed = true;
            instance = null; // Permite recrear el Singleton si es necesario
        }
    }
}