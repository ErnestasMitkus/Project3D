package com.ernestas;

import com.ernestas.entities.Camera;
import com.ernestas.entities.Entity;
import com.ernestas.entities.Light;
import com.ernestas.models.TexturedModel;
import com.ernestas.renderEngine.DisplayManager;
import com.ernestas.renderEngine.Loader;
import com.ernestas.models.RawModel;
import com.ernestas.renderEngine.OBJLoader;
import com.ernestas.renderEngine.Renderer;
import com.ernestas.shaders.StaticShader;
import com.ernestas.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Main {

    public static void main(String args[]) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);


        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));

        ModelTexture texture = texturedModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1f);

        Entity entity = new Entity(texturedModel, new Vector3f(0, -5, -25), 0, 0, 0, 1);

        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(0.7f, 0.85f, 0.85f));

        Camera camera = new Camera();

        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            renderer.prepare();
            camera.move();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();

        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
