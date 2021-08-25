package com.cibernet.minestuckuniverse.capabilities;

import com.cibernet.minestuckuniverse.MinestuckUniverse;
import com.cibernet.minestuckuniverse.capabilities.strife.IStrifeData;
import com.cibernet.minestuckuniverse.capabilities.strife.StrifeData;
import com.cibernet.minestuckuniverse.network.MSUChannelHandler;
import com.cibernet.minestuckuniverse.network.MSUPacket;
import com.mraof.minestuck.network.skaianet.SburbConnection;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import com.mraof.minestuck.world.lands.LandAspectRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MSUCapabilities
{
	@CapabilityInject(IStrifeData.class)
	public static final Capability<IStrifeData> STRIFE_DATA = null;

	public static void registerCapabilities()
	{
		MinecraftForge.EVENT_BUS.register(MSUCapabilities.class);

		CapabilityManager.INSTANCE.register(IStrifeData.class, new MSUCapabilityProvider.Storage<>(), StrifeData::new);
	}

	@SubscribeEvent
	public static void attachEntityCap(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityPlayer)
			event.addCapability(new ResourceLocation(MinestuckUniverse.MODID, "strife_data"), new MSUCapabilityProvider<>(STRIFE_DATA, (EntityLivingBase) event.getObject()));
	}

	@SubscribeEvent
	public static void onPlayerJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof EntityPlayerMP)
			MSUChannelHandler.sendToPlayer(MSUPacket.makePacket(MSUPacket.Type.UPDATE_STRIFE, ((EntityPlayer)event.getEntity())), ((EntityPlayer)event.getEntity()));
	}
}