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
package com.almuradev.toolbox.forge.test;

import com.almuradev.toolbox.forge.ModBootstrap;
import com.almuradev.toolbox.forge.inject.ModModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import net.kyori.membrane.facet.internal.Facets;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;

@Mod(
    modid = Constants.ID,
    name = Constants.NAME,
    acceptableRemoteVersions = "*"
)
public final class TestMod {

    @SidedProxy(
        clientSide = "com.almuradev.toolbox.forge.test.client.ClientBootstrap",
        serverSide = "com.almuradev.toolbox.forge.test.server.ServerBootstrap"
    )
    private static ModBootstrap bootstrap;

    private Facets facets;

    @Mod.EventHandler
    public void onConstruction(final FMLConstructionEvent event) {
        final Injector injector = Guice.createInjector(new ModModule(), bootstrap.createModule());
        this.facets = injector.getInstance(Facets.class);
        this.facets.enable();
    }
}
