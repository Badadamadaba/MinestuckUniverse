package com.cibernet.minestuckuniverse.proxy;

import com.cibernet.minestuckuniverse.MSUModelManager;
import com.cibernet.minestuckuniverse.entity.classes.EntityAcheron;
import com.cibernet.minestuckuniverse.entity.models.ModelAcheron;
import com.mraof.minestuck.client.model.ModelLich;
import com.mraof.minestuck.client.renderer.entity.RenderEntityMinestuck;
import com.mraof.minestuck.entity.underling.EntityLich;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
    
        RenderingRegistry.registerEntityRenderingHandler(EntityAcheron.class, RenderEntityMinestuck.getFactory(new ModelAcheron(), 0.5F));
        
        MinecraftForge.EVENT_BUS.register(MSUModelManager.class);
    }
}