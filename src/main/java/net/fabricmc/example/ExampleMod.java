package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {
	
	public static Block VAULT_BLOCK;
	public static Block PARTY_LAMP_BLOCK;
	
    public static Item VAULT_BLOCK_ITEM;
    public static Item PARTY_LAMP_BLOCK_ITEM;
    
	public static BlockEntityType<VaultBlockEntity> VAULT_BLOCK_ENTITY;
	public static BlockEntityType<PartyLampBlockEntity> PARTY_LAMP_BLOCK_ENTITY;
	
    public static Identifier VAULT;
    public static Identifier PARTY_LAMP;
    
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
		VAULT      = new Identifier(Constants.NAMESPACE, "vault_block");
		PARTY_LAMP = new Identifier(Constants.NAMESPACE, "party_lamp_block");
	}
	
	private static void registerBlocks() {
		VAULT_BLOCK = Registry.register(Registry.BLOCK, VAULT, 
				new VaultBlock(FabricBlockSettings.of(Material.METAL).requiresTool().breakByTool(FabricToolTags.PICKAXES, 2)
						 						  .strength(4.0f, 10000.0f).sounds(BlockSoundGroup.METAL)));
		PARTY_LAMP_BLOCK = Registry.register(Registry.BLOCK, PARTY_LAMP, 
				new PartyLampBlock(FabricBlockSettings.of(Material.METAL).requiresTool().breakByTool(FabricToolTags.PICKAXES, 2)
													  .strength(4.0f, 10000.0f).sounds(BlockSoundGroup.METAL)));
	}
	
	private static void registerItems() {
		System.out.println("registering items");
		VAULT_BLOCK_ITEM      = Registry.register(Registry.ITEM, VAULT, new BlockItem(VAULT_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS).maxCount(1)));
		System.out.println(("max count: " + VAULT_BLOCK_ITEM.getMaxCount()));
		PARTY_LAMP_BLOCK_ITEM = Registry.register(Registry.ITEM, PARTY_LAMP, new BlockItem(PARTY_LAMP_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
	}
	
	private static void registerEntities() {
		VAULT_BLOCK_ENTITY      = Registry.register(Registry.BLOCK_ENTITY_TYPE, VAULT, FabricBlockEntityTypeBuilder.create(VaultBlockEntity::new, VAULT_BLOCK).build(null));
		PARTY_LAMP_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, PARTY_LAMP, FabricBlockEntityTypeBuilder.create(PartyLampBlockEntity::new, PARTY_LAMP_BLOCK).build(null));
	}
	
	private static void registerScreenHandlers() {
        VAULT_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(VAULT, VaultScreenHandler::new);
	}

}
