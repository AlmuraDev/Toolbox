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
package com.almuradev.toolbox.forge.inject.network.indexed;

import com.almuradev.toolbox.forge.inject.network.PacketBinder;
import com.almuradev.toolbox.forge.inject.network.PacketEntry;
import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

public final class IndexedPacketBinder implements PacketBinder {

    private final Multibinder<IndexedPacketEntry<? extends IMessage, ? extends IMessage>> binder;

    public IndexedPacketBinder(@Nonnull final Binder binder) {
        this.binder = Multibinder.newSetBinder(binder, new TypeLiteral<IndexedPacketEntry<? extends IMessage, ? extends IMessage>>() {});
    }

    @Override
    public <IN extends IMessage, OUT extends IMessage> PacketBinder bind(final Class<IN> inboundPacket,
        final Consumer<PacketEntry<IN, OUT>> consumer) {
        final IndexedPacketEntry<IN, OUT> entry = new IndexedPacketEntry<>(inboundPacket);
        consumer.accept(entry);
        this.binder.addBinding().toInstance(entry);
        return this;
    }
}
