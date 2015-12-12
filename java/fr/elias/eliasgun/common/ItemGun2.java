package fr.elias.eliasgun.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ItemGun2 extends ItemFireWeaponBase {

	public ItemGun2()
	{
		super(4, 0);
		ammoMax = 15;
		ammoTick = 0;
		reloadMax = 20;
		reloadTick = 0;
		reload = false;
	}

	public void handleFire(World world, EntityPlayer player)
	{
		super.handleFire(world, player);
    	this.moveCamera(player, 1F);
	}
	
	public String reloadSound() {
		return "guns.pistol.reload";
	}

	public String fireSound() {
		return "guns.pistol.fire";
	}

	public Item getBullet() {
		return EliasGuns.gun_bullet;
	}

	public Item getBulletCasing() {
		return EliasGuns.gun_bullet_casing;
	}

	public Entity getBulletEntity(World world, EntityPlayer player)
	{
		return new EntityBullet(world, player);
	}

}
