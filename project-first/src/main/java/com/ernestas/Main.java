package com.ernestas;

import com.ernestas.renderEngine.DisplayManager;
import org.lwjgl.opengl.Display;

public class Main {

    public static void main(String args[]) {
        DisplayManager.createDisplay();

        while (!Display.isCloseRequested()) {

            DisplayManager.updateDisplay();

        }
        DisplayManager.closeDisplay();
    }

}
