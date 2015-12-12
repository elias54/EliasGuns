package fr.elias.eliasgun.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityDeagleBullet extends EntityBullet
{
	public EntityDeagleBullet(World worldIn) {
		super(worldIn);
		bulletStrength = 8.5F;
	}

	public EntityDeagleBullet(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
		bulletStrength = 8.5F;
	}

	public EntityDeagleBullet(World worldIn, double x, double y, double p_i1778_6_) {
		super(worldIn, x, y, p_i1778_6_);
		bulletStrength = 8.5F;
	}
}
