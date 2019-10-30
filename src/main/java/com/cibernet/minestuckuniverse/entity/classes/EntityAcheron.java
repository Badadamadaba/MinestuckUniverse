package com.cibernet.minestuckuniverse.entity.classes;

import com.mraof.minestuck.alchemy.GristHelper;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.GristType;
import com.mraof.minestuck.entity.ai.EntityAIAttackOnCollideWithRate;
import com.mraof.minestuck.entity.underling.EntityUnderling;
import com.mraof.minestuck.item.ItemBoondollars;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.Echeladder;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.MinestuckSoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityAcheron extends EntityUnderling
{
	public EntityAcheron(World world)
	{
		super(world);
		setSize(8.0F, 11.0F);
		this.stepHeight = 1.0F;
	}

	@Override
	protected String getUnderlingName()
	{
		return "acheron";
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();
		EntityAIAttackOnCollideWithRate aiAttack = new EntityAIAttackOnCollideWithRate(this, .2F, 20, false);
		aiAttack.setDistanceMultiplier(4F);
		this.tasks.addTask(3, aiAttack);
	}



	protected SoundEvent getAmbientSound()
	{
		return MinestuckSoundHandler.soundOgreAmbient;
	}

	protected SoundEvent getDeathSound()
	{
		return MinestuckSoundHandler.soundOgreDeath;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return MinestuckSoundHandler.soundOgreHurt;
	}

	@Override
	public GristSet getGristSpoils()
	{
		return GristHelper.getRandomDrop(type, 20);
	}

	@Override
	protected double getWanderSpeed()
	{
		return 0.55;
	}

	@Nullable
	@Override
	protected Item getDropItem()
	{
		/* TODO loot tables
		int chance = rand.nextInt(100);
		int value = 2000;
		if(chance < 1)
			value = 6000;
		else if(chance < 15)
			value = 5000;
		else if(chance < 40)
			value = 4000;
		else if(chance < 80)
			value = 300;
		*/
		return super.getDropItem();
	}

	@Override
	protected float getMaximumHealth()
	{
		return type != null ? 500 * type.getPower() + 1200 : 1;
	}

	@Override
	protected float getKnockbackResistance()
	{
		return 0.8F;
	}

	@Override
	protected double getAttackDamage()
	{
		return this.type.getPower() * 10 + 20;
	}

	@Override
	protected int getVitalityGel()
	{
		return rand.nextInt(30) + 5;
	}

	@Override
	protected void applyGristType(GristType type, boolean fullHeal)
	{
		super.applyGristType(type, fullHeal);
		this.experienceValue = (int) (4000 * type.getPower() + 700);
	}

	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		Entity entity = cause.getTrueSource();
		if(this.dead && !this.world.isRemote && type != null)
		{
			computePlayerProgress((int) (400*type.getPower() + 800));
			if(entity != null && entity instanceof EntityPlayerMP)
			{
				Echeladder ladder = MinestuckPlayerData.getData((EntityPlayerMP) entity).echeladder;
				ladder.checkBonus((byte) (Echeladder.UNDERLING_BONUS_OFFSET + 1));
			}
		}
	}
}
