package com.cibernet.minestuckuniverse.fillerItems;

import com.cibernet.minestuckuniverse.MinestuckUniverse;
import com.cibernet.minestuckuniverse.items.MinestuckUniverseItems;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.CombinationRegistry;
import com.mraof.minestuck.alchemy.GristRegistry;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.GristType;
import com.mraof.minestuck.item.MinestuckItems;
import mezz.jei.api.gui.IGuiItemStackGroup;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.cibernet.minestuckuniverse.fillerItems.MSUFillerItems.*;
import static com.mraof.minestuck.alchemy.CombinationRegistry.Mode.MODE_AND;
import static com.mraof.minestuck.alchemy.CombinationRegistry.Mode.MODE_OR;
import static com.mraof.minestuck.alchemy.GristType.*;

public class MSUFillerAlchemyRecipes
{
	public static void registerRecipes()
	{
		//Grist Conversions
		GristRegistry.addGristConversion(new ItemStack(diverHelmet), new GristSet(new GristType[] {Rust}, new int[] {64}));
		GristRegistry.addGristConversion(new ItemStack(murica), new GristSet(Artifact, -1));
		GristRegistry.addGristConversion(new ItemStack(muricaSouth), new GristSet(new GristType[] {Artifact, Garnet}, new int[] {-10, 1}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckUniverseItems.chc), new GristSet(new GristType[] {Build, Uranium}, new int[] {100, 34}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckUniverseItems.ctd), new GristSet(new GristType[] {Shale, Tar, Mercury, Sulfur}, new int[] {100, 34, 236, 152}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckUniverseItems.cht), new GristSet(new GristType[] {Shale, Build, Uranium, Mercury, Chalk, Quartz, Diamond, Zillium}, new int[] {999999999, 999999999, 999, 9999, 9999, 20, 40, 20}));
		GristRegistry.addGristConversion(new ItemStack(MSUFillerItems.CHS), new GristSet(new GristType[] {Shale, Build, Uranium, Mercury, Chalk, Quartz, Diamond, Zillium}, new int[] {999999999, 999999999, 999, 9999, 9999, 999, 999, 999}));
		GristRegistry.addGristConversion(new ItemStack(luxuryStick), new GristSet(new GristType[] {Build, Diamond}, new int[] {1, 9}));
		GristRegistry.addGristConversion(new ItemStack(royalStick), new GristSet(new GristType[] {Build, Gold}, new int[] {1, 9}));
		GristRegistry.addGristConversion(new ItemStack(reinforcedStick), new GristSet(new GristType[] {Build, Rust}, new int[] {1, 9}));
		GristRegistry.addGristConversion(new ItemStack(diamondIngot), new GristSet(new GristType[] {Diamond}, new int[] {8}));
		GristRegistry.addGristConversion(new ItemStack(luxuriousGem), new GristSet(new GristType[] {Diamond, Gold}, new int[] {4, 4}));
		GristRegistry.addGristConversion(new ItemStack(diamerald), new GristSet(new GristType[] {Diamond, Ruby}, new int[] {9, 9}));
		GristRegistry.addGristConversion(new ItemStack(oakPlank), new GristSet(new GristType[] {Build}, new int[] {3}));
		GristRegistry.addGristConversion(new ItemStack(birchPlank), new GristSet(new GristType[] {Build}, new int[] {3}));
		GristRegistry.addGristConversion(new ItemStack(sprucePlank), new GristSet(new GristType[] {Build}, new int[] {3}));
		GristRegistry.addGristConversion(new ItemStack(darkOakPlank), new GristSet(new GristType[] {Build}, new int[] {3}));
		GristRegistry.addGristConversion(new ItemStack(junglePlank), new GristSet(new GristType[] {Build}, new int[] {3}));
		GristRegistry.addGristConversion(new ItemStack(acaciaPlank), new GristSet(new GristType[] {Build}, new int[] {3}));
		//Alchemy
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_HELMET), new ItemStack(Blocks.IRON_BLOCK), MODE_AND, false, false, new ItemStack(diverHelmet));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.itemFrog, 1, 1), new ItemStack(Items.LEATHER_HELMET), MODE_AND, true, false, new ItemStack(froghat));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.cruxiteDowel), new ItemStack(MinestuckItems.rawUranium), MODE_AND, false, false, new ItemStack(MinestuckUniverseItems.chc));
		CombinationRegistry.addCombination(new ItemStack(MinestuckUniverseItems.chc), new ItemStack(Items.POTIONITEM), MODE_AND, false, false, new ItemStack(MinestuckUniverseItems.ctd));
		CombinationRegistry.addCombination(new ItemStack(MinestuckUniverseItems.ctd), new ItemStack(Items.PRISMARINE_SHARD), MODE_AND, false, false, new ItemStack(MinestuckUniverseItems.cht));
		CombinationRegistry.addCombination(new ItemStack(MinestuckUniverseItems.ctd), new ItemStack(Items.PRISMARINE_SHARD), MODE_OR, false, false, new ItemStack(MSUFillerItems.CHS));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.sbahjPoster), new ItemStack(Items.COMPASS), MODE_OR, new ItemStack(murica));
		CombinationRegistry.addCombination(new ItemStack(murica), new ItemStack(Blocks.WOOL, 1, EnumDyeColor.RED.getMetadata()), MODE_AND, false, true, new ItemStack(muricaSouth));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(Items.DIAMOND), MODE_AND, false, false, new ItemStack(luxuryStick));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(Items.GOLD_INGOT), MODE_AND, false, false, new ItemStack(royalStick));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(Items.IRON_INGOT), MODE_AND, false, false, new ItemStack(reinforcedStick));
		CombinationRegistry.addCombination(new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.DIAMOND), MODE_OR, false, false, new ItemStack(diamondIngot));
		CombinationRegistry.addCombination(new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.DIAMOND), MODE_AND, false, false, new ItemStack(luxuriousGem));
		CombinationRegistry.addCombination(new ItemStack(Items.DIAMOND), new ItemStack(Items.EMERALD), MODE_AND, false, false, new ItemStack(diamerald));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(Block.getBlockById(5)), MODE_OR, false, false, new ItemStack(oakPlank));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(Block.getBlockById(5-2)), MODE_OR, false, false, new ItemStack(birchPlank));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(Block.getBlockById(5-1)), MODE_OR, false, false, new ItemStack(sprucePlank));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(Block.getBlockById(5-5)), MODE_OR, false, false, new ItemStack(darkOakPlank));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(Block.getBlockById(5-3)), MODE_OR, false, false, new ItemStack(junglePlank));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(Block.getBlockById(5-4)), MODE_OR, false, false, new ItemStack(acaciaPlank));
	}
}
