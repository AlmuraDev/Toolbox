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
package com.almuradev.toolbox.forge.test.server;

import com.almuradev.toolbox.event.Witness;
import com.almuradev.toolbox.forge.inject.event.scope.NormalEventBusScope;
import com.almuradev.toolbox.forge.inject.event.scope.OreEventBusScope;
import com.almuradev.toolbox.forge.inject.event.scope.TerrainEventBusScope;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.inject.Singleton;

@Singleton
@NormalEventBusScope
@TerrainEventBusScope
@OreEventBusScope
public final class EventTester implements Witness {

    @SubscribeEvent
    public void onEvent(EntityJoinWorldEvent event) {
        System.err.println(event);
    }

    @SubscribeEvent
    public void onEvent(OreGenEvent event) {
        System.err.println(event);
    }

    @SubscribeEvent
    public void onEvent(PopulateChunkEvent.Populate event) {
        System.err.println(event);
    }
}
