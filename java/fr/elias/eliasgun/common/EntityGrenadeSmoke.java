package fr.elias.eliasgun.common;

import fr.elias.eliasgun.common.IGrenade.EnumGrenadeType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;


public class EntityGrenadeSmoke extends EntityGrenade implements IGrenade
{
    protected String BOUNCE_SOUND;
    private static final int SMOKE_TIME = 500;
    private static final int MAX_DIAMETER_TIME = 250;
    private static final double MAX_DIAMETER = 8D;

    public EntityGrenadeSmoke(World world)
    {
        super(world);
        BOUNCE_SOUND = "eliasguns:grenade.smokebounce";
    }

    public EntityGrenadeSmoke(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        BOUNCE_SOUND = "eliasguns:grenade.smokebounce";
    }

    public EntityGrenadeSmoke(World world, EntityLivingBase entityliving)
    {
        super(world, entityliving);
        BOUNCE_SOUND = "eliasguns:grenade.smokebounce";
    }
    protected void explode()
    {
        if (!exploded)
        {
            exploded = true;
            worldObj.playSoundAtEntity(this, "eliasguns:grenade.smoke", 1.0F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
        }

        if (fuse < -500)
        {
            isDead = true;
        }

        if (exploded)
        {
            double d = Math.min(8D, ((double)(-fuse) * 8D) / 250D);
            int i = Math.min(250, -fuse);

            for (int j = 0; j < i; j++)
            {
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (posX + rand.nextDouble() * d) - 0.5D * d, posY + rand.nextDouble() * d, (posZ + rand.nextDouble() * d) - 0.5D * d, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }
    }
    public ItemStack getEntityItem()
    {
    	return new ItemStack(EliasGuns.smokeGrenade);
    }
    public EnumGrenadeType getGrenadeType()
    {
    	return EnumGrenadeType.SMOKE;
    }
}
