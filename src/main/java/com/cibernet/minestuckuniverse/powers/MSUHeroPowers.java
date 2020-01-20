package com.cibernet.minestuckuniverse.powers;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mraof.minestuck.util.*;
import net.minecraft.entity.player.EntityPlayer;

public class MSUHeroPowers
{
	protected static final Table<EnumAspect, EnumClass, MSUPowerBase> powers = HashBasedTable.create();
	
	public static MSUPowerBase machineResize = new PowerMachineResize();
	public static MSUPowerBase daytimeShift = new PowerDaytimeShift();
	public static MSUPowerBase windSpeed = new PowerWindSpeed();
	public static MSUPowerBase teleport = new PowerTeleport();
	public static MSUPowerBase rageShift = new PowerRageShift();
	
	public static boolean hasPower(EntityPlayer playerIn, MSUPowerBase power)
	{
		MSUPowerBase playerPower = getPlayerPower(playerIn);
		return playerPower != null && playerPower.equals(power);
	}
	
	public static MSUPowerBase getPower(EnumClass clss, EnumAspect aspect)
	{
		return powers.contains(aspect,clss) ? powers.get(aspect,clss) : powers.get(aspect,null);
	}
	
	public static MSUPowerBase getPlayerPower(EntityPlayer player)
	{
		IdentifierHandler.PlayerIdentifier id = IdentifierHandler.encode(player);
		Title title = MinestuckPlayerData.getTitle(id);
		if(title == null)
			return null;
		return getPower(title.getHeroClass(), title.getHeroAspect());
	}
}