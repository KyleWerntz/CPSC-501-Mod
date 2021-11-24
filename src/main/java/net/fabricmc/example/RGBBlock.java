package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RGBBlock extends Block implements BlockEntityProvider {
	private static int maxColor = 2;
	public static final IntProperty COLOR = IntProperty.of("color", 0, maxColor);
	
	protected RGBBlock(Settings settings) {
		super(settings);
		setDefaultState(stateManager.getDefaultState().with(COLOR, 0));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(COLOR);
    }
	
	@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		BlockEntity be = world.getBlockEntity(pos);
    	if (be instanceof RGBBlockEntity rgbEntity) {
    		rgbEntity.incrementCounter();
    	}
        player.playSound(SoundEvents.BLOCK_STONE_PLACE, 1, 1);
        world.setBlockState(pos, state.with(COLOR, (state.get(COLOR)+1)%(maxColor+1)));
        return ActionResult.SUCCESS;
    }
	
	@Override
	public RGBBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new RGBBlockEntity(pos, state);
	}

}
