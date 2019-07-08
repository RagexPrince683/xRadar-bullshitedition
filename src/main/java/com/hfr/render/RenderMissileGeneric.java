package com.hfr.render;

import org.lwjgl.opengl.GL11;

import com.hfr.entity.EntityMissileAntiBallistic;
import com.hfr.entity.EntityMissileDecoy;
import com.hfr.entity.EntityMissileGeneric;
import com.hfr.entity.EntityMissileIncendiary;
import com.hfr.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderMissileGeneric extends Render {
	
	public RenderMissileGeneric() { }

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		if(p_76986_1_.getDataWatcher().getWatchableObjectInt(9) == 1)
			return;
		
		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
        
        if(p_76986_1_ instanceof EntityMissileGeneric)
        	bindTexture(ResourceManager.missileV2_HE_tex);
        if(p_76986_1_ instanceof EntityMissileIncendiary)
        	bindTexture(ResourceManager.missileV2_IN_tex);
        if(p_76986_1_ instanceof EntityMissileAntiBallistic)
        	bindTexture(ResourceManager.missileAA_tex);
        if(p_76986_1_ instanceof EntityMissileDecoy)
        	bindTexture(ResourceManager.missile_decoy_tex);
        ResourceManager.missileV2.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.missileV2_HE_tex;
	}

}