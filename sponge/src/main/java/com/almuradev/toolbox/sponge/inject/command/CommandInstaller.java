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

import net.kyori.membrane.facet.Enableable;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Set;

import javax.inject.Inject;

import static java.util.Objects.requireNonNull;

public final class CommandInstaller implements Enableable {
    private final PluginContainer container;
    private final CommandManager manager;
    private final Set<CommandEntry> root;

    @Inject
    private CommandInstaller(final PluginContainer container, final CommandManager manager, @BoundRootCommand final Set<CommandEntry> root) {
        this.container = container;
        this.manager = manager;
        this.root = root;
    }

    @Override
    public void enable() {
        this.registerRoot();
    }

    private void registerRoot() {
        this.root.forEach((entry) -> this.manager.register(this.container, entry.callable.get(), entry.aliases));
    }

    @Override
    public void disable() {
        // TODO(kashike): removal?
    }
}
