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

import static com.google.common.base.Preconditions.checkState;

import com.almuradev.toolbox.forge.inject.network.PacketEntry;
import com.google.inject.Injector;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public final class IndexedPacketEntry<IN extends IMessage, OUT extends IMessage> implements PacketEntry<IN, OUT> {

    private static int nextPacketId;
    private final int packetId = nextPacketId++;
    private final Class<IN> inboundPacket;
    @Nullable private Side side;
    private boolean strictSide;
    @Nullable private Class<? extends IMessageHandler<IN, OUT>> handler;

    IndexedPacketEntry(final Class<IN> inboundPacket) {
        this.inboundPacket = inboundPacket;
    }

    @Override
    public void handler(final Class<? extends IMessageHandler<IN, OUT>> handler, final Side side, final boolean strictSide) {
        this.handler = handler;
        this.side = side;
        this.strictSide = strictSide;
    }

    @Override
    public void register(final Side side, final Injector injector, final SimpleNetworkWrapper network) {
        checkState(this.side != null, "side not provided");
        checkState(this.handler != null, "handler not provided");

        // TODO Forge always forces a network handler...sometimes though we don't care. This logic may not be right though..
        if (this.side == side || (!this.strictSide && side == Side.CLIENT)) {
            network.registerMessage(this.handler, this.inboundPacket, packetId, side);
        } else {
            network.registerMessage(new DummyMessageHandler<IN, OUT>(), this.inboundPacket, packetId, side);
        }
    }

    // Oh Forge...
    private static final class DummyMessageHandler<IN extends IMessage, OUT extends IMessage> implements IMessageHandler<IN, OUT> {

        @Override
        public OUT onMessage(IN in, MessageContext messageContext) {
            return null;
        }
    }
}
