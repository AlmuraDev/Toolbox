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
package com.almuradev.toolbox.forge.inject.event;

import com.almuradev.toolbox.event.Witness;
import com.almuradev.toolbox.event.Witnesses;
import com.almuradev.toolbox.inject.ToolboxBinder;
import net.kyori.membrane.facet.internal.Facets;
import net.kyori.violet.AbstractModule;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;

import java.util.stream.Stream;

import javax.inject.Inject;

public final class WitnessModule extends AbstractModule implements ToolboxBinder {

  @Inject private Witnesses witnesses;
  @Inject private Facets facets;

  @Override
  protected void configure() {
    this.bind(EventBus.class).annotatedWith(new BusImpl(BusType.NORMAL)).toProvider(() -> MinecraftForge.EVENT_BUS);
    this.bind(EventBus.class).annotatedWith(new BusImpl(BusType.ORE)).toProvider(() -> MinecraftForge.ORE_GEN_BUS);
    this.bind(EventBus.class).annotatedWith(new BusImpl(BusType.TERRAIN)).toProvider(() -> MinecraftForge.TERRAIN_GEN_BUS);

    this.facet();
    this.requestInjection(this);
  }

  @Inject
  private void earlyConfigure() {
    this.register(this.facets.of(Witness.class, Witness.predicate()));
  }

  private void register(final Stream<? extends Witness> stream) {
    stream.forEach(witness -> {
      if (witness instanceof Witness.Impl) {
        ((Witness.Impl) witness).subscribed();
      }

      this.witnesses.register(witness);
    });
  }
}
