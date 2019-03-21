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
package com.almuradev.toolbox.sponge.inject;

import com.almuradev.toolbox.inject.ToolboxBinder;
import com.almuradev.toolbox.sponge.inject.command.CommandBinder;
import com.almuradev.toolbox.sponge.inject.network.packet.indexed.IndexedPacketBinder;
import com.almuradev.toolbox.sponge.inject.registry.RegistryBinder;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Sponge;

public interface PluginToolboxBinder extends ToolboxBinder {

    /**
     * Creates a command binder.
     *
     * @return a command binder
     */
    default CommandBinder command() {
        return new CommandBinder(this.binder());
    }

    /**
     * Creates an indexed packet binder.
     *
     * @return an indexed packet binder
     */
    default IndexedPacketBinder indexedPacket() {
        return new IndexedPacketBinder(this.binder());
    }

    /**
     * Creates a registry binder.
     *
     * @return a registry binder
     */
    default RegistryBinder registry() {
        return new RegistryBinder(this.binder());
    }

    /**
     * Run a runnable when on a specific platform type.
     *
     * @param type the platform type
     * @param runnable the runnable
     */
    default void on(final Platform.Type type, final Runnable runnable) {
        if (Sponge.getPlatform().getType() == type) {
            runnable.run();
        }
    }
}
