package com.cibernet.minestuckuniverse.skills.abilitech.heroAspect.breath;

import com.cibernet.minestuckuniverse.capabilities.badgeEffects.IBadgeEffects;
import com.cibernet.minestuckuniverse.capabilities.keyStates.SkillKeyStates;
import com.cibernet.minestuckuniverse.events.AbilitechTargetedEvent;
import com.cibernet.minestuckuniverse.particles.MSUParticles;
import com.cibernet.minestuckuniverse.skills.abilitech.heroAspect.TechHeroAspect;
import com.cibernet.minestuckuniverse.util.EnumTechType;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class TechBreathKnockback extends TechHeroAspect
{
    public TechBreathKnockback(String name, long cost)
    {
        super(name, EnumAspect.BREATH, cost, EnumTechType.OFFENSE);
    }

    protected static final int RADIUS = 12;

    @Override
    public boolean onUseTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, int techSlot, SkillKeyStates.KeyState state, int time)
    {
        if(state == SkillKeyStates.KeyState.NONE || time > 160)
            return false;

        if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 1)
        {
            player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
            return false;
        }

        if(time > 15)
            badgeEffects.startPowerParticles(getClass(), MSUParticles.ParticleType.BURST, EnumAspect.BREATH, 20);
        else
            badgeEffects.startPowerParticles(getClass(), MSUParticles.ParticleType.AURA, EnumAspect.BREATH, 10);

        if (time % 10 == 0 && !player.isCreative())
            player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1);


        float strength = -Math.min(Math.max(0, time-10)/80f , 1);
        player.setAir(300);

        for(Entity target : world.getEntitiesWithinAABB(Entity.class, player.getEntityBoundingBox().grow(RADIUS), (entity) -> entity != player))
        {
            if(!MinecraftForge.EVENT_BUS.post(new AbilitechTargetedEvent(player, target, this, techSlot, true)))
                target.setAir(300);

            if(MinecraftForge.EVENT_BUS.post(new AbilitechTargetedEvent(player, target, this, techSlot, false)))
                continue;

            Vec3d vec = new Vec3d(player.posX-target.posX, player.posY-target.posY, player.posZ-target.posZ).normalize();

            target.velocityChanged = true;
            target.isAirBorne = true;
            float f = MathHelper.sqrt(vec.x * vec.x + vec.z * vec.z + vec.y * vec.y);
            target.motionX /= 2.0D;
            target.motionZ /= 2.0D;
            target.motionX += vec.x / (double)f * (double)strength;
            target.motionY += vec.y / (double)f * (double)strength;
            target.motionZ += vec.z / (double)f * (double)strength;
        }

        return true;
    }
    
    //maybe change it to 6 because it needs 6 food to work TEST THIS
    @Override
	public boolean isUsableExternally(World world, EntityPlayer player)
	{
		return player.getFoodStats().getFoodLevel() >= 4 && super.isUsableExternally(world, player);
	}
}
