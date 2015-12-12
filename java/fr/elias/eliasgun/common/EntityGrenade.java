package fr.elias.eliasgun.common;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityGrenade extends EntityThrowable implements IGrenade
{
    protected String bounceSound;
    protected double bounceFactor;
    protected double bounceSlowFactor;
    protected int fuse;
    protected boolean exploded;
    protected double initialVelocity;
    protected static final int FUSE_LENGTH = 50;
    protected static final double MIN_BOUNCE_SOUND_VELOCITY = 0.1D;
    public float explosionStrength;

    public EntityGrenade(World world)
    {
        super(world);
        bounceSound = "eliasguns:grenade.bounce";
        bounceFactor = 0.15D;
        bounceSlowFactor = 0.8D;
        initialVelocity = 1.0D;
        setSize(0.25F, 0.25F);
        exploded = false;
        fuse = 50;
        explosionStrength = 3F;
    }
    
    public EntityGrenade(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    public EntityGrenade(World world, EntityLivingBase entityliving)
    {
        this(world);
        //setAngles(entityliving.rotationYaw, 0.0F);
        double d = -MathHelper.sin((entityliving.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((entityliving.rotationYaw * (float)Math.PI) / 180F);
        motionX = initialVelocity * d * (double)MathHelper.cos((entityliving.rotationPitch / 180F) * (float)Math.PI);
        motionY = -initialVelocity * (double)MathHelper.sin((entityliving.rotationPitch / 180F) * (float)Math.PI);
        motionZ = initialVelocity * d1 * (double)MathHelper.cos((entityliving.rotationPitch / 180F) * (float)Math.PI);

        if (entityliving.ridingEntity != null && (entityliving.ridingEntity instanceof EntityLiving))
        {
            entityliving = (EntityLiving)entityliving.ridingEntity;
        }

        motionX += entityliving.motionX;
        motionY += entityliving.onGround ? 0.0D : entityliving.motionY;
        motionZ += entityliving.motionZ;
        setPosition(entityliving.posX + d * 0.8D, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ + d1 * 0.8D);
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
    }
    public boolean isInRangeToRenderDist(double d)
    {
        return true;
    }
    public void onUpdate()
    {
        double d = motionX;
        double d1 = motionY;
        double d2 = motionZ;
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        moveEntity(motionX, motionY, motionZ);
        boolean flag = false;

        if (motionX == 0.0D && d != 0.0D)
        {
            motionX = -bounceFactor * d;
            motionY = bounceSlowFactor * d1;
            motionZ = bounceSlowFactor * d2;

            if (Math.abs(d) > 0.1D)
            {
                flag = true;
            }
        }

        if (motionY == 0.0D && d1 != 0.0D)
        {
            motionX = bounceSlowFactor * d;
            motionY = -bounceFactor * d1;
            motionZ = bounceSlowFactor * d2;

            if (Math.abs(d1) > 0.1D)
            {
                flag = true;
            }
        }

        if (motionZ == 0.0D && d2 != 0.0D)
        {
            motionX = bounceSlowFactor * d;
            motionY = bounceSlowFactor * d1;
            motionZ = -bounceFactor * d2;

            if (Math.abs(d2) > 0.1D)
            {
                flag = true;
            }
        }

        if (flag)
        {
            handleBounce();
        }

        motionY -= 0.04D;
        motionX *= 0.99D;
        motionY *= 0.99D;
        motionZ *= 0.99D;
        handleExplode();
    }

    protected void handleBounce()
    {
        worldObj.playSoundAtEntity(this, bounceSound, 0.25F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
    }

    protected void handleExplode()
    {
        if (fuse-- <= 0)
        {
            explode();
        }
    }
    protected void explode()
    {
        if (!exploded)
        {
            exploded = true;
            if(!worldObj.isRemote)
            	worldObj.createExplosion(this, posX, posY, posZ, explosionStrength, true);

            for (int i = 0; i < 32; i++)
            {
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX, posY, posZ, worldObj.rand.nextDouble() - 0.5D, worldObj.rand.nextDouble() - 0.5D, worldObj.rand.nextDouble() - 0.5D, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY, posZ, worldObj.rand.nextDouble() - 0.5D, worldObj.rand.nextDouble() - 0.5D, worldObj.rand.nextDouble() - 0.5D, new int[0]);
            }

            isDead = true;
        }
    }
    public boolean canBeCollidedWith()
    {
        return true;
    }

    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        return false;
    }
    
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setByte("Fuse", (byte)fuse);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        fuse = nbttagcompound.getByte("Fuse");
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
    }

    public float getEyeHeight()
    {
        return height;
    }

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumGrenadeType getGrenadeType() {
		// TODO Auto-generated method stub
		return EnumGrenadeType.FRAG;
	}
}
