package net.fabricmc.example;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class VaultBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(Constants.VAULT_SIZE + (2 * Constants.KEY_SIZE), ItemStack.EMPTY);
    
    public VaultBlockEntity(BlockPos pos, BlockState state) {
        super(MainInitializer.VAULT_BLOCK_ENTITY, pos, state);
    }
 
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    
    public void setItems(DefaultedList<ItemStack> items) {
    	this.inventory = items;
    }
    
    @Override
    public boolean isValid(int slot, ItemStack item) {
    	System.out.println("checking if slot " + slot + " is valid");
    	return slot >= 0 && isUnlocked(inventory) ? slot < inventory.size() : slot < Constants.KEY_SIZE;
    }
 
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new VaultScreenHandler(syncId, playerInventory, this);
    }
 
    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
 
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }
 
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        return nbt;
    }
}