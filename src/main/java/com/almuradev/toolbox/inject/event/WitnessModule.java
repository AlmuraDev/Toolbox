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
package com.almuradev.toolbox.inject.event;

import net.kyori.membrane.facet.internal.Facets;
import net.kyori.violet.AbstractModule;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStateEvent;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.stream.Stream;

import javax.inject.Inject;

public final class WitnessModule extends AbstractModule {
    @Inject private PluginContainer plugin;
    @Inject private EventManager em;
    @Inject private Witnesses witnesses;
    @Inject private Facets facets;

    @Override
    protected void configure() {
        this.requestInjection(this);
    }

    @Inject
    private void earlyConfigure() {
        this.em.registerListeners(this.plugin, this);
        this.register(this.facets.of(Witness.class, Witness.predicate()));
    }

    @Listener
    public void lifecycleConfigure(final GameStateEvent event) {
        this.register(this.facets.of(Witness.Lifecycle.class, Witness.Lifecycle.predicate(event.getState())));
    }

    private void register(final Stream<? extends Witness> stream) {
        stream.forEach(witness -> {
            if (witness instanceof Witness.Impl) {
                ((Witness.Impl) witness).subscribed = true;
            }

            this.witnesses.register(witness);
        });
    }
}
