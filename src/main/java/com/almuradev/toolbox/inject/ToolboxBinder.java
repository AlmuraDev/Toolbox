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
package com.almuradev.toolbox.inject;

import com.almuradev.toolbox.inject.command.CommandBinder;
import com.almuradev.toolbox.inject.network.packet.indexed.IndexedPacketBinder;
import com.almuradev.toolbox.inject.registry.RegistryBinder;
import net.kyori.membrane.facet.Facet;
import net.kyori.membrane.facet.FacetBinder;
import net.kyori.violet.ForwardingBinder;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Sponge;

public interface ToolboxBinder extends ForwardingBinder {
    /**
     * Creates a facet binder.
     *
     * @return a facet binder
     * @see Facet
     */
    default FacetBinder facet() {
        return FacetBinder.create(this.binder());
    }

    /**
     * Creates a command binder.
     *
     * @return a command binder
     */
    default CommandBinder command() {
        return CommandBinder.create(this.binder());
    }

    /**
     * Creates an indexed packet binder.
     *
     * @return an indexed packet binder
     */
    default IndexedPacketBinder indexedPacket() {
        return IndexedPacketBinder.create(this.binder());
    }

    /**
     * Creates a registry binder.
     *
     * @return a registry binder
     */
    default RegistryBinder registry() {
        return RegistryBinder.create(this.binder());
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
