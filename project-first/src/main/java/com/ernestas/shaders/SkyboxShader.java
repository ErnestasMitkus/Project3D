package com.ernestas.shaders;

import com.ernestas.entities.Camera;
import com.ernestas.toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class SkyboxShader extends ShaderProgram {

    private static final String VERTEX_FILE = "shaders/skyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "shaders/skyboxFragmentShader.txt";

    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationFogColor;

    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0; //Make skybox not move with the world
        matrix.m31 = 0; //camera moves in the completely oposite direction of the world
        matrix.m32 = 0;
        super.loadMatrix(locationViewMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationFogColor = super.getUniformLocation("fogColor");
    }

    public void loadFogColor(Vector3f fogColor) {
        super.loadVector(locationFogColor, fogColor);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}
