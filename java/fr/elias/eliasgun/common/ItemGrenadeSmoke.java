package fr.elias.eliasgun.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenadeSmoke extends Item
{
	public ItemGrenadeSmoke()
	{
		super();
	}
	
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
    	if(!worldIn.isRemote)
    	{
    		worldIn.spawnEntityInWorld(new EntityGrenadeSmoke(worldIn, playerIn));
    	}
    	playerIn.swingItem();
        return itemStackIn;
    }
}
