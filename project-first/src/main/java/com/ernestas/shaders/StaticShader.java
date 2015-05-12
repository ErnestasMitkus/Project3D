package com.ernestas.shaders;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "shaders/fragmentShader.txt";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
