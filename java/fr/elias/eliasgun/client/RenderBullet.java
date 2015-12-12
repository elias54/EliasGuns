package fr.elias.eliasgun.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import fr.elias.eliasgun.common.EntityBullet;

public class RenderBullet extends Render
{
	protected static final ResourceLocation texture = new ResourceLocation("eliasguns:textures/entity/itemBullets.png");
	public RenderBullet()
	{
		super(Minecraft.getMinecraft().getRenderManager());
	}
	
    public void renderArrow(EntityBullet entitybullet, double d, double d1, double d2, float f, float f1)
    {
        this.bindEntityTexture(entitybullet);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef((entitybullet.prevRotationYaw + (entitybullet.rotationYaw - entitybullet.prevRotationYaw) * f1) - 90F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entitybullet.prevRotationPitch + (entitybullet.rotationPitch - entitybullet.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        int i = 0;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (float)(0 + i * 10) / 32F;
        float f5 = (float)(5 + i * 10) / 32F;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (float)(5 + i * 10) / 32F;
        float f9 = (float)(10 + i * 10) / 32F;
        float f10 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glRotatef(45F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(-7D, -2D, -2D, f6, f8);
        worldRenderer.addVertexWithUV(-7D, -2D, 2D, f7, f8);
        worldRenderer.addVertexWithUV(-7D, 2D, 2D, f7, f9);
        worldRenderer.addVertexWithUV(-7D, 2D, -2D, f6, f9);
        tessellator.draw();
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(-7D, 2D, -2D, f6, f8);
        worldRenderer.addVertexWithUV(-7D, 2D, 2D, f7, f8);
        worldRenderer.addVertexWithUV(-7D, -2D, 2D, f7, f9);
        worldRenderer.addVertexWithUV(-7D, -2D, -2D, f6, f9);
        tessellator.draw();

        for (int j = 0; j < 4; j++)
        {
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertexWithUV(-8D, -2D, 0.0D, f2, f4);
            worldRenderer.addVertexWithUV(8D, -2D, 0.0D, f3, f4);
            worldRenderer.addVertexWithUV(8D, 2D, 0.0D, f3, f5);
            worldRenderer.addVertexWithUV(-8D, 2D, 0.0D, f2, f5);
            tessellator.draw();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
	
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        renderArrow((EntityBullet)entity, d, d1, d2, f, f1);
    }
    
	protected ResourceLocation getEntityTexture(EntityBullet entity)
	{return texture;}
	protected ResourceLocation getEntityTexture(Entity entity)
	{return getEntityTexture((EntityBullet) entity);}
}
