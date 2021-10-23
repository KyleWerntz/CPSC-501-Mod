package net.fabricmc.example;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class PartyLampBlockEntity extends BlockEntity {

	public PartyLampBlockEntity(BlockPos pos, BlockState state) {
		super(ExampleMod.PARTY_LAMP_BLOCK_ENTITY, pos, state);
	}
	

}