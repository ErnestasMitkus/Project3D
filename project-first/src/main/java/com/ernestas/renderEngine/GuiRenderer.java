package com.ernestas.renderEngine;

import com.ernestas.guis.GuiTexture;
import com.ernestas.models.RawModel;
import com.ernestas.renderEngine.Loader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class GuiRenderer {

    private final RawModel quad;

    public GuiRenderer(Loader loader) {
        float[] positions = {
            -1,  1,
            -1, -1,
             1,  1,
             1, -1
        };
        quad = loader.loadToVAO(positions);
    }

    public void render(List<GuiTexture> guis) {
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);

        for (GuiTexture gui: guis) {
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

}
