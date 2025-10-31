package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Proyecto Invasión");

        // Configuración de ventana con tamaño definido
        config.setWindowedMode(800, 600);

        // Permite redimensionar la ventana
        config.setResizable(true);

        // Opción para maximizar (comentar si da problemas)
        // config.setMaximized(true);

        new Lwjgl3Application(new InvasionGame(), config);
    }
}