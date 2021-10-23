package net.fabricmc.example;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public class VaultScreenHandler extends ScreenHandler {
	private class EmptyInventory implements ImplementedInventory {
		private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(Constants.VAULT_SIZE + Constants.KEY_SIZE, ItemStack.EMPTY);
		@Override
		public DefaultedList<ItemStack> getItems() {
			return inventory;
		}
	}
    private final Inventory inventory;
    private EmptyInventory emptyInventory = new EmptyInventory();
    private boolean previouslyUnlocked = true;


    public VaultScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(Constants.VAULT_SIZE + (2 * Constants.KEY_SIZE)));
    }

    public VaultScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ExampleMod.VAULT_SCREEN_HANDLER, syncId);
        checkSize(inventory, Constants.VAULT_SIZE + (2 * Constants.KEY_SIZE));
        this.inventory = inventory;
        int rows = (int)Math.sqrt(Constants.VAULT_SIZE);
        int cols = Constants.VAULT_SIZE / rows;
        int inventoryOffset = 0;
        int yOffset = 8;
        int xOffset = 12;
        boolean codeEqualsKey = true;
        
        if (inventory instanceof ImplementedInventory) {
            DefaultedList<ItemStack> codeChecker = ((ImplementedInventory)inventory).getItems();
	    	codeEqualsKey = ((ImplementedInventory) inventory).isUnlocked(codeChecker);
	    	previouslyUnlocked = codeEqualsKey;
        } else {
        	codeEqualsKey = false;
        }
        inventory.onOpen(playerInventory.player);
        System.out.println("open screen");
        
        for (int i = 0; i < Constants.KEY_SIZE; i++) {
        	this.addSlot(new Slot(inventory, i, xOffset, yOffset + i * Constants.GUI_ITEM_SIZE));
        }
        inventoryOffset += Constants.KEY_SIZE;
        
    	xOffset = 48;
    	for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
            	if (codeEqualsKey) {
                    this.addSlot(new Slot(inventory, j + i * rows + inventoryOffset, xOffset + j * Constants.GUI_ITEM_SIZE, yOffset + i * Constants.GUI_ITEM_SIZE));
            	} else {
            		this.addSlot(new Slot(emptyInventory, j + i * rows, xOffset + j * Constants.GUI_ITEM_SIZE, yOffset + i * Constants.GUI_ITEM_SIZE));
            	}
            }
        }
        inventoryOffset += Constants.VAULT_SIZE;

        xOffset = 156;
    	for (int i = 0; i < Constants.KEY_SIZE; i++) {
        	if (codeEqualsKey) {
        		this.addSlot(new Slot(inventory, i + inventoryOffset, xOffset, yOffset + i * Constants.GUI_ITEM_SIZE));
	    	} else {
	    		this.addSlot(new Slot(emptyInventory, i + inventoryOffset - Constants.KEY_SIZE, xOffset, yOffset + i * Constants.GUI_ITEM_SIZE));
	    	}
        }

        //The player inventory... rows always 3, cols always 9
        rows = 3;
        cols = 9;
        xOffset = 12;
        yOffset = 102;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * cols + cols, xOffset + j * Constants.GUI_ITEM_SIZE, yOffset + i * Constants.GUI_ITEM_SIZE));
            }
        }
        
        //The player Hotbar... always 9
        xOffset = 12;
        yOffset = 160;
        for (int i = 0; i < cols; ++i) {
            this.addSlot(new Slot(playerInventory, i, xOffset + i * Constants.GUI_ITEM_SIZE, yOffset));
        }
    }
    
    @Override
    public boolean canInsertIntoSlot(ItemStack i, Slot s) {
    	return canInsertIntoSlot(s);
    }
    
    @Override
    public void close(PlayerEntity player) {
    	if (!previouslyUnlocked && inventory instanceof ImplementedInventory) {
    		ImplementedInventory castedInventory = (ImplementedInventory) inventory;
            castedInventory.emptyIllegalContents(player, getStacks(), Constants.KEY_SIZE, (2 * Constants.KEY_SIZE) + Constants.VAULT_SIZE);
        } 
    	super.close(player);
    }
 
    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
 
    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
 
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
 
        return newStack;
    }
    
}