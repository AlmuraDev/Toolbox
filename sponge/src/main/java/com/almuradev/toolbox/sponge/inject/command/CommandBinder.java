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
package com.almuradev.toolbox.sponge.inject.command;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import org.spongepowered.api.command.CommandCallable;

import javax.inject.Provider;

public final class CommandBinder {
    private final Binder binder;
    private final Multibinder<CommandEntry> root;

    public CommandBinder(final Binder binder) {
        this.binder = binder;
        this.root = Multibinder.newSetBinder(binder, CommandEntry.class, BoundRootCommand.class);
    }

    public CommandBinder root(final CommandCallable callable, final String... aliases) {
        this.root.addBinding().toInstance(new CommandEntry(() -> callable, aliases));
        return this;
    }

    public CommandBinder root(final Class<? extends CommandCallable> callable, final String... aliases) {
        this.root.addBinding().toInstance(new CommandEntry(this.binder.getProvider(callable), aliases));
        return this;
    }

    public CommandBinder rootProvider(final Class<? extends Provider<? extends CommandCallable>> callable, final String... aliases) {
        final Provider<? extends Provider<? extends CommandCallable>> provider = this.binder.getProvider(callable);
        this.root.addBinding().toInstance(new CommandEntry(() -> provider.get().get(), aliases));
        return this;
    }

    public CommandBinder rootProvider(final Provider<? extends CommandCallable> callable, final String... aliases) {
        this.root.addBinding().toInstance(new CommandEntry(callable, aliases));
        return this;
    }
}
