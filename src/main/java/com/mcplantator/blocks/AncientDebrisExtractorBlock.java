package com.mcplantator.blocks;

import com.mcplantator.blockentities.AncientDebrisExtractorBlockEntity;
import com.mcplantator.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

/**
 * Ancient Debris Extractor Block - processes nether blocks to extract ancient debris
 * Uses redstone as fuel, has 10% chance to find ancient debris
 */
public class AncientDebrisExtractorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public AncientDebrisExtractorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, Boolean.FALSE));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                  InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AncientDebrisExtractorBlockEntity) {
                NetworkHooks.openScreen((ServerPlayer) player, (AncientDebrisExtractorBlockEntity) blockEntity, pos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AncientDebrisExtractorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                    BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.ANCIENT_DEBRIS_EXTRACTOR.get(),
                AncientDebrisExtractorBlockEntity::serverTick);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AncientDebrisExtractorBlockEntity) {
                ((AncientDebrisExtractorBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public int getLightEmission(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos) {
        return state.getValue(LIT) ? 13 : 0;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            Direction facing = state.getValue(FACING);

            // Redstone particles at x=8, z=8, y=0 (center of block)
            if (random.nextDouble() < 0.3D) {
                // Center coordinates (x=8, y=0, z=8 in pixel space)
                double x = pos.getX() + 0.5D;  // Center X (8/16 = 0.5)
                double y = pos.getY() + 0.0D;  // Bottom Y (0/16 = 0.0)
                double z = pos.getZ() + 0.5D;  // Center Z (8/16 = 0.5)

                // Redstone dust particles (red color)
                DustParticleOptions dustOptions = new DustParticleOptions(
                    new Vector3f(1.0F, 0.0F, 0.0F), // RGB color (red)
                    1.0F
                );

                level.addParticle(dustOptions, x, y, z, 0.0D, 0.02D, 0.0D);
            }

            // Large smoke particles
            if (random.nextDouble() < 0.5D) {
                double localX = 3.0D / 16.0D;
                double localY = 10.0D / 16.0D;
                double localZ = 8.0D / 16.0D;

                double[] worldCoords = transformCoordinates(localX, localZ, facing);

                level.addParticle(ParticleTypes.LARGE_SMOKE,
                    pos.getX() + worldCoords[0],
                    pos.getY() + localY,
                    pos.getZ() + worldCoords[1],
                    0.0D, 0.07D, 0.0D);
            }
        }
    }

    /**
     * Transform local block coordinates to world coordinates based on block rotation
     * @param localX X coordinate in block space (0.0-1.0)
     * @param localZ Z coordinate in block space (0.0-1.0)
     * @param facing Block facing direction
     * @return [worldX, worldZ] transformed coordinates
     */
    private double[] transformCoordinates(double localX, double localZ, Direction facing) {
        double worldX, worldZ;

        switch (facing) {
            case NORTH:
                worldX = localX;
                worldZ = localZ;
                break;
            case SOUTH:
                worldX = 1.0D - localX;
                worldZ = 1.0D - localZ;
                break;
            case WEST:
                worldX = localZ;
                worldZ = 1.0D - localX;
                break;
            case EAST:
                worldX = 1.0D - localZ;
                worldZ = localX;
                break;
            default:
                worldX = localX;
                worldZ = localZ;
                break;
        }

        return new double[]{worldX, worldZ};
    }
}
