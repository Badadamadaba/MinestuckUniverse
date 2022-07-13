package com.cibernet.minestuckuniverse.events;

import com.cibernet.minestuckuniverse.skills.abilitech.Abilitech;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

@Cancelable
public class AbilitechTargetedEvent extends Event
{
	final World world;
	final Entity target;
	final Abilitech abilitech;
	final int techSlot;
	final Boolean beneficial; //making this an object so that it can be null

	public AbilitechTargetedEvent(@Nonnull World world, @Nonnull Entity target, @Nonnull Abilitech abilitech, int techSlot, Boolean beneficial)
	{
		this.world = world;
		this.target = target;
		this.abilitech = abilitech;
		this.techSlot = techSlot;
		this.beneficial = beneficial;
	}

	@Override
	public boolean isCancelable() {
		return true;
	}

	public Abilitech getAbilitech() {
		return abilitech;
	}

	public Entity getTarget() {
		return target;
	}

	public int getTechSlot() {
		return techSlot;
	}

	public World getWorld() {
		return world;
	}

	public Boolean isBeneficial() {
		return beneficial;
	}
}
