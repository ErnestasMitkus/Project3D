package com.ernestas.renderEngine;

import com.ernestas.entities.Camera;
import com.ernestas.entities.Entity;
import com.ernestas.entities.Light;
import com.ernestas.models.TexturedModel;
import com.ernestas.shaders.StaticShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    private StaticShader shader = new StaticShader();
    private Renderer renderer = new Renderer(shader);

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();


    public void render(Light light, Camera camera) {
        renderer.prepare();
        shader.start();
        shader.loadLight(light);
        shader.loadViewMatrix(camera);

        renderer.render(entities);

        shader.stop();
        entities.clear();
    }

    public void processEntity(Entity entity) {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void cleanUp() {
        shader.cleanUp();
    }

}
