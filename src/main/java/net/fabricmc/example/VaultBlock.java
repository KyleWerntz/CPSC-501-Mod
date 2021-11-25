package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class VaultBlock extends BlockWithEntity {
	private DefaultedList<ItemStack> items;
	
    protected VaultBlock(Settings settings) {
        super(settings);
    }
    
    @Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(Properties.FACING);
	}
    
    @Override
    public VaultBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VaultBlockEntity(pos, state);
    }
 
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerFacing().getOpposite();
        return this.getDefaultState().with(Properties.FACING, direction);
    }
    
    @Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
    	return VoxelShapes.cuboid(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.625f, 0.9375f);
	}
    
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity player, ItemStack item) {
    	BlockEntity be = world.getBlockEntity(pos);
    	if (items != null && be instanceof VaultBlockEntity vbe) {
    		vbe.setItems(this.items);
        	super.onPlaced(world, pos, state, player, item);
    	}
    }
 
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    	
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
 
            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }
    
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    	BlockEntity be = world.getBlockEntity(pos);
    	if (be instanceof VaultBlockEntity vbe) {
    		this.items = vbe.getItems();
        	super.onBreak(world, pos, state, player);
    	}
    }
 
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }
 
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
}