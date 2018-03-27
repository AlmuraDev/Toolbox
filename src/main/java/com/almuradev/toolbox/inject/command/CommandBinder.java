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
package com.almuradev.toolbox.inject.command;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import org.spongepowered.api.command.CommandCallable;

import javax.inject.Provider;

public final class CommandBinder {
    private final Multibinder<CommandCallable> root;

    public CommandBinder(final Binder binder) {
        this.root = Multibinder.newSetBinder(binder, CommandCallable.class, BoundRootCommand.class);
    }

    public CommandBinder root(final CommandCallable callable) {
        this.root.addBinding().toInstance(callable);
        return this;
    }

    public CommandBinder root(final Class<? extends CommandCallable> callable) {
        this.root.addBinding().to(callable);
        return this;
    }

    public CommandBinder rootProvider(final Class<? extends Provider<? extends CommandCallable>> callable) {
        this.root.addBinding().toProvider(callable);
        return this;
    }

    public CommandBinder rootProvider(final Provider<? extends CommandCallable> callable) {
        this.root.addBinding().toProvider(callable);
        return this;
    }
}
