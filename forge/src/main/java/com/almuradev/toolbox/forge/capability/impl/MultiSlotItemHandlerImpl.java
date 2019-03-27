/*
 * This file is part of Toolbox, licensed under the MIT License (MIT).
 *
 * Copyright (c) AlmuraDev <https://www.almuramc.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.almuradev.toolbox.forge.capability.impl;

import static com.google.common.base.Preconditions.checkState;

import com.almuradev.toolbox.forge.capability.MultiSlotItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class MultiSlotItemHandlerImpl extends ItemStackHandler implements MultiSlotItemHandler {

    private final Map<Integer, Integer> customSlotLimits = new HashMap<>();

    @Override
    public final void resize(final int slotCount) {
        final NonNullList<ItemStack> stacks = this.stacks;
        this.setSize(slotCount);
        for (int i = 0; i < slotCount; i++) {
            if (i >= stacks.size()) {
                break;
            }

            final ItemStack stack = stacks.get(i);
            this.stacks.set(i, stack);
        }
    }

    @Override
    public final void setSlotLimit(final int slot, final int limit) {
        if (slot > this.getSlots()) {
            return;
        }

        checkState(limit > 0, "Slot limit must be greater than or equal to 0!");

        this.customSlotLimits.put(slot, limit);
        final ItemStack stack = this.getStackInSlot(slot);
        if (limit < stack.getCount()) {
            stack.setCount(limit);
            this.onContentsChanged(slot);
        }
    }

    @Override
    public final void setSize(final int size) {
        super.setSize(size);
    }

    @Override
    public final void setStackInSlot(final int slot, @Nonnull final ItemStack stack) {
        super.setStackInSlot(slot, stack);
    }

    @Override
    public final int getSlots() {
        return super.getSlots();
    }

    @Nonnull
    @Override
    public final ItemStack getStackInSlot(final int slot) {
        return super.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public final ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate) {
        return super.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public final ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }

    @Override
    public final int getSlotLimit(final int slot) {
        return this.customSlotLimits.getOrDefault(slot, super.getSlotLimit(slot));
    }

    @Override
    protected final int getStackLimit(final int slot, @Nonnull final ItemStack stack) {
        return super.getStackLimit(slot, stack);
    }

    @Override
    public final NBTTagCompound serializeNBT() {
        // TODO Custom slot sizes
        return super.serializeNBT();
    }

    @Override
    public final void deserializeNBT(final NBTTagCompound nbt) {
        // TODO Custom slot sizes
        super.deserializeNBT(nbt);
    }

    @Override
    protected final void validateSlotIndex(final int slot) {
        super.validateSlotIndex(slot);
    }
}
