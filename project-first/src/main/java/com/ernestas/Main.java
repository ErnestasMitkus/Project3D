package com.ernestas;

import com.ernestas.models.TexturedModel;
import com.ernestas.renderEngine.DisplayManager;
import com.ernestas.renderEngine.Loader;
import com.ernestas.models.RawModel;
import com.ernestas.renderEngine.Renderer;
import com.ernestas.shaders.StaticShader;
import com.ernestas.textures.ModelTexture;
import org.lwjgl.opengl.Display;

public class Main {

    public static void main(String args[]) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        StaticShader shader = new StaticShader();

        float[] vertices = {
            -0.5f, 0.5f, 0f,    //V0
            -0.5f, -0.5f, 0f,   //V1
            0.5f, -0.5f, 0f,    //V2
            0.5f, 0.5f, 0f,     //V3
        };

        int[] indices = {
            0, 1, 3,    //Top left triangle(V0, V1, V3)
            3, 1, 2     //Bottom right triangle(V3, V1, V2)
        };

        float[] textureCoords = {
            0, 0,   //V0
            0, 1,   //V1
            1, 1,   //V2
            1, 0    //V3
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("doge"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while (!Display.isCloseRequested()) {
            renderer.prepare();
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();

        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
