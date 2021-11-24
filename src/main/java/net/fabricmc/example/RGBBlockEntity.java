package net.fabricmc.example;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class RGBBlockEntity extends BlockEntity {
	private int counter = 0;

	public RGBBlockEntity(BlockPos pos, BlockState state) {
		super(MainInitializer.RGB_BLOCK_ENTITY, pos, state);
	}
	
	public void incrementCounter() {
		counter++;
	}
	
	@Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putInt("number", counter);
        return tag;
    }
	
	@Override
	public void readNbt(NbtCompound tag) {
	    super.readNbt(tag);
	    counter = tag.getInt("counter");
	}
}
