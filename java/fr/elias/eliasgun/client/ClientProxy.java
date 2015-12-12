package fr.elias.eliasgun.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import fr.elias.eliasgun.common.CommonProxy;
import fr.elias.eliasgun.common.EliasGuns;
import fr.elias.eliasgun.common.EntityBullet;
import fr.elias.eliasgun.common.EntityDeagleBullet;
import fr.elias.eliasgun.common.EntityGrenade;
import fr.elias.eliasgun.common.EntityGrenadeSmoke;
import fr.elias.eliasgun.common.EntityGrenadeSticky;
import fr.elias.eliasgun.common.ItemWithHoldingLikeBowAnimation;

public class ClientProxy extends CommonProxy
{
	public void render()
	{
		tickClientHandlerEvent = new TickClientHandlerEvent();
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(EliasGuns.smokeGrenade, 0, new ModelResourceLocation("eliasguns:smokeGrenade", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(EliasGuns.grenade, 0, new ModelResourceLocation("eliasguns:grenade", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(EliasGuns.stickGrenade, 0, new ModelResourceLocation("eliasguns:stickGrenade", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(EliasGuns.gun, 0, new ModelResourceLocation("eliasguns:gun", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(EliasGuns.gun_bullet, 0, new ModelResourceLocation("eliasguns:gun_bullet", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(EliasGuns.gun_bullet_casing, 0, new ModelResourceLocation("eliasguns:gun_bullet_casing", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(EliasGuns.desert_eagle, 0, new ModelResourceLocation("eliasguns:desert_eagle", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(EliasGuns.desert_eagle_bullet, 0, new ModelResourceLocation("eliasguns:desert_eagle_bullet", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(EliasGuns.desert_eagle_casing, 0, new ModelResourceLocation("eliasguns:desert_eagle_casing", "inventory"));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeSmoke.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), EliasGuns.smokeGrenade, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), EliasGuns.grenade, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeSticky.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), EliasGuns.stickGrenade, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet());
		RenderingRegistry.registerEntityRenderingHandler(EntityDeagleBullet.class, new RenderBullet());
		FMLCommonHandler.instance().bus().register(this.tickClientHandlerEvent);
		MinecraftForge.EVENT_BUS.register(this.tickClientHandlerEvent);
		tickClientHandlerEvent.registerBowAnimationLockedItem(ItemWithHoldingLikeBowAnimation.class);
	}
    public void handleRightClickDelay(int delay)
    {
    	ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), delay, "rightClickDelayTimer", "field_71467_ac");
    }
}
