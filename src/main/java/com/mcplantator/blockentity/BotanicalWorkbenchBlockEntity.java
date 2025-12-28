package com.mcplantator.blockentity;

import com.mcplantator.container.BotanicalWorkbenchMenu;
import com.mcplantator.init.ModBlockEntities;
import com.mcplantator.init.ModRecipes;
import com.mcplantator.recipes.BotanicalRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Block Entity for the Botanical Workbench.
 * Handles inventory (2 input slots + 1 output slot) and recipe checking.
 */
public class BotanicalWorkbenchBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                checkRecipe();
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            // Slot 2 is output only
            return slot != 2;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            // When extracting from output slot, consume input items
            if (slot == 2 && !simulate) {
                itemHandler.extractItem(0, 1, false);
                itemHandler.extractItem(1, 1, false);
            }
            return super.extractItem(slot, amount, simulate);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public BotanicalWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOTANICAL_WORKBENCH.get(), pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.mcplantator.botanical_workbench");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new BotanicalWorkbenchMenu(containerId, playerInventory, this);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BotanicalWorkbenchBlockEntity blockEntity) {
        if (level.isClientSide()) {
            return;
        }
        // Recipe checking is done on inventory change
    }

    private void checkRecipe() {
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(2);
        inventory.setItem(0, itemHandler.getStackInSlot(0));
        inventory.setItem(1, itemHandler.getStackInSlot(1));

        Optional<BotanicalRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.BOTANICAL_TYPE, inventory, level);

        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(level.registryAccess());
            itemHandler.setStackInSlot(2, result.copy());
        } else {
            itemHandler.setStackInSlot(2, ItemStack.EMPTY);
        }
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }
}
