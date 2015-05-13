package com.ernestas;

import com.ernestas.entities.Camera;
import com.ernestas.entities.Entity;
import com.ernestas.entities.Light;
import com.ernestas.entities.Player;
import com.ernestas.guis.GuiTexture;
import com.ernestas.renderEngine.GuiRenderer;
import com.ernestas.models.TexturedModel;
import com.ernestas.objConverter.OBJFileLoader;
import com.ernestas.renderEngine.*;
import com.ernestas.terrains.Terrain;
import com.ernestas.textures.ModelTexture;
import com.ernestas.textures.TerrainTexture;
import com.ernestas.textures.TerrainTexturePack;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String args[]) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer();


        //*******************TERRAIN TEXTURE STUFF******************

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        List<Terrain> terrains = new ArrayList<>();
        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");
        terrains.add(new Terrain(-1, -1, loader, texturePack, blendMap, "heightMap"));
        terrains.add(new Terrain(-1, 0, loader, texturePack, blendMap, "heightMap"));
        terrains.add(new Terrain(0, 0, loader, texturePack, blendMap, "heightMap"));
        terrains.add(terrain);
        //**********************************************************


        //*******************MODEL STUFF******************
        TexturedModel staticModel = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("tree")),
                new ModelTexture(loader.loadTexture("tree")));

        TexturedModel grass = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("grassModel")),
                new ModelTexture(loader.loadTexture("grassTexture")));

        TexturedModel flower = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("grassModel")),
                new ModelTexture(loader.loadTexture("flower")));

        TexturedModel fern = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("fern")),
                new ModelTexture(loader.loadTexture("fern"), 2));

        TexturedModel bobble = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("lowPolyTree")),
            new ModelTexture(loader.loadTexture("lowPolyTree")));

        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        flower.getTexture().setHasTransparency(true);
        flower.getTexture().setUseFakeLighting(true);
        fern.getTexture().setHasTransparency(true);


        List<Entity> entities = new ArrayList<>();
        Random random = new Random(676452);
        for (int i = 0; i < 400; i++) {
            if (i % 10 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = Terrain.getCurrentTerrain(terrains, x, z).getHeightOfTerrain(x, z);
                entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));
            }
            if (i % 5 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = Terrain.getCurrentTerrain(terrains, x, z).getHeightOfTerrain(x, z);
                entities.add(new Entity(bobble, new Vector3f(x, y, z), 0, random.nextFloat() * 360,
                    0, random.nextFloat() * 0.1f + 0.6f));

                x = random.nextFloat() * 800 - 400;
                z = random.nextFloat() * -600;
                y = Terrain.getCurrentTerrain(terrains, x, z).getHeightOfTerrain(x, z);
                entities.add(new Entity(staticModel, new Vector3f(x, y, z), 0, 0, 0, random.nextFloat() * 1 + 4));
            }
        }
        //************************************************

        //**************** PLAYER ************************
        TexturedModel stanfordBunny = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("stanfordBunny")),
                new ModelTexture(loader.loadTexture("white")));
        Player player = new Player(stanfordBunny, new Vector3f(100, 0, -50), 0, 0, 0, 1);
        //************************************************


        //**************** GUIS ************************
        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"),
            new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        GuiTexture gui2 = new GuiTexture(loader.loadTexture("thinmatrix"),
            new Vector2f(0.3f, 0.58f), new Vector2f(0.4f, 0.4f));

        guis.add(gui);
        guis.add(gui2);

        GuiRenderer guiRenderer = new GuiRenderer(loader);
        //**********************************************

        Camera camera = new Camera(player);
        Light light = new Light(new Vector3f(20000, 40000, 20000), new Vector3f(1, 1, 1));

        while (!Display.isCloseRequested()) {
            camera.move();
            player.move(Terrain.getCurrentTerrain(terrains, player.getPosition().x, player.getPosition().z));
            renderer.processEntity(player);

            for (Terrain terrainObj : terrains) {
                renderer.processTerrain(terrainObj);
            }

            // render all entities here
            for (Entity entity: entities) {
                renderer.processEntity(entity);
            }

            renderer.render(light, camera);

            guiRenderer.render(guis);

            DisplayManager.updateDisplay();

        }

        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
