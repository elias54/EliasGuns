package fr.elias.eliasgun.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenade extends Item {

	public ItemGrenade()
	{
		super();
	}
	
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
    	if(!worldIn.isRemote)
    	{
    		worldIn.spawnEntityInWorld(new EntityGrenade(worldIn, playerIn));
    	}
    	playerIn.swingItem();
        return itemStackIn;
    }
}
