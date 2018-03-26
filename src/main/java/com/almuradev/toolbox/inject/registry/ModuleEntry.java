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

import com.google.inject.Injector;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.registry.CatalogRegistryModule;
import org.spongepowered.api.registry.RegistryModule;

/**
 * An abstract registry binder entry for a registry module
 *
 * @param <T> the type
 */
abstract class ModuleEntry<T> extends AbstractEntry {
    /**
     * A registry binder entry for a non-catalog registry module that will be
     * constructed through the injector.
     *
     * @param <T> the type
     */
    static class Provider<T> extends ModuleEntry<T> {
        private final Class<? extends RegistryModule> module;

        Provider(final Class<? extends RegistryModule> module) {
            this.module = module;
        }

        @Override
        void install(final Injector injector, final GameRegistry registry) {
            registry.registerModule(injector.getInstance(this.module));
        }
    }

    /**
     * A registry binder entry for a catalog registry module that will be
     * constructed through the injector.
     *
     * @param <C> the catalog type
     */
    static final class CatalogProvider<C extends CatalogType> extends ModuleEntry<C> {
        private final Class<C> type;
        private final Class<? extends CatalogRegistryModule<C>> module;

        CatalogProvider(final Class<C> type, final Class<? extends CatalogRegistryModule<C>> module) {
            this.type = type;
            this.module = module;
        }

        @Override
        void install(final Injector injector, final GameRegistry registry) {
            registry.registerModule(this.type, injector.getInstance(this.module));
        }
    }

    /**
     * A registry binder entry for an already constructed catalog registry module
     * that will be registered with the registry.
     *
     * @param <C> the catalog type
     */
    static final class CatalogInstance<C extends CatalogType> extends ModuleEntry<C> {
        private final RegistryModule module;

        CatalogInstance(final RegistryModule module) {
            this.module = module;
        }

        @Override
        void install(final Injector injector, final GameRegistry registry) {
            registry.registerModule(this.module);
        }
    }

    /**
     * A registry binder entry for an already constructed dummy-enabled catalog registry module
     * that will be registered with the registry.
     *
     * @param <C> the catalog type
     */
    static final class DummyEnabledCatalogInstance<C extends CatalogType> extends ModuleEntry<C> {
        private final Class<C> type;
        private final CatalogRegistryModule<C> module;

        DummyEnabledCatalogInstance(final Class<C> type, final CatalogRegistryModule<C> module) {
            this.type = type;
            this.module = module;
        }

        @Override
        void install(final Injector injector, final GameRegistry registry) {
            registry.registerModule(this.type, this.module);
        }
    }
}
