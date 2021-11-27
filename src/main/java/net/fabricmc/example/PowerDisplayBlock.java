package net.fabricmc.example;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PowerDisplayBlock extends Block {
	public static final IntProperty POWER = IntProperty.of("power", 0, 15);
	
	protected PowerDisplayBlock(Settings settings) {
		super(settings);
		setDefaultState(stateManager.getDefaultState().with(POWER, 0));
	}
	
	@Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POWER, ctx.getWorld().getReceivedRedstonePower(ctx.getBlockPos()));
    }
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(POWER);
    }
	
	@Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
        	world.getBlockTickScheduler().schedule(pos, this, 0);
        }
    }
	
	@Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, state.with(POWER, world.getReceivedRedstonePower(pos)));
    }

}
