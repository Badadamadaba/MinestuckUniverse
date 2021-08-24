package com.cibernet.minestuckuniverse.events.handlers;

import com.cibernet.minestuckuniverse.MinestuckUniverse;
import com.cibernet.minestuckuniverse.enchantments.MSUEnchantments;
import com.cibernet.minestuckuniverse.items.IPropertyWeapon;
import com.cibernet.minestuckuniverse.items.armor.ItemPogoBoots;
import com.cibernet.minestuckuniverse.items.MSUItemBase;
import com.cibernet.minestuckuniverse.items.MinestuckUniverseItems;
import com.cibernet.minestuckuniverse.items.properties.PropertyRandomDamage;
import com.cibernet.minestuckuniverse.items.weapons.ItemBeamBlade;
import com.cibernet.minestuckuniverse.network.MSUChannelHandler;
import com.cibernet.minestuckuniverse.network.MSUPacket;
import com.cibernet.minestuckuniverse.potions.MSUPotions;
import com.cibernet.minestuckuniverse.util.MSUUtils;
import com.google.common.collect.Multimap;
import com.mraof.minestuck.alchemy.AlchemyRecipes;
import com.mraof.minestuck.event.AlchemizeItemEvent;
import com.mraof.minestuck.item.ItemCaptcharoidCamera;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.network.skaianet.SburbHandler;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import com.mraof.minestuck.world.lands.gen.ChunkProviderLands;
import com.sun.javafx.geom.Vec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemClock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.UUID;

public class CommonEventHandler
{

	@SubscribeEvent
	public static void onEntityHurt(LivingDamageEvent event)
	{
		if(event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(MinestuckUniverseItems.spikedHelmet))
		{
			if(event.getEntityLiving().world.rand.nextInt(2) == 0 && event.getSource().getImmediateSource() != null)
				event.getSource().getImmediateSource().attackEntityFrom(DamageSource.causeThornsDamage(event.getEntityLiving()), 4);
		}
		if(event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(MinestuckUniverseItems.wizardHat) && event.getSource().isMagicDamage())
		{
			event.setAmount(event.getAmount()*0.5f);
			event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD).damageItem(1, event.getEntityLiving());
		}
		if(event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(MinestuckUniverseItems.archmageHat) && event.getSource().isMagicDamage())
		{
			event.setAmount(event.getAmount()*0.2f);
			event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD).damageItem(1, event.getEntityLiving());
		}
	}

	@SubscribeEvent
	public static void onFall(LivingFallEvent event)
	{
		Item footwear = event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
		if (footwear instanceof ItemPogoBoots) {
			EntityLivingBase entity = event.getEntityLiving();
			ItemPogoBoots item = (ItemPogoBoots) event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

			if (!entity.isSneaking())
			{
				double motion = Math.min(10, Math.sqrt(event.getDistance())*0.3)*item.power;

				if(motion > 0.115)
				{
					entity.motionY = motion;
					entity.motionX *= 1.5f;
					entity.motionZ /= 1.5f;

					entity.velocityChanged = true;
					entity.isAirBorne = true;
					entity.onGround = false;
					entity.fallDistance = 0;
					event.setDamageMultiplier(0);
				}

				if (item.isSolar() && entity.motionY > item.power*1.2f)
					entity.setFire(5);
				}
		}
		else if(footwear == MinestuckUniverseItems.rocketBoots)
			event.setDamageMultiplier(event.getDamageMultiplier()*0.4f);
	}

	@SubscribeEvent
	public static void onJump(LivingEvent.LivingJumpEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		Item footwear = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

		if (footwear instanceof ItemPogoBoots)
		{
			ItemPogoBoots item = (ItemPogoBoots) event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

			if (!entity.isSneaking())
				entity.motionY *= 0.25 + item.power;
		}
		else if(footwear == MinestuckUniverseItems.windWalkers)
			entity.motionY *= 2f;
	}

	public static final IAttribute COOLED_ATTACK_STRENGTH = new RangedAttribute(null, MinestuckUniverse.MODID+".cooledAttackStrength", 0, 0, 1).setDescription("Cooled Attack Strength");

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onInputUpdate(InputUpdateEvent event)
	{
		ItemStack footwear = event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.FEET);
		if(!event.getEntityPlayer().isElytraFlying() && event.getMovementInput().jump && footwear.getItem() == MinestuckUniverseItems.rocketBoots)
		{
			event.getEntityPlayer().moveRelative(event.getMovementInput().moveStrafe*0.5f, 1, event.getMovementInput().moveForward*0.5f, 0.165f);
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		Item footwear = event.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
		if(footwear == MinestuckUniverseItems.airJordans || footwear == MinestuckUniverseItems.cobaltJordans)
			event.player.onGround = true;
		else if(footwear == MinestuckUniverseItems.windWalkers)
		{
			event.player.motionY *= 0.65 +0.3 * Math.max(0, Math.min(1, event.player.motionY*50f));
			event.player.fallDistance = 0;
			if(!event.player.world.isRemote && !event.player.onGround && event.player.motionY < 0)
				((WorldServer)event.player.world).spawnParticle(EnumParticleTypes.CLOUD, event.player.posX, event.player.posY, event.player.posZ, 1, 0.25D, 0.1D, 0.25D, 0.05D);

		}
		//else if(footwear == MinestuckUniverseItems.rocketBoots && !event.player.onGround && !event.player.world.isRemote) TODO particle effects
			//((WorldServer)event.player.world).spawnParticle(EnumParticleTypes.FLAME, event.player.posX, event.player.posY, event.player.posZ, 1, 0.1D, 0.0D, 0.1D, 0.05D);


		if(event.player.isSpectator())
		{
			event.player.capabilities.isFlying = true;
			event.player.capabilities.allowFlying = true;
		}

		if(event.player.getAttributeMap().getAttributeInstance(COOLED_ATTACK_STRENGTH) == null)
			event.player.getAttributeMap().registerAttribute(COOLED_ATTACK_STRENGTH);

		if(event.phase == TickEvent.Phase.START)
		{
			for (EntityEquipmentSlot slot : EntityEquipmentSlot.values())
			{
				ItemStack stack = event.player.getItemStackFromSlot(slot);
				AbstractAttributeMap attrMap = event.player.getAttributeMap();

				for (Map.Entry<String, AttributeModifier> attr : stack.getAttributeModifiers(slot).entries()) {
					IAttributeInstance attrInstance = attrMap.getAttributeInstanceByName(attr.getKey());

					if (attrInstance != null && attrInstance.hasModifier(attr.getValue()) && attrInstance.getModifier(attr.getValue().getID()).getAmount() != attr.getValue().getAmount()) {
						attrInstance.removeModifier(attr.getValue().getID());
						attrInstance.applyModifier(attr.getValue());
					}

				}
			}

			ItemStack stack = event.player.getHeldItemMainhand();
			if(event.player.getCooldownTracker().hasCooldown(stack.getItem()))
				event.player.resetCooldown();
		}
		else if(event.phase == TickEvent.Phase.END)
		{
			double str = getCooledAttackStrength(event.player);
			double currStr = event.player.getCooledAttackStrength(0.5f);

			if(str != currStr)
				event.player.getAttributeMap().getAttributeInstance(COOLED_ATTACK_STRENGTH).setBaseValue(currStr);
		}
	}

	@SubscribeEvent
	public static void playerJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof EntityPlayer && ((EntityPlayer)event.getEntity()).getAttributeMap().getAttributeInstance(COOLED_ATTACK_STRENGTH) == null)
			((EntityPlayer)event.getEntity()).getAttributeMap().registerAttribute(COOLED_ATTACK_STRENGTH);
	}

	@SubscribeEvent
	public static void onEntityAttacked(AttackEntityEvent event)
	{
		if(event.getEntityLiving() instanceof EntityPlayer && ((EntityPlayer) event.getEntityLiving()).getCooldownTracker().hasCooldown(event.getEntityLiving().getHeldItemMainhand().getItem()))
			event.setCanceled(true);
	}

	public static float getCooledAttackStrength(EntityPlayer player)
	{
		return (float) player.getAttributeMap().getAttributeInstance(COOLED_ATTACK_STRENGTH).getAttributeValue();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event)
	{
		if(Minecraft.getMinecraft().player == null)
			return;

		EntityPlayer player = Minecraft.getMinecraft().player;

		if((player.isPotionActive(MSUPotions.SKYHBOUND) && player.getActivePotionEffect(MSUPotions.SKYHBOUND).getDuration() >= 5)
				|| (player.isCreative() && player.isPotionActive(MSUPotions.EARTHBOUND) && player.getActivePotionEffect(MSUPotions.EARTHBOUND).getDuration() < 5))
			player.capabilities.allowFlying = true;
		if((player.isPotionActive(MSUPotions.EARTHBOUND) && player.getActivePotionEffect(MSUPotions.EARTHBOUND).getDuration() >= 5)
				|| (!player.isCreative() && player.isPotionActive(MSUPotions.SKYHBOUND) && player.getActivePotionEffect(MSUPotions.SKYHBOUND).getDuration() < 5))
		{
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
		}

		if(!player.isCreative() && player.isPotionActive(MSUPotions.CREATIVE_SHOCK))
		{
			int duration = player.getActivePotionEffect(MSUPotions.CREATIVE_SHOCK).getDuration();
			if(duration >= 5)
				player.capabilities.allowEdit = false;
			else player.capabilities.allowEdit = !MSUUtils.getPlayerGameType(player).hasLimitedInteractions();
		}


		if(player.isSpectator())
		{
			player.capabilities.isFlying = true;
			player.capabilities.allowFlying = true;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();

		boolean isRandom = false;
		float randValue = 0;

		if(stack.getItem() instanceof IPropertyWeapon && ((IPropertyWeapon) stack.getItem()).hasProperty(PropertyRandomDamage.class, stack))
		{
			PropertyRandomDamage prop = (PropertyRandomDamage) ((IPropertyWeapon) stack.getItem()).getProperty(PropertyRandomDamage.class, stack);
			randValue = prop.getMax() * prop.getMulitiplier();
			isRandom = true;
		}
		else if(stack.getItem() instanceof com.mraof.minestuck.item.weapon.ItemRandomWeapon)
		{
			randValue = 64;
			isRandom = true;
		}

		for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
		{
			Multimap<String, AttributeModifier> multimap = stack.getAttributeModifiers(entityequipmentslot);

			if (!multimap.isEmpty())
			{
				for (Map.Entry<String, AttributeModifier> entry : multimap.entries())
				{
					AttributeModifier attributemodifier = entry.getValue();
					double d0 = attributemodifier.getAmount();

					if (event.getEntityPlayer() != null)
					{
						if (isRandom && attributemodifier.getID().equals(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF")))
						{
							d0 = d0 + event.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
							d0 = d0 + (double) EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
							double d1;

							if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2)
								d1 = d0;
							else d1 = d0 * 100.0D;

							String attackString = (" " + I18n.translateToLocalFormatted("attribute.modifier.equals." + attributemodifier.getOperation(), ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)entry.getKey())));
							String newAttackString = (" " + I18n.translateToLocalFormatted("attribute.modifier.equals." + attributemodifier.getOperation(), (ItemStack.DECIMALFORMAT.format(d1) + "-" + ItemStack.DECIMALFORMAT.format(d1+randValue)), I18n.translateToLocal("attribute.name." + (String)entry.getKey())));

							if(event.getToolTip().contains(attackString))
								event.getToolTip().set(event.getToolTip().indexOf(attackString), newAttackString);
							else event.getToolTip().add(newAttackString);
						}
					}

				}
			}
		}
	}

	@SubscribeEvent
	public static void onPotionRemove(PotionEvent.PotionRemoveEvent event)
	{
		onPotionEnd(event.getEntityLiving(), event.getPotion());
	}

	@SubscribeEvent
	public static void onPotionExpire(PotionEvent.PotionExpiryEvent expiryEvent)
	{
		onPotionEnd(expiryEvent.getEntityLiving(), expiryEvent.getPotionEffect().getPotion());
	}

	private static void onPotionEnd(EntityLivingBase entityLiving, Potion potion)
	{
		if(entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityLiving;

			if(potion == MSUPotions.EARTHBOUND && player.isCreative())
			{
				MSUChannelHandler.sendToPlayer(MSUPacket.makePacket(MSUPacket.Type.FLIGHT_EFFECT, potion == MSUPotions.EARTHBOUND), player);
				player.capabilities.allowFlying = true;
			}
			if(potion == MSUPotions.SKYHBOUND && !player.isCreative())
			{
				MSUChannelHandler.sendToPlayer(MSUPacket.makePacket(MSUPacket.Type.FLIGHT_EFFECT, potion == MSUPotions.EARTHBOUND), player);
				player.capabilities.allowFlying = false;
				player.capabilities.isFlying = false;
			}
			if(!player.isCreative() && potion == MSUPotions.CREATIVE_SHOCK)
			{
				player.capabilities.allowEdit = !MSUUtils.getPlayerGameType(player).hasLimitedInteractions();
				MSUChannelHandler.sendToPlayer(MSUPacket.makePacket(MSUPacket.Type.BUILD_INHIBIT_EFFECT), player);
			}
		}
	}

	@SubscribeEvent
	public static void onBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		if(event.getEntityPlayer().isPotionActive(MSUPotions.CREATIVE_SHOCK))
			event.setNewSpeed(0);
	}

	@SubscribeEvent
	public static void onHarvestCheck(PlayerEvent.HarvestCheck event)
	{
		if(event.getEntityPlayer().isPotionActive(MSUPotions.CREATIVE_SHOCK))
			event.setCanHarvest(false);
	}

	@SubscribeEvent
	public static void onKnockback(LivingKnockBackEvent event)
	{
		if(event.getAttacker() instanceof EntityPlayer)
			event.setStrength(event.getStrength() + Math.max(0, 0.5f*(EnchantmentHelper.getMaxEnchantmentLevel(MSUEnchantments.SUPERPUNCH, (EntityLivingBase) event.getAttacker())-1)));
	}

	@SubscribeEvent
	public static void playSoundEvent(PlaySoundAtEntityEvent entityEvent)
	{
		if(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC.equals(entityEvent.getSound()) && entityEvent.getEntity() instanceof EntityPlayer && ((EntityPlayer) entityEvent.getEntity()).getActiveItemStack().getItem() instanceof MSUItemBase)
			entityEvent.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRightClickEmpty(PlayerInteractEvent.RightClickItem event)
	{
		if(event.getItemStack().getItem() instanceof ItemCaptcharoidCamera)
		{
			EntityPlayer player = event.getEntityPlayer();
			World world = player.world;

			ItemStack stack = ItemStack.EMPTY;

			if(MinestuckDimensionHandler.isLandDimension(world.provider.getDimension()) && player.rotationPitch < -75)
				stack = new ItemStack(MinestuckUniverseItems.skaia);
			else if(world.provider.getDimension() == 0)
			{
				Vec3d playerPosVec = new Vec3d(player.posX, player.posY, player.posZ);
				Vec3d playerLookVec = player.getLookVec();

				RayTraceResult rayTrace = world.rayTraceBlocks(playerPosVec, playerPosVec.add(playerLookVec.scale(200)), true, true, false);

				if(rayTrace != null)
					return;

				playerLookVec = getVecFromRotation(-player.rotationPitch, player.rotationYaw);
				float celestialAngle = (world.getCelestialAngle(0)*360f + 90f) % 360f;
				Vec3d sunVec = getVecFromRotation(celestialAngle, -90);
				Vec3d moonVec = getVecFromRotation((celestialAngle+180f) % 360f, -90);

				if(playerLookVec.squareDistanceTo(sunVec) < 0.07f)
					stack = new ItemStack(MinestuckUniverseItems.sun);
				else if(playerLookVec.distanceTo(moonVec) < 0.07f)
					stack = new ItemStack(MinestuckUniverseItems.moon);

			}


			player.inventory.addItemStackToInventory(stack.isEmpty() ? new ItemStack(MinestuckItems.captchaCard) : AlchemyRecipes.createGhostCard(stack));
			event.getItemStack().damageItem(1, player);
			event.setCancellationResult(EnumActionResult.SUCCESS);
			event.setCanceled(true);
		}
	}

	public static Vec3d getVecFromRotation(float pitch, float yaw)
	{
		float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
		float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
		float f2 = -MathHelper.cos(-pitch * 0.017453292F);
		float f3 = MathHelper.sin(-pitch * 0.017453292F);
		return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2)).normalize();
	}

	@SubscribeEvent
	public static void onAlchemize(AlchemizeItemEvent event)
	{
		if(event.getResultItem().getItem() instanceof ItemBeamBlade)
			ItemBeamBlade.changeState(event.getResultItem(), false);
	}
}
