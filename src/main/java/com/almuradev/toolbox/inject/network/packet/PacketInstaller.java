/*
 * This file is part of Toolbox, licensed under the MIT License.
 *
 * Copyright (c) 2017 AlmuraDev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.almuradev.toolbox.inject.network.packet;

import com.almuradev.toolbox.inject.network.packet.indexed.ForIndexedPacketBinder;
import com.almuradev.toolbox.inject.network.packet.indexed.IndexedPacketEntryImpl;
import com.google.inject.Injector;
import net.kyori.membrane.facet.Enableable;
import org.spongepowered.api.Platform;
import org.spongepowered.api.network.ChannelBinding;
import org.spongepowered.api.network.Message;

import java.util.Set;

import javax.inject.Inject;

public class PacketInstaller implements Enableable {
    private final Platform platform;
    private final Injector injector;
    private final ChannelBinding.IndexedMessageChannel channel;
    private final Set<IndexedPacketEntryImpl<? extends Message>> entries;

    @Inject
    private PacketInstaller(final Platform platform, final Injector injector, @ForIndexedPacketBinder final ChannelBinding.IndexedMessageChannel channel, final Set<IndexedPacketEntryImpl<? extends Message>> entries) {
        this.platform = platform;
        this.injector = injector;
        this.channel = channel;
        this.entries = entries;
    }

    @Override
    public void enable() {
        for (final IndexedPacketEntryImpl<? extends Message> entry : this.entries) {
            entry.register(this.channel, this.platform.getType(), this.injector);
        }
    }

    @Override
    public void disable() {
    }
}