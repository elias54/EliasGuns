package fr.elias.eliasgun.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemWithHoldingLikeBowAnimation extends Item
{
	public ItemWithHoldingLikeBowAnimation(){super();}
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player){return true;}
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity){return true;}
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){return true;}
	public EnumAction getItemUseAction(ItemStack par1ItemStack){return EnumAction.BOW;}
}
