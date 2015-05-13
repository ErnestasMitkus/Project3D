package com.ernestas;

import com.ernestas.entities.Camera;
import com.ernestas.entities.Entity;
import com.ernestas.entities.Light;
import com.ernestas.models.TexturedModel;
import com.ernestas.renderEngine.*;
import com.ernestas.models.RawModel;
import com.ernestas.shaders.StaticShader;
import com.ernestas.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Main {

    public static void main(String args[]) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();


        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));

        ModelTexture texture = texturedModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1f);

        Entity entity = new Entity(texturedModel, new Vector3f(0, -5, -25), 0, 0, 0, 1);

        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(0.7f, 0.85f, 0.85f));

        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()) {
            camera.move();

            // render all entities here
            renderer.processEntity(entity);

            renderer.render(light, camera);
            DisplayManager.updateDisplay();

        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
