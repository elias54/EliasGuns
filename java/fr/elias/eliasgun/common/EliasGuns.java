package fr.elias.eliasgun.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = "eliasguns", name = "Elias's Guns Mod", version = "1.0")
public class EliasGuns 
{
	@SidedProxy(clientSide="fr.elias.eliasgun.client.ClientProxy", serverSide="fr.elias.eliasgun.common.CommonProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance("eliasguns")
	public static EliasGuns instance;
	
	public static Item grenade,
					   smokeGrenade,
					   stickGrenade;
	public static Item gun, desert_eagle;
	public static Item gun_bullet, desert_eagle_bullet;
	public static Item gun_bullet_casing, desert_eagle_casing;
	
	public int smokeGrenadeProjectileID,
			   grenadeProjectileID,
			   stickGrenadeProjectileID,
			   gun_bullet_projectileID,
			   deagle_bullet_projectileID;
	
	public CreativeTabs eliasGunsTab = new CreativeTabs("eliasGunsTab")
	{public Item getTabIconItem(){return EliasGuns.gun;}};
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try {
			config.load();
			smokeGrenadeProjectileID = config.get("Entity", "Smoke Grenade Projectile ID", 424).getInt();
			grenadeProjectileID = config.get("Entity", "Grenade Projectile ID", 425).getInt();
			stickGrenadeProjectileID = config.get("Entity", "Sticky Grenade Projectile ID", 426).getInt();
			gun_bullet_projectileID = config.get("Entity", "Bullet Projectile ID", 427).getInt();
			deagle_bullet_projectileID = config.get("Entity", "Bullet Projectile ID", 428).getInt();
			config.save();
		} finally {
			if(config.hasChanged())
			{
				config.save();
			}
		}
		
		grenade = new ItemGrenade().setCreativeTab(eliasGunsTab).setUnlocalizedName("grenade");
		smokeGrenade = new ItemGrenadeSmoke().setCreativeTab(eliasGunsTab).setUnlocalizedName("smokeGrenade");
		stickGrenade = new ItemGrenadeSticky().setCreativeTab(eliasGunsTab).setUnlocalizedName("stickyGrenade");
		
		gun = new ItemGun2().setMaxStackSize(1).setCreativeTab(eliasGunsTab).setUnlocalizedName("gun");
		gun_bullet = new Item().setMaxStackSize(15).setCreativeTab(eliasGunsTab).setUnlocalizedName("gun_bullet");
		gun_bullet_casing = new Item().setCreativeTab(eliasGunsTab).setUnlocalizedName("gun_bullet_casing");
		
		desert_eagle = new ItemDesertEagle().setMaxStackSize(1).setCreativeTab(eliasGunsTab).setUnlocalizedName("desert_eagle");
		desert_eagle_bullet = new Item().setMaxStackSize(7).setCreativeTab(eliasGunsTab).setUnlocalizedName("desert_eagle_bullet");
		desert_eagle_casing = new Item().setCreativeTab(eliasGunsTab).setUnlocalizedName("desert_eagle_casing");
		
		GameRegistry.registerItem(smokeGrenade, "smokeGrenade", "eliasguns");
		GameRegistry.registerItem(grenade, "grenade", "eliasguns");
		GameRegistry.registerItem(stickGrenade, "stickGrenade", "eliasguns");
		
		GameRegistry.registerItem(gun, "gun", "eliasguns");
		GameRegistry.registerItem(gun_bullet, "gun_bullet", "eliasguns");
		GameRegistry.registerItem(gun_bullet_casing, "gun_bullet_casing", "eliasguns");

		GameRegistry.registerItem(desert_eagle, "desert_eagle", "eliasguns");
		GameRegistry.registerItem(desert_eagle_bullet, "desert_eagle_bullet", "eliasguns");
		GameRegistry.registerItem(desert_eagle_casing, "desert_eagle_casing", "eliasguns");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.render();
		EntityRegistry.registerModEntity(EntityGrenadeSmoke.class, "grenadeSmoke", smokeGrenadeProjectileID, this, 40, 1, true);
		EntityRegistry.registerModEntity(EntityGrenade.class, "grenade", grenadeProjectileID, this, 40, 1, true);
		EntityRegistry.registerModEntity(EntityBullet.class, "bullet", gun_bullet_projectileID, this, 40, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeSticky.class, "stickGrenade", stickGrenadeProjectileID, this, 40, 1, true);
	}
}
