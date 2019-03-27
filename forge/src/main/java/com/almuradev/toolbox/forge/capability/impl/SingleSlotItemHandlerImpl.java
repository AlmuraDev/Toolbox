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

import com.almuradev.toolbox.forge.capability.SingleSlotItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class SingleSlotItemHandlerImpl implements SingleSlotItemHandler {

    private int slotLimit;
    private ItemStack stack = ItemStack.EMPTY;

    @Override
    public final int getSlots() {
        return 1;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(final int slot) {
        if (slot != 0) {
            return ItemStack.EMPTY;
        }

        return this.stack;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate) {
        // Bail if we're inserting into a bad slot index
        if (slot != 0) {
            return stack;
        }

        // Bail if we're passed an empty stack
        if (stack.isEmpty()) {
            return stack;
        }

        int limit = this.getSlotLimit(slot);

        if (!this.stack.isEmpty()) {
            // These stacks cannot stack (mismatch) so return passed stack
            if (!ItemHandlerHelper.canItemStacksStack(stack, this.stack)) {
                return stack;
            }

            limit -= this.stack.getCount();
        }

        // If limit is 0, we can't stack anymore, return passed stack
        if (limit <= 0) {
            return stack;
        }

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {

            // If stored stack is empty then set stored stack up to our limit or just passed stack (if less than limit)
            if (this.stack.isEmpty()) {
                this.stack = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack.copy();
            } else {
                // If stored stack is not empty, grow stack to limit
                this.stack.grow(reachedLimit ? limit : stack.getCount());
            }

            this.onContentsChanged(slot);
        }

        // Return a diff stack of the passed in stack or an empty one if we consumed the whole stack
        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
        // If amount 0 or bad slot index, return empty stack
        if (amount == 0 || slot != 0) {
            return ItemStack.EMPTY;
        }

        // If stored stack is empty, return empty stack
        if (this.stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        final int toExtract = Math.min(amount, this.getSlotLimit(slot));

        // If stored stack count is less than toExtract...
        if (this.stack.getCount() <= toExtract) {
            final ItemStack toReturn = this.stack.copy();

            // ...set stored stack to empty
            if (!simulate) {
                this.stack = ItemStack.EMPTY;

                this.onContentsChanged(slot);
            }
            return toReturn;
        } else {
            // Set stored stack size to the amount remaining if we're not simulating
            if (!simulate) {
                this.stack = ItemHandlerHelper.copyStackWithSize(this.stack, this.stack.getCount() - toExtract);

                this.onContentsChanged(slot);
            }

            // Return a stack with size set to the amount extracted
            return ItemHandlerHelper.copyStackWithSize(this.stack, toExtract);
        }
    }

    @Override
    public int getSlotLimit(final int slot) {
        if (slot != 0) {
            return 0;
        }

        return this.slotLimit;
    }

    @Override
    public void setSlotLimit(final int slot, final int limit) {
        if (slot != 0) {
            return;
        }

        checkState(limit > 0, "Slot limit must be greater than or equal to 0!");

        this.slotLimit = limit;
        if (this.slotLimit < this.stack.getCount()) {
            this.stack.setCount(this.slotLimit);
            this.onContentsChanged(0);
        }
    }

    protected void onContentsChanged(final int slot) {
    }
}
