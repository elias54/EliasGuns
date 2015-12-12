package fr.elias.eliasgun.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBullet extends EntityThrowable {

	public float bulletStrength;
	public int ticksAlive;
	
	public EntityBullet(World worldIn) {
		super(worldIn);
		bulletStrength = 3F;
	}

	public EntityBullet(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
		bulletStrength = 3F;
	}

	public EntityBullet(World worldIn, double x, double y, double p_i1778_6_) {
		super(worldIn, x, y, p_i1778_6_);
		bulletStrength = 3F;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop)
	{
		if(mop.entityHit != null)
		{
			float l = bulletStrength;
            if (mop.entityHit instanceof EntityLivingBase)
            {
            	mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), l);
            }
            else
            {
    			mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(getThrower(), this), l);
            }
			setDead();
		}
		else
		{
			setDead();
		}
	}
	
	public void onUpdate()
	{
		if(ticksAlive++>500)
		{
			setDead();
		}
		super.onUpdate();
	}
	
    protected float getGravityVelocity()
    {
        return 0.0F;
    }
	
}
