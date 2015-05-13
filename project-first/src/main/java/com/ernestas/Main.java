package com.ernestas;

import com.ernestas.entities.Camera;
import com.ernestas.entities.Entity;
import com.ernestas.entities.Light;
import com.ernestas.models.TexturedModel;
import com.ernestas.renderEngine.*;
import com.ernestas.models.RawModel;
import com.ernestas.shaders.StaticShader;
import com.ernestas.terrains.Terrain;
import com.ernestas.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String args[]) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();

        Light light = new Light(new Vector3f(0, 20, 0), new Vector3f(0.7f, 0.85f, 0.85f));
        Camera camera = new Camera();
        MasterRenderer renderer = new MasterRenderer();

        RawModel model = OBJLoader.loadObjModel("tree", loader);
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));

        RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
        TexturedModel grass = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);

        RawModel fernModel = OBJLoader.loadObjModel("fern", loader);
        TexturedModel fern = new TexturedModel(fernModel, new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);


        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));
            entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 1));
            entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 0.6f));
        }

        ModelTexture grassTerrainTexture = new ModelTexture(loader.loadTexture("grass"));

        List<Terrain> terrains = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Terrain grassTerrain = new Terrain(i / 5 - 2, i % 5 - 3, loader, grassTerrainTexture);
            terrains.add(grassTerrain);
        }

        while (!Display.isCloseRequested()) {
            camera.move();

            for (Terrain terrain : terrains) {
                renderer.processTerrain(terrain);
            }

            // render all entities here
            for (Entity entity: entities) {
                renderer.processEntity(entity);
            }

            renderer.render(light, camera);
            DisplayManager.updateDisplay();

        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
