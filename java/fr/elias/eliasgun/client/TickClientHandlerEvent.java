package fr.elias.eliasgun.client;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import org.lwjgl.opengl.GL11;

import fr.elias.eliasgun.common.EliasGuns;
import fr.elias.eliasgun.common.ItemGun;

public class TickClientHandlerEvent
{
	public ArrayList<Class<? extends Item>> bowAnimationLockedItems = new ArrayList();
	final Minecraft minecraft = Minecraft.getMinecraft();
	@SubscribeEvent
	public void renderEvent(RenderGameOverlayEvent event)
	{
		if(event.isCancelable() || event.type == ElementType.EXPERIENCE)
		{
			return;
		}
		if(minecraft.currentScreen == null)
		{
			showAmmo();
		}
	}
	
	public void showAmmo()
	{
		/*ItemStack is = minecraft.thePlayer.getCurrentEquippedItem();
		if(is != null)
		{
			if(is.getItem() instanceof ItemGun)
			{
				ItemGun gun = (ItemGun) is.getItem();
				if(minecraft.thePlayer.inventory.hasItemStack(new ItemStack(EliasGuns.gun_bullet, 15)))
				{
					minecraft.ingameGUI.drawString(minecraft.fontRendererObj, "Current Ammo : "+ (15 - gun.ammoTick) + "/" + gun.ammoMax, 220, 220, 0xFFFFFF);
				}else{
					minecraft.ingameGUI.drawString(minecraft.fontRendererObj, "Current Ammo : "+ "0/" + gun.ammoMax, 220, 220, 0xFFFFFF);
				}
			}
		}*/
	}
	
    protected void bindBlur(ScaledResolution p_180476_1_, String s, float alpha)
    {
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.0033333333F * alpha);
        GlStateManager.enableAlpha();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.01f);
        minecraft.getTextureManager().bindTexture(new ResourceLocation("eliasguns:" + s));
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.startDrawingQuads();
        worldrenderer.addVertexWithUV(0.0D, (double)p_180476_1_.getScaledHeight(), -90.0D, 0.0D, 1.0D);
        worldrenderer.addVertexWithUV((double)p_180476_1_.getScaledWidth(), (double)p_180476_1_.getScaledHeight(), -90.0D, 1.0D, 1.0D);
        worldrenderer.addVertexWithUV((double)p_180476_1_.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
        worldrenderer.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
    }
    
	@SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event)
    {
		if ((event.side.isClient()) && (event.phase.equals(TickEvent.Phase.END)))
		{
			ItemStack is = event.player.getCurrentEquippedItem();
			if ((is != null) && ((event.player != Minecraft.getMinecraft().getRenderViewEntity()) || (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0)) && (isItemBowAnimationLocked(is.getItem())))
			{
				if (event.player.getItemInUseCount() <= 0)
				{
					event.player.clearItemInUse();
					event.player.setItemInUse(is, 2147483647);
				}
        	}
		}
    }
	public void registerBowAnimationLockedItem(Class<? extends Item> clz)
	{
		if (!this.bowAnimationLockedItems.contains(clz))
		{
			this.bowAnimationLockedItems.add(clz);
		}
	}
	public boolean isItemBowAnimationLocked(Item item)
	{
		for (Class clz : this.bowAnimationLockedItems)
		{
			if (clz.isInstance(item))
			{
				return true;
			}
		}
		return false;
	}
}
