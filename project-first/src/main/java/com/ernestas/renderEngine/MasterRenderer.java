package com.ernestas.renderEngine;

import com.ernestas.entities.Camera;
import com.ernestas.entities.Entity;
import com.ernestas.entities.Light;
import com.ernestas.models.TexturedModel;
import com.ernestas.shaders.StaticShader;
import com.ernestas.shaders.TerrainShader;
import com.ernestas.terrains.Terrain;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    private static final float FOV = 70; //Field of view
    private static final float NEAR_PLANE = 0.1f; //Minimum near you can see
    private static final float FAR_PLANE = 1000; //How far you can see

    private Matrix4f projectionMatrix;

    private StaticShader entityShader = new StaticShader();
    private EntityRenderer entityRenderer;

    private TerrainShader terrainShader = new TerrainShader();
    private TerrainRenderer terrainRenderer;

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();


    public MasterRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    public void render(Light light, Camera camera) {
        prepare();

        entityShader.start();
        entityShader.loadLight(light);
        entityShader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        entityShader.stop();

        terrainShader.start();
        terrainShader.loadLight(light);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();

        entities.clear();
        terrains.clear();
    }

    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
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
        entityShader.cleanUp();
        terrainShader.cleanUp();
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0, 0, 0, 1);
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustumLength = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = xScale;
        projectionMatrix.m11 = yScale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength);
        projectionMatrix.m33 = 0;
    }

}
