package fr.elias.eliasgun.common;

import java.util.List;

import fr.elias.eliasgun.common.IGrenade.EnumGrenadeType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenadeSticky extends EntityGrenade implements IGrenade
{
	public EntityGrenadeSticky(World world)
	{
		super(world);
		explosionStrength = 4F;
	}

	public EntityGrenadeSticky(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		explosionStrength = 4F;
	}

	public EntityGrenadeSticky(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
		explosionStrength = 4F;
	}
	public void onUpdate()
	{
		List list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox());
		if(list.size() > 0)
		{
			Entity entity = (Entity)list.get(0);
			if(entity != getThrower())
			{
				this.mountEntity(entity);
				if(entity instanceof EntityCreeper)
				{
					explosionStrength = 16F;
				}
			}else{
				this.mountEntity(null);
			}
		}
    	if(!worldObj.isAirBlock(getPosition().down()) || !worldObj.isAirBlock(getPosition().north())|| !worldObj.isAirBlock(getPosition().east()) || !worldObj.isAirBlock(getPosition().west()))
    	{
    		motionX = motionY = motionZ = 0.0D;
    	}
		super.onUpdate();
	}
	protected void handleExplode()
    {
		super.handleExplode();
    }
	public EnumGrenadeType getGrenadeType()
	{
		return EnumGrenadeType.STICKY;
	}
}
