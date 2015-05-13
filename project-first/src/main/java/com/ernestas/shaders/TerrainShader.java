package com.ernestas.shaders;

import com.ernestas.entities.Camera;
import com.ernestas.entities.Light;
import com.ernestas.shaders.ShaderProgram;
import com.ernestas.toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class TerrainShader extends ShaderProgram {

    private static final String VERTEX_FILE = "shaders/terrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "shaders/terrainFragmentShader.txt";

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightPosition;
    private int locationLightColor;
    private int locationShineDamper;
    private int locationReflectivity;
    private int locationSkyColor;
    private int locationBackgroundTexture;
    private int locationrTexture;
    private int locationgTexture;
    private int locationbTexture;
    private int locationBlendMap;

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationLightPosition = super.getUniformLocation("lightPosition");
        locationLightColor = super.getUniformLocation("lightColor");
        locationShineDamper = super.getUniformLocation("shineDamper");
        locationReflectivity = super.getUniformLocation("reflectivity");
        locationSkyColor = super.getUniformLocation("skyColor");
        locationBackgroundTexture = super.getUniformLocation("backgroundTexture");
        locationrTexture = super.getUniformLocation("rTexture");
        locationgTexture = super.getUniformLocation("gTexture");
        locationbTexture = super.getUniformLocation("bTexture");
        locationBlendMap = super.getUniformLocation("blendMap");
    }

    public void connectTextureUnits() {
        super.loadInt(locationBackgroundTexture, 0);
        super.loadInt(locationrTexture, 1);
        super.loadInt(locationgTexture, 2);
        super.loadInt(locationbTexture, 3);
        super.loadInt(locationBlendMap, 4);
    }

    public void loadSkyColor(float r, float g, float b) {
        super.loadVector(locationSkyColor, new Vector3f(r, g, b));
    }

    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(locationShineDamper, damper);
        super.loadFloat(locationReflectivity, reflectivity);
    }

    public void loadLight(Light light) {
        super.loadVector(locationLightPosition, light.getPosition());
        super.loadVector(locationLightColor, light.getColor());
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(locationViewMatrix, viewMatrix);
    }
}
