package com.hfr.main;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.hfr.blocks.ModBlocks;
import com.hfr.entity.*;
import com.hfr.handler.GUIHandler;
import com.hfr.items.ModItems;
import com.hfr.lib.RefStrings;
import com.hfr.packet.PacketDispatcher;
import com.hfr.tileentity.*;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = RefStrings.MODID, name = RefStrings.NAME, version = RefStrings.VERSION)
public class MainRegistry
{
	@Instance(RefStrings.MODID)
	public static MainRegistry instance;
	
	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;
	
	@Metadata
	public static ModMetadata meta;
	
	public static Logger logger;
	
	public static int radarRange = 1000;
	public static int radarBuffer = 30;
	public static int radarAltitude = 55;
	public static int radarConsumption = 50;
	
	public static int fieldBase = 100;
	public static int fieldRange = 50;
	public static int fieldHealth = 25;
	public static int upRange = 16;
	public static int upHealth = 50;
	public static int fieldDet = 25;
	public static int baseCooldown = 100;
	public static int rangeCooldown = 100;

	public static double exSpeed = 1D;
	public static double exWeight = 2D;
	public static int mult = 100;
	public static double flanmult = 1D;
	public static boolean flancalc = true;

	public static int abDelay = 40;
	public static int abRange = 500;
	public static int empRadius = 500;
	public static int empDuration = 5 * 60 * 20;
	public static int empParticle = 20;
	public static boolean empSpecial = true;
	public static int padBuffer = 100000000;
	public static int padUse = 50000000;
	public static int mHealth = 15;
	public static int mDespawn = 5000;
	public static int mSpawn = 6000;
	public static int derrickBuffer = 100000;
	public static int derrickUse = 1000;

	public static int mushLife = 15 * 20;
	public static int mushScale = 80;
	public static int fireDuration = 4 * 20;
	public static int t1blast = 50;
	public static int t2blast = 100;
	public static int t3blast = 150;
	
	public static int crafting = 0;
	
	public static boolean freeRadar = false;
	public static boolean sound = true;
	public static boolean comparator = false;
	
	Random rand = new Random();

	public static DamageSource blast = (new DamageSource("blast")).setExplosion();
	public static DamageSource zyklon = (new DamageSource("zyklon")).setDamageBypassesArmor().setDamageIsAbsolute();
	
	@EventHandler
	public void PreLoad(FMLPreInitializationEvent PreEvent)
	{
		if(logger == null)
			logger = PreEvent.getModLog();
		
		ModBlocks.mainRegistry();
		ModItems.mainRegistry();
		loadConfig(PreEvent);
		CraftingManager.mainRegistry();
		proxy.registerRenderInfo();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
		
		GameRegistry.registerTileEntity(TileEntityMachineSiren.class, "tileentity_hfr_siren");
		GameRegistry.registerTileEntity(TileEntityMachineRadar.class, "tileentity_hfr_radar");
		GameRegistry.registerTileEntity(TileEntityForceField.class, "tileentity_hfr_field");
		GameRegistry.registerTileEntity(TileEntityVaultDoor.class, "tileentity_hfr_vault");
		GameRegistry.registerTileEntity(TileEntityDummy.class, "tileentity_hfr_dummy");
		GameRegistry.registerTileEntity(TileEntityHatch.class, "tileentity_hfr_hatch");
		GameRegistry.registerTileEntity(TileEntityLaunchPad.class, "tileentity_hfr_launchpad");
		GameRegistry.registerTileEntity(TileEntityChlorineSeal.class, "tileentity_hfr_gaschamber");
		GameRegistry.registerTileEntity(TileEntityMachineDerrick.class, "tileentity_hfr_derrick");
		GameRegistry.registerTileEntity(TileEntityDebug.class, "tileentity_hfr_devon_truck");

		int id = 0;
	    EntityRegistry.registerModEntity(EntityMissileGeneric.class, "entity_missile_v2", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileIncendiary.class, "entity_missile_v2F", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileStrong.class, "entity_missile_large", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileIncendiaryStrong.class, "entity_missile_largeF", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileBurst.class, "entity_missile_korea", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileInferno.class, "entity_missile_koreaF", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileAntiBallistic.class, "entity_missile_anti", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileEMPStrong.class, "entity_missile_emp", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityNukeCloudSmall.class, "entity_mushroom_cloud", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileDecoy.class, "entity_missile_decoy", id++, this, 1000, 1, true);

	    EntityRegistry.registerModEntity(EntityEMP.class, "entity_lingering_emp", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityBlast.class, "entity_deathblast", id++, this, 1000, 1, true);
	
		ForgeChunkManager.setForcedChunkLoadingCallback(this, new LoadingCallback() {
			
	        @Override
	        public void ticketsLoaded(List<Ticket> tickets, World world) {
	            for(Ticket ticket : tickets) {
	            	
	                if(ticket.getEntity() instanceof IChunkLoader) {
	                    ((IChunkLoader)ticket.getEntity()).init(ticket);
	                }
	            }
	        }
	    });
		
		FMLCommonHandler.instance().bus().register(new CommonEventHandler());
	}

	@EventHandler
	public static void load(FMLInitializationEvent event)
	{
		
	}
	
	@EventHandler
	public static void PostLoad(FMLPostInitializationEvent PostEvent)
	{
	}
	
	public static List<Block> blastShields = new ArrayList();
	
	public void loadConfig(FMLPreInitializationEvent event)
	{
		if(logger == null)
			logger = event.getModLog();
		
		PacketDispatcher.registerPackets();

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();Property propRadarRange = config.get("RADAR", "radarRange", 1000);
        propRadarRange.comment = "Range of the radar, 50 will result in 100x100 block area covered";
        radarRange = propRadarRange.getInt();
        
        Property propRadarBuffer = config.get("RADAR", "radarBuffer", 30);
        propRadarBuffer.comment = "How high entities have to be above the radar to be detected";
        radarBuffer = propRadarBuffer.getInt();
        
        Property propRadarAltitude = config.get("RADAR", "radarAltitude", 55);
        propRadarAltitude.comment = "Y height required for the radar to work";
        radarAltitude = propRadarAltitude.getInt();
        
        Property propRadarConsumption = config.get("RADAR", "radarConsumption", 50);
        propRadarConsumption.comment = "Amount of RF per tick required for the radar to work";
        radarConsumption = propRadarConsumption.getInt();
        
        Property propCrafting = config.get(Configuration.CATEGORY_GENERAL, "craftingDifficulty", 0);
        propCrafting.comment = "How difficult the crafting recipes are, from 0 - 2 (very easy to hard), values outside this range make most stuff uncraftable";
        crafting = propCrafting.getInt();
        
        Property propFree = config.get(Configuration.CATEGORY_GENERAL, "freeRadar", false).setDefaultValue(false);
        propFree.comment = "Whether or not the radar and shield are free to use, i.e. do not require RF";
        freeRadar = propFree.getBoolean();
        
        Property propSound = config.get("RADAR", "radarPing", true).setDefaultValue(true);
        propSound.comment = "Whether or not the radar makes frequent pinging sounds";
        sound = propSound.getBoolean();
        
        Property propComp = config.get("RADAR", "comparatorOutput", false).setDefaultValue(false);
        propComp.comment = "Whether or not the radar uses a comparator to output it's signal, will directly output otherwise";
        comparator = propComp.getBoolean();
        
        Property propFB = config.get("FORCEFIELD", "fieldBaseConsumption", 100);
        propFB.comment = "Amount of RF per tick required for the forcefield to work";
        fieldBase = propFB.getInt();
        
        Property propFR = config.get("FORCEFIELD", "fieldRangeConsumption", 50);
        propFR.comment = "Amount of RF per tick per forcefield range upgrade";
        fieldRange = propFR.getInt();
        
        Property propFH = config.get("FORCEFIELD", "fieldHealthConsumption", 25);
        propFH.comment = "Amount of RF per tick per forcefield shield upgrade";
        fieldHealth = propFH.getInt();
        
        Property propER = config.get("FORCEFIELD", "fieldRangeUpgradeEffect", 16);
        propER.comment = "The radius increase per forcefield range upgrade";
        upRange = propER.getInt();
        
        Property propEH = config.get("FORCEFIELD", "fieldHealthUpgradeEffect", 50);
        propEH.comment = "The HP increase per forcefield shield upgrade";
        upHealth = propEH.getInt();
        
        Property propMS = config.get("FORCEFIELD", "fieldSpeedImpactExponent", 100);
        propMS.comment = "The exponent of the projectile's speed in the damage equation (100 -> ^1)";
        exSpeed = propMS.getInt() * 0.01;
        
        Property propMM = config.get("FORCEFIELD", "fieldMassImpactExponent", 200);
        propMM.comment = "The exponent of the projectile's mass (hitbox size) in the damage equation (200 -> ^2)";
        exWeight = propMM.getInt() * 0.01;
        
        Property propM = config.get("FORCEFIELD", "fieldImpactMultiplier", 100);
        propM.comment = "The general multiplier of the damage equation (hitbox size ^ massExp * entity speed ^ speedExp * mult)";
        mult = propM.getInt();
        
        Property propFLAN = config.get("FORCEFIELD", "fieldImpactFlanMultiplier", 100);
        propFLAN.comment = "The damage multiplier of flan's mod projectiles. 100 is the normal damage it would do to a player, 200 is double damage, etc.";
        flanmult = propFLAN.getInt() * 0.01;
        
        Property propDet = config.get("FORCEFIELD", "fieldEntityDetectionRange", 25);
        propDet.comment = "Padding of the entity detection range (effective range is this + shield radius), may requires to be increased to detect VERY fast projectiles";
        fieldDet = propDet.getInt();
        
        Property propAF = config.get("FORCEFIELD", "useFlanSpecialCase", true).setDefaultValue(true);
        propAF.comment = "Whether or not the forcefield should use a special function to pull the damage value out of flan's mod projectiles. Utilizes the worst code and the shittiest programming techniques in the universe, but flan's bullets may not behave as expected if this option is turned off";
        flancalc = propAF.getBoolean();
        
        Property propBC = config.get("FORCEFIELD", "fieldBaseCooldown", 300);
        propBC.comment = "Duration of the base cooldown in ticks after the forcefield has been broken";
        baseCooldown = propBC.getInt();
        
        Property propRC = config.get("FORCEFIELD", "fieldRangeCooldown", 3);
        propRC.comment = "Duration of the additional cooldown in ticks per block of radius. Standard radius is 16, the additional cooldown duraion is therefore 48 ticks, or 348 in total. Values below 5 are recommended.";
        rangeCooldown = propRC.getInt();
        
        Property propABDelay = config.get("MISSILE", "antiBallisticDelay", 40);
        propABDelay.comment = "Targeting delay of the AB missile in ticks. The AB will fly straight up ignoring missiles until this much time has passed.";
        abDelay = propABDelay.getInt();
        
        Property propABRadius = config.get("MISSILE", "antiBallisticRange", 500);
        propABRadius.comment = "The detection range of the AB missile.";
        abRange = propABRadius.getInt();
        
        Property propEMPDura = config.get("MISSILE", "empDuration", 5*60*20);
        propEMPDura.comment = "How long machines will stay disabled after EMP strike";
        empDuration = propEMPDura.getInt();
        
        Property propEMPRange = config.get("MISSILE", "empRange", 100);
        propEMPRange.comment = "The radius of the EMP effect";
        empRadius = propEMPRange.getInt();
        
        Property propEMPPart = config.get("MISSILE", "empParticleDelay", 20);
        propEMPPart.comment = "The average delay between spark particles of disabled machines. Should be above 10. 0 will crash the game, so don't do that.";
        empParticle = propEMPPart.getInt();
        
        Property empSpecialP = config.get("MISSILE", "empHFSpecialFunction", true);
        empSpecialP.comment = "Whether or not the EMP should use a special function to properly set all machine's RF to 0";
        empSpecial = empSpecialP.getBoolean();
        
        Property padBuf = config.get("MISSILE", "launchPadStorage", 100*1000*1000);
        padBuf.comment = "The amount of RF the launch pad can hold.";
        padBuffer = padBuf.getInt();
        
        Property padUseP = config.get("MISSILE", "launchPadRequirement", 50*1000*1000);
        padUseP.comment = "How much RF is required for a rocket launch. Has to be smaller or equal to the buffer size.";
        padUse = padUseP.getInt();
        
        Property mushLifeP = config.get("MISSILE", "fireballLife", 15 * 20);
        mushLifeP.comment = "How many ticks the mushroom cloud will persist";
        mushLife = mushLifeP.getInt();
        
        Property mushScaleP = config.get("MISSILE", "fireballScale", 80);
        mushScaleP.comment = "Scale of the mushroom cloud";
        mushScale = mushScaleP.getInt();
        
        Property fireDurationP = config.get("MISSILE", "fireDuration", 4 * 20);
        fireDurationP.comment = "How long the fire blast will last";
        fireDuration = fireDurationP.getInt();
        
        Property t1blastP = config.get("MISSILE", "tier1Blast", 50);
        t1blastP.comment = "Blast radius(c) of tier 1 missiles";
        t1blast = t1blastP.getInt();
        
        Property t2blastP = config.get("MISSILE", "tier2Blast", 100);
        t2blastP.comment = "Blast radius(c) of tier 2 missiles";
        t2blast = t2blastP.getInt();
        
        Property t3blastP = config.get("MISSILE", "tier3Blast", 150);
        t3blastP.comment = "Blast radius(c) of tier 3 missiles";
        t3blast = t3blastP.getInt();
        
        Property mHealthP = config.get("MISSILE", "missileHealth", 15);
        mHealthP.comment = "How much beating a missile can take before it goes to commit unlive.";
        mHealth = mHealthP.getInt();
        
        Property mDespawnP = config.get("MISSILE", "simpleMissileDespawn", 5000);
        mDespawnP.comment = "Altitude at which cheapo missiles despawn and teleport to the target";
        mDespawn = mDespawnP.getInt();
        
        Property mSpawnP = config.get("MISSILE", "simpleMissileSpawn", 6000);
        mSpawnP.comment = "Altitude at which cheapo missiles spawn in when teleporting";
        mSpawn = mSpawnP.getInt();
        
        Property drywall = config.get("MISSILE", "blastShields", new String[] {
        		"" + Block.getIdFromBlock(Blocks.obsidian),
        		"" + Block.getIdFromBlock(ModBlocks.concrete),
        		"" + Block.getIdFromBlock(ModBlocks.concrete_bricks),
        		"" + Block.getIdFromBlock(ModBlocks.vault_door),
        		"" + Block.getIdFromBlock(ModBlocks.vault_door_dummy)});
        drywall.comment = "What blocks can block fire blasts (default: obsidian, concrete, concrete bricks, vault door, vault door dummy)";
        String[] vals = drywall.getStringList();
        
        for(String val : vals) {
        	blastShields.add(Block.getBlockById(Integer.parseInt(val)));
        }
        
        Property dBufferP = config.get("DERRICK", "derrickBuffer", 100000);
        dBufferP.comment = "How much energy the derrick can store";
        derrickBuffer = dBufferP.getInt();
        
        Property dUseP = config.get("DERRICK", "derrickConsumption", 1000);
        dUseP.comment = "How much energy the derrick uses per tick";
        derrickUse = dUseP.getInt();
        
        config.save();
	}
}