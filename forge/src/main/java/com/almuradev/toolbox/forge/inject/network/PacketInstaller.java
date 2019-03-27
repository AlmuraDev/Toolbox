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
package com.almuradev.toolbox.forge.inject.network;

import com.almuradev.toolbox.forge.inject.network.indexed.IndexedPacketEntry;
import com.google.inject.Injector;
import net.kyori.membrane.facet.Enableable;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Set;

import javax.inject.Inject;

public final class PacketInstaller implements Enableable {

    private final Side side;
    private final Injector injector;
    private final SimpleNetworkWrapper channel;
    private final Set<IndexedPacketEntry<?, ?>> entries;

    @Inject
    public PacketInstaller(final Side side, final Injector injector, final SimpleNetworkWrapper channel,
        final Set<IndexedPacketEntry<? extends IMessage, ? extends IMessage>> entries) {
        this.side = side;
        this.injector = injector;
        this.channel = channel;
        this.entries = entries;
    }

    @Override
    public void enable() {
        this.entries.forEach(entry -> entry.register(this.side, this.injector, this.channel));
    }
}
