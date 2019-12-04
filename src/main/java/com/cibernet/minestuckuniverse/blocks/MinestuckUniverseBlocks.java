package com.cibernet.minestuckuniverse.blocks;

import com.cibernet.minestuckuniverse.MSUModelManager;
import com.cibernet.minestuckuniverse.MinestuckUniverse;
import com.cibernet.minestuckuniverse.alchemy.MinestuckUniverseGrist;
import com.cibernet.minestuckuniverse.items.MinestuckUniverseItems;
import com.mraof.minestuck.alchemy.GristType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MinestuckUniverseBlocks
{

    //Base
    public static Block magicBlock = new MSUBlockBase(Material.CLAY, "magic_block", "magicBlock");

    public static Block sbahjBedrock = new MSUBlockBase(Material.CAKE, "sbahj_bedrock", "sbahjBedrock").setResistance(0F).setHardness(-1F);
    public static Block zillyStone = new MSUBlockBase(Material.ROCK, "zillystone", "zillystone").setResistance(999F).setHardness(5.5F);
	
	public static Block gristBlockAmber = new BlockGrist(GristType.Amber);
	public static Block gristBlockAmethyst = new BlockGrist(GristType.Amethyst);
	public static Block gristBlockArtifact = new BlockGrist(GristType.Artifact);
	public static Block gristBlockBuild = new BlockGrist(GristType.Build);
	public static Block gristBlockCaulk = new BlockGrist(GristType.Caulk);
	public static Block gristBlockChalk = new BlockGrist(GristType.Chalk);
	public static Block gristBlockCobalt = new BlockGrist(GristType.Cobalt);
	public static Block gristBlockDiamond = new BlockGrist(GristType.Diamond);
	public static Block gristBlockGarnet = new BlockGrist(GristType.Garnet);
	public static Block gristBlockGold = new BlockGrist(GristType.Gold);
	public static Block gristBlockIodine = new BlockGrist(GristType.Iodine);
	public static Block gristBlockMarble = new BlockGrist(GristType.Marble);
	public static Block gristBlockMercury = new BlockGrist(GristType.Mercury);
	public static Block gristBlockQuartz = new BlockGrist(GristType.Quartz);
	public static Block gristBlockRuby = new BlockGrist(GristType.Ruby);
	public static Block gristBlockRust = new BlockGrist(GristType.Rust);
	public static Block gristBlockShale = new BlockGrist(GristType.Shale);
	public static Block gristBlockSulfur = new BlockGrist(GristType.Sulfur);
	public static Block gristBlockTar = new BlockGrist(GristType.Tar);
	public static Block gristBlockUranium = new BlockGrist(GristType.Uranium);
	public static Block gristBlockZillium = new BlockGrist(GristType.Zillium);
	public static Block gristBlockVis = new BlockGrist(MinestuckUniverseGrist.Vis);
	public static Block gristBlockMana = new BlockGrist(MinestuckUniverseGrist.Mana);
	
	
	//Thaumcraft
    public static Block thaumChasis = new MSUBlockBase(Material.IRON, "thaumic_machine_frame", "thaumChasis");

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();

        registerBlock(registry, magicBlock, true);
        registerBlock(registry, sbahjBedrock, true);
        registerBlock(registry, zillyStone, true);
	
	
		registerBlock(registry, gristBlockBuild, true);
		registerBlock(registry, gristBlockAmber, true);
		registerBlock(registry, gristBlockAmethyst, true);
		registerBlock(registry, gristBlockArtifact, true);
		registerBlock(registry, gristBlockCaulk, true);
		registerBlock(registry, gristBlockChalk, true);
		registerBlock(registry, gristBlockCobalt, true);
		registerBlock(registry, gristBlockDiamond, true);
		registerBlock(registry, gristBlockGarnet, true);
		registerBlock(registry, gristBlockGold, true);
		registerBlock(registry, gristBlockIodine, true);
		registerBlock(registry, gristBlockMarble, true);
		registerBlock(registry, gristBlockMercury, true);
		registerBlock(registry, gristBlockQuartz, true);
		registerBlock(registry, gristBlockRuby, true);
		registerBlock(registry, gristBlockRust, true);
		registerBlock(registry, gristBlockShale, true);
		registerBlock(registry, gristBlockSulfur, true);
		registerBlock(registry, gristBlockTar, true);
		registerBlock(registry, gristBlockUranium, true);
		registerBlock(registry, gristBlockZillium, true);
        
        if(MinestuckUniverse.isThaumLoaded)
        {
        	registerBlock(registry, gristBlockVis, true);
            //registerBlock(registry, thaumChasis, true);
            //registerBlock(registry, gristDecomposer, true);
        }
        
        if(MinestuckUniverse.isBotaniaLoaded)
			registerBlock(registry, gristBlockMana, true);
    }
    
    private static Block registerBlock(IForgeRegistry<Block> registry, Block block, boolean... hasItem)
    {
        registry.register(block);
        MSUModelManager.blocks.add(block);
        
        if(hasItem.length > 0)
			MinestuckUniverseItems.itemBlocks.add(block);
        
        return block;
    }
	
	
	private static void registerGristBlocks(IForgeRegistry<Block> registry)
	{
	}
}
