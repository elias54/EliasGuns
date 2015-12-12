package fr.elias.eliasgun.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenadeSticky extends Item
{
	public ItemGrenadeSticky()
	{
		super();
	}
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
    	if(!worldIn.isRemote)
    	{
    		worldIn.spawnEntityInWorld(new EntityGrenadeSticky(worldIn, playerIn));
    	}
    	playerIn.swingItem();
        return itemStackIn;
    }
}
