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
package com.almuradev.toolbox.sponge.inject.registry;

import com.google.inject.Injector;
import net.kyori.membrane.facet.Enableable;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.GameRegistry;

import java.util.Set;

import javax.inject.Inject;

/**
 * A facet that installs registry entries into the registry.
 */
public final class RegistryInstaller implements Enableable {
    private final Injector injector;
    private final GameRegistry registry;
    private final Set<ModuleEntry<? extends CatalogType>> modules;
    private final Set<BuilderEntry<?>> builders;

    @Inject
    private RegistryInstaller(final Injector injector, final GameRegistry registry, final Set<ModuleEntry<? extends CatalogType>> modules, final Set<BuilderEntry<?>> builders) {
        this.injector = injector;
        this.registry = registry;
        this.modules = modules;
        this.builders = builders;
    }

    @Override
    public void enable() {
        this.modules.forEach(module -> module.install(this.injector, this.registry));
        this.builders.forEach(builder -> builder.install(this.injector, this.registry));
    }

    @Override
    public void disable() {
        // cannot remove from the registry
    }
}
