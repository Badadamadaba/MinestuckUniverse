package com.cibernet.minestuckuniverse.powers;

import com.cibernet.minestuckuniverse.items.ItemClasspectPower;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MSUPowerBase
{
	private final EnumClass cls;
	private final EnumAspect aspect;
	public MSUPowerBase(EnumClass clss, EnumAspect aspect)
	{
		MSUClasspectPowers.powers.put(aspect, clss, this);
		this.cls = clss;
		this.aspect = aspect;
	}
	
	public abstract boolean use(World worldIn, EntityPlayer player, boolean isNative);
	public abstract boolean useOnBlock(World worldIn, EntityPlayer playerIn, BlockPos pos, float hitX, float hitY, float hitZ, boolean isNative);
}