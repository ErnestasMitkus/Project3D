package com.ernestas;

import com.ernestas.renderEngine.DisplayManager;
import com.ernestas.renderEngine.Loader;
import com.ernestas.renderEngine.RawModel;
import com.ernestas.renderEngine.Renderer;
import org.lwjgl.opengl.Display;

public class Main {

    public static void main(String args[]) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,

            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,
            -0.5f, 0.5f, 0f
        };

        RawModel model = loader.loadToVAO(vertices);

        while (!Display.isCloseRequested()) {
            renderer.prepare();
            renderer.render(model);
            DisplayManager.updateDisplay();

        }

        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
