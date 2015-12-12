package fr.elias.eliasgun.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemGun extends ItemWithHoldingLikeBowAnimation
{
	public int firetick;
	public int firemax;
	
	public int reloadTick;
	public int reloadMax;
	public boolean reload;
	
	public int ammoTick;
	public int ammoMax;

	public ItemGun()
	{
		super();
		firemax = 4;
		firetick = firemax;
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
        	if(!playerIn.inventory.hasItemStack(new ItemStack(EliasGuns.gun_bullet, ammoMax)))
        	{
        		worldIn.playSoundAtEntity(playerIn, "eliasguns:guns.pistol.empty", 1.0F, 1.0F);
        	}else{
            	if(ammoTick != ammoMax && !reload && playerIn.inventory.consumeInventoryItem(EliasGuns.gun_bullet))
            	{
            		if(!worldIn.isRemote)
            		{
                    	ammoTick++;
            		}
                	handleFire(worldIn, playerIn);
            	}else if(ammoTick == ammoMax && !reload && playerIn.inventory.hasItemStack(new ItemStack(EliasGuns.gun_bullet, ammoMax)))
            	{
            		reload = true;
            	}
        	}
    	}
        return itemStackIn;
    }
    public void handleReload(World worldIn, EntityPlayer playerIn)
    {
    	if(reloadTick != reloadMax)
    	{
    		reloadTick++;
    	}
    	if(reloadTick > 0 && reloadTick < 4)
    	{
    		worldIn.playSoundAtEntity(playerIn, "eliasguns:guns.pistol.reload", 1.0F, 1.0F);
    	}
    	if(reloadTick == reloadMax)
    	{
    		reload = false;
    		reloadTick = 0;
    		ammoTick = 0;
    	}
    	//System.out.println(reloadTick + "/" + reloadMax);
    }
    public void handleFire(World worldIn, EntityPlayer playerIn)
    {
    	EntityBullet bullet = new EntityBullet(worldIn, playerIn);
    	worldIn.playSoundAtEntity(playerIn, "eliasguns:guns.pistol.fire", 1.0F, 1.0F);
    	//move camera on fire
    	playerIn.rotationPitch -= (1 + (Math.random() * itemRand.nextFloat()));
    	playerIn.rotationYaw += (float) (Math.random() * itemRand.nextInt(1));
    	
    	//fire motherfucker !
		if(!playerIn.worldObj.isRemote)
        {
    		playerIn.worldObj.spawnEntityInWorld(bullet);
    		if(!playerIn.capabilities.isCreativeMode)
    		playerIn.dropItem(EliasGuns.gun_bullet_casing, 1);
        }
    }
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if(isSelected)
    	{
    		if(worldIn.isRemote)
    		EliasGuns.proxy.handleRightClickDelay(firetick);
    		
    		this.updateWeaponTick(stack, worldIn, (EntityPlayer) entityIn);
    	}
    }

	public void updateWeaponTick(ItemStack is, World world, EntityPlayer player)
	{
		is.setTagCompound(new NBTTagCompound());
		if(ammoTick > ammoMax)
    	{
    		ammoTick = ammoMax;
    	}
    	if(!player.inventory.hasItem(EliasGuns.gun_bullet))
    	{
    		ammoTick = 0;
    	}
    	if(reload)
    	{
        	this.handleReload(world, player);
    	}
    	is.getTagCompound().setBoolean("reloading", reload);
    	is.getTagCompound().setInteger("reloadTick", reloadTick);
    	is.getTagCompound().setInteger("ammoTick", ammoTick);
	}
}
