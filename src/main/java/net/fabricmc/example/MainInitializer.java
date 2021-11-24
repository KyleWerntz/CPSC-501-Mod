package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MainInitializer implements ModInitializer {
	public static Block RGB_BLOCK;
	public static Block VAULT_BLOCK;
	
	public static Item RGB_BLOCK_ITEM;
    public static Item VAULT_BLOCK_ITEM;
    
    public static BlockEntityType<RGBBlockEntity> RGB_BLOCK_ENTITY;
	public static BlockEntityType<VaultBlockEntity> VAULT_BLOCK_ENTITY;
	
	public static Identifier RGB_BLOCK_ID;
    public static Identifier VAULT_ID;
    
    public static ScreenHandlerType<VaultScreenHandler> VAULT_SCREEN_HANDLER;
	
	@Override
	public void onInitialize() {
		registerIDs();
		registerBlocks();
		registerItems();
		registerEntities();
		registerScreenHandlers();
	}
	
	private static void registerIDs() {
		VAULT_ID        = new Identifier(Constants.NAMESPACE, "vault_block");
		RGB_BLOCK_ID = new Identifier(Constants.NAMESPACE, "rgb_block");
	}
	
	private static void registerBlocks() {
		VAULT_BLOCK = Registry.register(Registry.BLOCK, VAULT_ID, 
				new VaultBlock(FabricBlockSettings.of(Material.METAL).requiresTool().breakByTool(FabricToolTags.PICKAXES, 2)
						 						  .strength(4.0f, 10000.0f).sounds(BlockSoundGroup.METAL)));
		RGB_BLOCK = Registry.register(Registry.BLOCK, RGB_BLOCK_ID, 
				new RGBBlock(FabricBlockSettings.of(Material.METAL).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1)
												.strength(2.0f, 100.0f).sounds(BlockSoundGroup.STONE)));
	}
	
	private static void registerItems() {
		VAULT_BLOCK_ITEM = Registry.register(Registry.ITEM, VAULT_ID, new BlockItem(VAULT_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS).maxCount(1)));
		RGB_BLOCK_ITEM   = Registry.register(Registry.ITEM, RGB_BLOCK_ID, new BlockItem(RGB_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}
	
	private static void registerEntities() {
		VAULT_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, VAULT_ID, FabricBlockEntityTypeBuilder.create(VaultBlockEntity::new, VAULT_BLOCK).build(null));
		RGB_BLOCK_ENTITY   = Registry.register(Registry.BLOCK_ENTITY_TYPE, RGB_BLOCK_ID, FabricBlockEntityTypeBuilder.create(RGBBlockEntity::new, RGB_BLOCK).build(null));
	}
	
	private static void registerScreenHandlers() {
        VAULT_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(VAULT_ID, VaultScreenHandler::new);
	}

}
