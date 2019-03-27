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
import com.almuradev.toolbox.forge.inject.network.Channel;
import com.almuradev.toolbox.forge.inject.network.ChannelId;
import com.almuradev.toolbox.forge.inject.network.Indexed;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.netty.buffer.ByteBuf;
import net.kyori.membrane.facet.internal.Facets;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import javax.inject.Inject;

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

    @Inject @ChannelId("toolbox:test") @Indexed Channel channel;

    @Mod.EventHandler
    public void onConstruction(final FMLConstructionEvent event) {
        final Injector injector = Guice.createInjector(new ModModule(), bootstrap.createModule());
        injector.injectMembers(this);
        this.channel.<MyRequest, MyResponse>bind(MyRequest.class, entry -> entry.handler(MyRequestHandler.class, Side.SERVER));
        this.facets = injector.getInstance(Facets.class);
        this.facets.enable();
    }

    private static class MyRequest implements IMessage {
        @Override
        public void fromBytes(final ByteBuf buf) {
        }

        @Override
        public void toBytes(final ByteBuf buf) {
        }
    }
    private static class MyResponse implements IMessage {
        @Override
        public void fromBytes(final ByteBuf buf) {
        }

        @Override
        public void toBytes(final ByteBuf buf) {
        }
    }
    private static class MyRequestHandler implements IMessageHandler<MyRequest, MyResponse> {
        @Override
        public MyResponse onMessage(final MyRequest request, final MessageContext context) {
            return new MyResponse();
        }
    }
}
