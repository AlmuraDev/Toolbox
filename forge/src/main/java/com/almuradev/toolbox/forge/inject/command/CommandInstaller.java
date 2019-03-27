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
package com.almuradev.toolbox.forge.inject.command;

import com.almuradev.toolbox.event.Witness;
import com.almuradev.toolbox.forge.inject.event.scope.LifecycleEventBusScope;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;
import net.minecraft.command.CommandHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.Set;

@LifecycleEventBusScope
public final class CommandInstaller implements Witness {

    private final Injector injector;
    private final Set<CommandEntry> commands;

    @Inject
    public CommandInstaller(final Injector injector, final Set<CommandEntry> commands) {
        this.injector = injector;
        this.commands = commands;
    }

    @Subscribe
    public void onFMLStartingServer(final FMLServerStartingEvent event) {
        this.commands.forEach(entry -> entry.install(this.injector, (CommandHandler) event.getServer().getCommandManager()));
    }
}
