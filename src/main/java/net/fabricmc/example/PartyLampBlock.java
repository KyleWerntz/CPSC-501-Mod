package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.client.color.block.BlockColors;

public class PartyLampBlock extends Block {

	public PartyLampBlock(Settings settings) {
		super(settings);
	}
	
	
	@Override
	public boolean emitsRedstonePower(BlockState state) {
		return false;
	}
	
	@Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
	
	
	
	
	
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
    	return VoxelShapes.fullCube();
    }
	

}
