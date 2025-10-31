package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Asegúrate de que esta clase esté en el paquete com.mygdx.game
public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Proyecto Invasión");

        // ¡Importante! Asegúrate de que aquí dice "new InvasionGame()"
        new Lwjgl3Application(new InvasionGame(), config);
    }
}