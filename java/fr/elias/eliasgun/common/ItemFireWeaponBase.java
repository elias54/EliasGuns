package fr.elias.eliasgun.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class ItemFireWeaponBase extends ItemWithHoldingLikeBowAnimation
{
	public int reloadTick;
	public int reloadMax;
	public boolean reload;
	
	public int ammoTick;
	public int ammoMax;
	
	public int fireMax;
	
	public ItemFireWeaponBase(int firemax, int autoDelay)
	{
		super();
		fireMax = firemax;
		ammoMax = 15;
		ammoTick = 0;
		reloadMax = 20;
		reloadTick = 0;
		reload = false;
	}

    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
    	super.onItemRightClick(itemStackIn, worldIn, playerIn);
    	if(playerIn.capabilities.isCreativeMode)
    	{
    		handleFire(worldIn, playerIn);
    	}else
    	{
        	if(!playerIn.inventory.hasItemStack(new ItemStack(getBullet(), ammoMax)))
        	{
        		worldIn.playSoundAtEntity(playerIn, "eliasguns:guns.pistol.empty", 1.0F, 1.0F);
        	}else{
            	if(ammoTick != ammoMax && !reload && playerIn.inventory.consumeInventoryItem(getBullet()))
            	{
            		if(!worldIn.isRemote)
            		{
                    	ammoTick++;
            		}
                	handleFire(worldIn, playerIn);
            	}else if(ammoTick == ammoMax && !reload && playerIn.inventory.hasItemStack(new ItemStack(getBullet(), ammoMax)))
            	{
            		reload = true;
            	}
        	}
    	}
        return itemStackIn;
    }
	
	public void handleFire(World world, EntityPlayer player)
	{
		world.playSoundAtEntity(player, "eliasguns:" + fireSound(), 1.0F, 1.0F);
		if(!player.worldObj.isRemote)
        {
			player.worldObj.spawnEntityInWorld(getBulletEntity(world, player));
    		if(!player.capabilities.isCreativeMode)
    		player.dropItem(getBulletCasing(), 1);
        }
	}
    public void handleReload(World worldIn, EntityPlayer playerIn)
    {
    	if(reloadTick != reloadMax)
    	{
    		reloadTick++;
    	}
    	if(reloadTick > 0 && reloadTick < 4)
    	{
    		worldIn.playSoundAtEntity(playerIn, "eliasguns:" + reloadSound(), 1.0F, 1.0F);
    	}
    	if(reloadTick == reloadMax)
    	{
    		reload = false;
    		reloadTick = 0;
    		ammoTick = 0;
    	}
    }
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if(isSelected)
    	{	
    		this.updateWeaponTick(stack, worldIn, (EntityPlayer) entityIn);
    	}
    }
	public void updateWeaponTick(ItemStack is, World world, EntityPlayer player)
	{
		if(world.isRemote)
		{
			EliasGuns.proxy.handleRightClickDelay(fireMax);
		}
		is.setTagCompound(new NBTTagCompound());
		if(ammoTick > ammoMax)
    	{
    		ammoTick = ammoMax;
    	}
    	if(!player.inventory.hasItem(getBullet()))
    	{
    		ammoTick = 0;
    	}
    	if(reload)
    	{
        	handleReload(world, player);
    	}
    	is.getTagCompound().setBoolean("reloading", reload);
    	is.getTagCompound().setInteger("reloadTick", reloadTick);
    	is.getTagCompound().setInteger("reloadMax", reloadMax);
    	is.getTagCompound().setInteger("ammoTick", ammoTick);
	}

	public abstract String reloadSound();
	public abstract String fireSound();
	public abstract Item getBullet();
	public abstract Item getBulletCasing();
	public abstract Entity getBulletEntity(World world, EntityPlayer player);
	
	public void moveCamera(EntityPlayer player, float f)
	{
		//f = 1
    	player.rotationPitch -= (f + (Math.random() * itemRand.nextFloat()));
    	player.rotationYaw += (float) (Math.random() * itemRand.nextInt(1));
	}
}
