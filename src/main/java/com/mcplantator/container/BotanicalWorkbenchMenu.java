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
        });
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();

            // From custom slots to player inventory
            if (index < 36) {
                if (!this.moveItemStackTo(slotStack, 36, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            // From player inventory to custom slots
            else if (!this.moveItemStackTo(slotStack, 0, 36, false)) {
                return ItemStack.EMPTY;
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
