package com.mcplantator.container;

import com.mcplantator.blockentity.BotanicalWorkbenchBlockEntity;
import com.mcplantator.init.ModBlocks;
import com.mcplantator.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Container/Menu for the Botanical Workbench GUI.
 */
public class BotanicalWorkbenchMenu extends AbstractContainerMenu {
    private final BotanicalWorkbenchBlockEntity blockEntity;
    private final Level level;

    public BotanicalWorkbenchMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, playerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public BotanicalWorkbenchMenu(int containerId, Inventory playerInventory, BlockEntity entity) {
        super(ModMenuTypes.BOTANICAL_WORKBENCH.get(), containerId);
        checkContainerSize(playerInventory, 3);
        this.blockEntity = (BotanicalWorkbenchBlockEntity) entity;
        this.level = playerInventory.player.level();

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        IItemHandler itemHandler = this.blockEntity.getItemHandler();

        // Input slot 1 (top) - X: 56, Y: 20
        this.addSlot(new SlotItemHandler(itemHandler, 0, 56, 20));

        // Input slot 2 (bottom) - X: 56, Y: 56
        this.addSlot(new SlotItemHandler(itemHandler, 1, 56, 56));

        // Output slot (result) - X: 116, Y: 38
        this.addSlot(new SlotItemHandler(itemHandler, 2, 116, 38) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false; // Output slot, cannot place items
            }

            @Override
            public void onTake(net.minecraft.world.entity.player.Player player, ItemStack stack) {
                // Consume input items for each output item taken
                if (level != null && !level.isClientSide()) {
                    net.minecraftforge.items.IItemHandler handler = blockEntity.getItemHandler();
                    // Consume 1 from each input slot
                    handler.extractItem(0, 1, false);
                    handler.extractItem(1, 1, false);
                    // Check if new recipe is possible
                    blockEntity.checkRecipe();
                }
                super.onTake(player, stack);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();

            // Slot indices: 0-35 = player inventory, 36-37 = input slots, 38 = output slot
            final int PLAYER_INV_START = 0;
            final int PLAYER_INV_END = 36;
            final int INPUT_SLOTS_START = 36;
            final int INPUT_SLOTS_END = 38;
            final int OUTPUT_SLOT = 38;

            // If shift-clicking the output slot
            if (index == OUTPUT_SLOT) {
                // Calculate max craftable items based on available ingredients
                IItemHandler handler = blockEntity.getItemHandler();
                int ingredient1Count = handler.getStackInSlot(0).getCount();
                int ingredient2Count = handler.getStackInSlot(1).getCount();
                int maxCraftable = Math.min(ingredient1Count, ingredient2Count);

                if (maxCraftable <= 0 || !slot.hasItem()) {
                    return ItemStack.EMPTY;
                }

                // Create stack with all craftable items
                ItemStack resultToCraft = slotStack.copy();
                resultToCraft.setCount(maxCraftable);

                // Try to add all crafted items to player inventory at once
                ItemStack remainder = resultToCraft.copy();
                if (!this.moveItemStackTo(remainder, PLAYER_INV_START, PLAYER_INV_END, true)) {
                    return ItemStack.EMPTY;
                }

                // Calculate how many were actually moved (remainder might not be empty if inventory was full)
                int actualCrafted = maxCraftable - remainder.getCount();

                if (actualCrafted > 0) {
                    // Consume ingredients for all crafted items at once (server side only)
                    if (level != null && !level.isClientSide()) {
                        handler.extractItem(0, actualCrafted, false);
                        handler.extractItem(1, actualCrafted, false);
                        // Update recipe once at the end
                        blockEntity.checkRecipe();
                    }

                    // Clear the output slot since we handled everything manually
                    slot.set(ItemStack.EMPTY);
                }

                itemstack = resultToCraft.copy();
                itemstack.setCount(actualCrafted);
            }
            // From player inventory to input slots
            else if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
                if (!this.moveItemStackTo(slotStack, INPUT_SLOTS_START, INPUT_SLOTS_END, false)) {
                    return ItemStack.EMPTY;
                }
            }
            // From input slots to player inventory
            else if (index >= INPUT_SLOTS_START && index < INPUT_SLOTS_END) {
                if (!this.moveItemStackTo(slotStack, PLAYER_INV_START, PLAYER_INV_END, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.BOTANICAL_WORKBENCH.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }
}
