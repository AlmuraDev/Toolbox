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
package com.almuradev.toolbox.forge.inject.event.registrar;

import com.almuradev.toolbox.event.Witness;
import com.almuradev.toolbox.event.WitnessRegistrar;
import com.almuradev.toolbox.forge.inject.event.Bus;
import com.almuradev.toolbox.forge.inject.event.BusType;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.minecraftforge.fml.common.eventhandler.EventBus;

@Singleton
public final class TerrainEventBusWitnessRegistrar implements WitnessRegistrar {
    private final EventBus bus;

    @Inject
    public TerrainEventBusWitnessRegistrar(@Bus(type = BusType.TERRAIN) final EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void register(final Witness witness) {
        this.bus.register(witness);
    }
}
