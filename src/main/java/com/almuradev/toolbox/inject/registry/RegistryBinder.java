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
package com.almuradev.toolbox.inject.registry;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.registry.CatalogRegistryModule;
import org.spongepowered.api.registry.RegistryModule;

import java.util.function.Supplier;

/**
 * A registry binder.
 */
public final class RegistryBinder {
    private final Multibinder<ModuleEntry<? extends CatalogType>> modules;
    private final Multibinder<BuilderEntry<?>> builders;

    public static RegistryBinder create(final Binder binder) {
        return new RegistryBinder(binder);
    }

    private RegistryBinder(final Binder binder) {
        this.modules = Multibinder.newSetBinder(binder, new TypeLiteral<ModuleEntry<? extends CatalogType>>() {});
        this.builders = Multibinder.newSetBinder(binder, new TypeLiteral<BuilderEntry<?>>() {});
    }

    /**
     * Create a binding entry for the specified module type and class.
     *
     * <p>Bound modules will be installed into {@link GameRegistry}.</p>
     *
     * @param type the type class
     * @param module the module class
     * @param <C> the type
     * @return this binder
     */
    public <C extends CatalogType> RegistryBinder module(final Class<C> type, final Class<? extends CatalogRegistryModule<C>> module) {
        this.modules.addBinding().toInstance(new ModuleEntry.CatalogProvider<>(type, module));
        return this;
    }

    /**
     * Create a binding entry for the specified dummy-enabled module type and instance.
     *
     * <p>Bound modules will be installed into {@link GameRegistry}.</p>
     *
     * @param type the type class
     * @param module the module instance
     * @param <C> the type
     * @return this binder
     */
    public <C extends CatalogType> RegistryBinder module(final Class<C> type, final CatalogRegistryModule<C> module) {
        this.modules.addBinding().toInstance(new ModuleEntry.DummyEnabledCatalogInstance<>(type, module));
        return this;
    }

    /**
     * Create a binding entry for the specified module class.
     *
     * <p>Bound modules will be installed into {@link GameRegistry}.</p>
     *
     * @param module the module class
     * @return this binder
     */
    public RegistryBinder module(final Class<? extends RegistryModule> module) {
        this.modules.addBinding().toInstance(new ModuleEntry.Provider<>(module));
        return this;
    }

    /**
     * Create a binding entry for the specified module instance.
     *
     * <p>Bound modules will be installed into {@link GameRegistry}.</p>
     *
     * @param module the module instance
     * @return this binder
     */
    public RegistryBinder module(final RegistryModule module) {
        this.modules.addBinding().toInstance(new ModuleEntry.CatalogInstance<>(module));
        return this;
    }

    /**
     * Create a binding entry for the specified builder class and supplier.
     *
     * <p>Bound builders will be installed into {@link GameRegistry}.</p>
     *
     * @param type the type class
     * @param supplier the builder supplier
     * @param <C> the type
     * @return this binder
     */
    public <C> RegistryBinder builder(final Class<C> type, final Supplier<? extends C> supplier) {
        this.builders.addBinding().toInstance(new BuilderEntry<>(type, supplier));
        return this;
    }
}
