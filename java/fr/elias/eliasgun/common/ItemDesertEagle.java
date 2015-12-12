package fr.elias.eliasgun.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDesertEagle extends ItemFireWeaponBase
{
	public ItemDesertEagle()
	{
		super(4, 0);
		this.ammoMax = 7;
		this.ammoTick = 0;
		this.reloadMax = 40;
		this.reloadTick = 0;
		this.reload = false;
	}
	
	public void handleFire(World world, EntityPlayer player)
	{
		super.handleFire(world, player);
    	this.moveCamera(player, 1.6F);
	}
	
	public String reloadSound() {
		return "guns.deagle.reload";
	}

	public String fireSound() {
		return "guns.deagle.fire";
	}

	public Item getBullet() {
		return EliasGuns.desert_eagle_bullet;
	}

	public Item getBulletCasing() {
		return EliasGuns.desert_eagle_casing;
	}

	public Entity getBulletEntity(World world, EntityPlayer player)
	{
		return new EntityDeagleBullet(world, player);
	}

}
