package com.cibernet.minestuckuniverse;

import com.cibernet.minestuckuniverse.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;


@Mod(
        modid = MinestuckUniverse.MODID,
        name = MinestuckUniverse.NAME,
        version = MinestuckUniverse.VERSION,
        dependencies = "required-after:minestuck@[1.2.300.7,);",
        guiFactory = "com.cibernet.minestuckuniverse.gui.MSUGuiFactory"
)
public class MinestuckUniverse
{
    public static final String MODID = "minestuckuniverse";
    public static final String NAME = "Minestuck Universe";
    public static final String SHORT = "MSU";
    public static final String VERSION = "@VERSION@";
    
    @Mod.Instance("minestuckuniverse")
    public static MinestuckUniverse instance;
    @SidedProxy(
            clientSide = "com.cibernet.minestuckuniverse.proxy.ClientProxy",
            serverSide = "com.cibernet.minestuckuniverse.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    public static boolean isThaumLoaded;
    public static boolean isBotaniaLoaded;
    public static boolean isSplatcraftLodaded;
    public static boolean isCarryOnLoaded;
    public static boolean isArsenalLoaded;
    public static boolean isVcLoaded;
    public static boolean isMSGTLoaded;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MSUConfig.load(event.getSuggestedConfigurationFile(), event.getSide());

        isThaumLoaded = Loader.isModLoaded("thaumcraft");
        isBotaniaLoaded = Loader.isModLoaded("botania");
        isArsenalLoaded = Loader.isModLoaded("minestuckarsenal");
        isSplatcraftLodaded = Loader.isModLoaded("splatcraft");
        isMSGTLoaded = Loader.isModLoaded("minestuckgodtier");
        isCarryOnLoaded = Loader.isModLoaded("carryon");
        isVcLoaded = Loader.isModLoaded("variedcommodities");

        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event)
    {
        proxy.serverStarted();
    }
}
