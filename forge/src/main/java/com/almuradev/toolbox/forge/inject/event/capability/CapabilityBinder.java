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
package com.almuradev.toolbox.forge.inject.event.capability;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import net.minecraftforge.common.capabilities.Capability;

import java.util.concurrent.Callable;

public final class CapabilityBinder {

    private final Multibinder<CapabilityEntry> capabilities;

    public CapabilityBinder(final Binder binder) {
        this.capabilities = Multibinder.newSetBinder(binder, new TypeLiteral<CapabilityEntry>() {});
    }

    /**
     * Registers a new {@link Capability}
     * @param capabilityClass The capability class, usually a marker interface
     * @param capabilityStorage The capability storage, used to handle persistence
     * @param instanceFactory The instance factory, used to create capability instances when passed the capability class
     * @param <T> The capability type, usually a marker interface
     * @return This object, for chaining
     */
    public <T> CapabilityBinder register(final Class<? extends T> capabilityClass, final Capability.IStorage<? extends T> capabilityStorage,
        final Callable<? extends T> instanceFactory) {
        this.capabilities.addBinding().toInstance(new CapabilityEntry((Class) capabilityClass, (Capability.IStorage) capabilityStorage,
            (Callable) instanceFactory));
        return this;
    }
}
