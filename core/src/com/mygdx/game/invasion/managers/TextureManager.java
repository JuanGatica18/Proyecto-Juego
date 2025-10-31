package com.mygdx.game.invasion.managers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import java.util.Map;

// GM1.6: Encapsulamiento (todos los metodos son public, atributos privados)
// Principio S (Single Responsibility): Solo se encarga de texturas
public class TextureManager {

    private static TextureManager instance; // Instancia única (Singleton)
    private Map<String, Texture> textures; // Mapa para guardar las texturas

    // Constructor privado para evitar que se instancie directamente
    private TextureManager() {
        textures = new HashMap<>();
        loadTextures(); // Carga las texturas al iniciar
    }

    // Método para obtener la instancia única
    public static TextureManager getInstance() {
        if (instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }

    // Carga todas las texturas que vamos a usar
    private void loadTextures() {
        textures.put("player_ship", new Texture("nave.png"));
        textures.put("fast_enemy", new Texture("enemigo_rapido.png"));
        textures.put("tank_enemy", new Texture("enemigo_tanque.png"));
        textures.put("player_bullet", new Texture("bala.png"));

        // Aquí puedes añadir más texturas: "bullet.png", "powerup.png", etc.
    }

    // Obtiene una textura por su nombre
    public Texture getTexture(String name) {
        if (!textures.containsKey(name)) {
            Gdx.app.error("TextureManager", "Texture not found: " + name);
            // Podrías devolver una textura por defecto o null
            return null;
        }
        return textures.get(name);
    }

    // Libera todas las texturas cargadas
    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        textures.clear();
    }
}