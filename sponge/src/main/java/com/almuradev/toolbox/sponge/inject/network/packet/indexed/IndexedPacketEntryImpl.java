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
package com.almuradev.toolbox.sponge.inject.network.packet.indexed;

import com.google.inject.Injector;
import org.spongepowered.api.Platform;
import org.spongepowered.api.network.ChannelBinding;
import org.spongepowered.api.network.Message;
import org.spongepowered.api.network.MessageHandler;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkState;

public class IndexedPacketEntryImpl<M extends Message> implements IndexedPacketEntry<M> {
    // State-of-the-art proprietary sequential numbering system
    private static int nextChannel;
    private final int channel = nextChannel++;
    private final Class<M> packet;
    @Nullable private Platform.Type side;
    private boolean strictSide;
    @Nullable private Class<? extends MessageHandler<M>> handler;

    IndexedPacketEntryImpl(final Class<M> packet) {
        this.packet = packet;
    }

    public void register(final ChannelBinding.IndexedMessageChannel channel, final Platform.Type side, final Injector injector) {
        checkState(this.side != null, "side not provided");
        checkState(this.handler != null, "handler not provided");
        channel.registerMessage(this.packet, this.channel);
        if (this.compatibleWith(side)) {
            channel.addHandler(this.packet, this.side, injector.getInstance(this.handler));
        }
    }

    private boolean compatibleWith(final Platform.Type side) {
        return this.side == side || (!this.strictSide && side == Platform.Type.CLIENT);
    }

    @Override
    public void handler(final Class<? extends MessageHandler<M>> handler, final Platform.Type side, final boolean strictSide) {
        this.handler = handler;
        this.side = side;
        this.strictSide = strictSide;
    }
}
