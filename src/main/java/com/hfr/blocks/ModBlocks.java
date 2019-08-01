package com.hfr.blocks;

import com.hfr.items.ItemRadarBlock;
import com.hfr.items.ItemTankBlock;
import com.hfr.lib.RefStrings;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class ModBlocks {
	
	public static void mainRegistry()
	{
		initializeBlock();
		registerBlock();
	}

	public static Block concrete;
	public static Block concrete_bricks;
	public static Block temp;

	public static Block ore_oil;
	public static Block ore_oil_empty;
	public static Block oil_pipe;

	public static Block seal_controller;
	public static Block seal_frame;
	public static Block seal_hatch;

	public static Block vault_door;
	public static Block vault_door_dummy;

	public static Block vent_chlorine_seal;
	public static Block chlorine_gas;
	
	public static Block machine_radar;
	public static final int guiID_radar = 0;
	public static Block machine_siren;
	public static final int guiID_siren = 1;
	public static Block machine_forcefield;
	public static final int guiID_forcefield = 2;
	public static Block launch_pad;
	public static final int guiID_launchpad = 3;
	public static Block machine_derrick;
	public static final int guiID_derrick = 4;
	public static Block machine_refinery;
	public static final int guiID_refinery = 5;
	public static Block machine_tank;
	public static final int guiID_tank = 6;

	public static Block oil_duct;
	public static Block gas_duct;

	public static Block railgun_plasma;
	public static final int guiID_railgun = 7;
	public static Block cannon_naval;
	public static final int guiID_naval = 8;

	public static Block debug;

	public static Block.SoundType soundTypeConcrete = new ModSoundType("concrete", 0.25F, 1.0F)
    {
        public String func_150496_b()
        {
            return Block.soundTypeStone.func_150496_b();
        }
    };
	public static Block.SoundType soundTypeMetal = new ModSoundType("metal", 0.5F, 1.0F)
    {
        public String func_150496_b()
        {
            return Block.soundTypeMetal.func_150496_b();
        }
    };

	private static void initializeBlock() {
		
		concrete = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("concrete").setHardness(5.0F).setResistance(1000.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":concrete");
		concrete_bricks = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("concrete_bricks").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":concrete_bricks");
		temp = new BlockTemporary(Material.ground).setStepSound(Block.soundTypeGravel).setBlockName("temp").setHardness(1.0F).setResistance(1.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":dirt_alt");

		ore_oil = new BlockGeneric(Material.rock).setBlockName("ore_oil").setHardness(1.0F).setResistance(5.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":ore_oil");
		ore_oil_empty = new BlockGeneric(Material.rock).setBlockName("ore_oil_empty").setHardness(1.0F).setResistance(5.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":ore_oil_empty");
		oil_pipe = new BlockNoDrop(Material.rock).setBlockName("oil_pipe").setHardness(1.0F).setResistance(60.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":oil_pipe");

		seal_controller = new BlockSeal(Material.rock).setStepSound(soundTypeMetal).setBlockName("seal_controller").setHardness(5.0F).setResistance(10000.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":concrete");
		seal_frame = new BlockGeneric(Material.rock).setStepSound(soundTypeMetal).setBlockName("seal_frame").setHardness(5.0F).setResistance(10000.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":seal_frame");
		seal_hatch = new BlockHatch(Material.rock).setStepSound(soundTypeMetal).setBlockName("seal_hatch").setHardness(2.5F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":seal_hatch");

		vault_door = new VaultDoor(Material.rock).setStepSound(soundTypeMetal).setBlockName("vault_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":vault_door");
		vault_door_dummy = new DummyBlockVault(Material.rock).setStepSound(soundTypeMetal).setBlockName("vault_door_dummy").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":concrete");
		
		machine_radar = new MachineRadar(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":machine_radar");
		machine_siren = new MachineSiren(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_siren").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":machine_siren");
		launch_pad = new LaunchPad(Material.iron).setStepSound(soundTypeMetal).setBlockName("launch_pad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		machine_forcefield = new MachineForceField(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_forcefield").setLightLevel(1.0F).setHardness(5.0F).setResistance(100.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":machine_forcefield");
		machine_derrick = new MachineDerrick(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_derrick").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":machine_derrick");
		machine_refinery = new MachineRefinery(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_refinery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":machine_refinery");
		machine_tank = new MachineTank(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":tank");

		oil_duct = new BlockDuct(Material.iron).setStepSound(soundTypeMetal).setBlockName("oil_duct").setHardness(2.5F).setResistance(1.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":oil_duct_icon");
		gas_duct = new BlockDuct(Material.iron).setStepSound(soundTypeMetal).setBlockName("gas_duct").setHardness(2.5F).setResistance(1.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":gas_duct_icon");

		vent_chlorine_seal = new BlockClorineSeal(Material.iron).setStepSound(soundTypeMetal).setBlockName("vent_chlorine_seal").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone);
		chlorine_gas = new BlockClorine(Material.cloth).setBlockName("chlorine_gas").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":chlorine_gas");
		
		railgun_plasma = new RailgunPlasma(Material.iron).setStepSound(soundTypeMetal).setBlockName("railgun_plasma").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":railgun_plasma");
		cannon_naval = new CannonNaval(Material.iron).setStepSound(soundTypeMetal).setBlockName("cannon_naval").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":cannon_naval");
		
		debug = new BlockDebug(Material.cloth).setStepSound(soundTypeMetal).setBlockName("debug").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":debug");
	}

	private static void registerBlock() {

		GameRegistry.registerBlock(concrete, concrete.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_bricks, concrete_bricks.getUnlocalizedName());
		GameRegistry.registerBlock(temp, temp.getUnlocalizedName());

		GameRegistry.registerBlock(ore_oil, ore_oil.getUnlocalizedName());
		GameRegistry.registerBlock(ore_oil_empty, ore_oil_empty.getUnlocalizedName());
		GameRegistry.registerBlock(oil_pipe, oil_pipe.getUnlocalizedName());

		GameRegistry.registerBlock(seal_controller, seal_controller.getUnlocalizedName());
		GameRegistry.registerBlock(seal_frame, seal_frame.getUnlocalizedName());
		GameRegistry.registerBlock(seal_hatch, seal_hatch.getUnlocalizedName());
		
		GameRegistry.registerBlock(vault_door, vault_door.getUnlocalizedName());
		GameRegistry.registerBlock(vault_door_dummy, vault_door_dummy.getUnlocalizedName());
		
		GameRegistry.registerBlock(vent_chlorine_seal, vent_chlorine_seal.getUnlocalizedName());
		GameRegistry.registerBlock(chlorine_gas, chlorine_gas.getUnlocalizedName());
		
		GameRegistry.registerBlock(machine_radar, ItemRadarBlock.class, machine_radar.getUnlocalizedName());
		GameRegistry.registerBlock(machine_siren, machine_siren.getUnlocalizedName());
		GameRegistry.registerBlock(machine_forcefield, ItemRadarBlock.class, machine_forcefield.getUnlocalizedName());
		GameRegistry.registerBlock(machine_derrick, ItemRadarBlock.class, machine_derrick.getUnlocalizedName());
		GameRegistry.registerBlock(machine_refinery, ItemRadarBlock.class, machine_refinery.getUnlocalizedName());
		GameRegistry.registerBlock(machine_tank, ItemTankBlock.class, machine_tank.getUnlocalizedName());

		GameRegistry.registerBlock(oil_duct, oil_duct.getUnlocalizedName());
		GameRegistry.registerBlock(gas_duct, gas_duct.getUnlocalizedName());

		GameRegistry.registerBlock(launch_pad, ItemRadarBlock.class, launch_pad.getUnlocalizedName());
		GameRegistry.registerBlock(railgun_plasma, ItemRadarBlock.class, railgun_plasma.getUnlocalizedName());
		GameRegistry.registerBlock(cannon_naval, ItemRadarBlock.class, cannon_naval.getUnlocalizedName());

		GameRegistry.registerBlock(debug, ItemRadarBlock.class, debug.getUnlocalizedName());
	}
}
