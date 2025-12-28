package com.mcplantator.blocks;

import com.mcplantator.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * Gunpowder Crop Block - grows in 8 stages like wheat.
 * When mature, drops gunpowder seeds and gunpowder.
 */
public class GunpowderCropBlock extends CropBlock {
    public static final int MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public GunpowderCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.GUNPOWDER_SEED.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (level.getRawBrightness(pos, 0) >= 9) {
            int age = this.getAge(state);
            if (age < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, level, pos);
                if (random.nextInt((int)(25.0F / growthSpeed) + 1) == 0) {
                    level.setBlock(pos, this.getStateForAge(age + 1), 2);
                }
            }
        }
    }

    protected static float getGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
        float speed = 1.0F;
        BlockPos belowPos = pos.below();

        for(int x = -1; x <= 1; ++x) {
            for(int z = -1; z <= 1; ++z) {
                float fertilityBonus = 0.0F;
                BlockState belowState = level.getBlockState(belowPos.offset(x, 0, z));
                if (belowState.canSustainPlant(level, belowPos.offset(x, 0, z), net.minecraft.core.Direction.UP, (net.minecraftforge.common.IPlantable)block)) {
                    fertilityBonus = 1.0F;
                    if (belowState.isFertile(level, belowPos.offset(x, 0, z))) {
                        fertilityBonus = 3.0F;
                    }
                }

                if (x != 0 || z != 0) {
                    fertilityBonus /= 4.0F;
                }

                speed += fertilityBonus;
            }
        }

        BlockPos northPos = pos.north();
        BlockPos southPos = pos.south();
        BlockPos westPos = pos.west();
        BlockPos eastPos = pos.east();
        boolean xAxis = level.getBlockState(westPos).is(block) || level.getBlockState(eastPos).is(block);
        boolean zAxis = level.getBlockState(northPos).is(block) || level.getBlockState(southPos).is(block);

        if (xAxis && zAxis) {
            speed /= 2.0F;
        } else {
            boolean diagonals = level.getBlockState(westPos.north()).is(block) ||
                               level.getBlockState(eastPos.north()).is(block) ||
                               level.getBlockState(eastPos.south()).is(block) ||
                               level.getBlockState(westPos.south()).is(block);
            if (diagonals) {
                speed /= 2.0F;
            }
        }

        return speed;
    }
}
