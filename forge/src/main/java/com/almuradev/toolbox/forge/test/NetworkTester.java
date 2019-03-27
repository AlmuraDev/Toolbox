package com.almuradev.toolbox.forge.test;

import com.almuradev.toolbox.event.Witness;
import com.almuradev.toolbox.forge.inject.event.scope.LifecycleEventBusScope;
import com.almuradev.toolbox.forge.inject.network.Channel;
import com.almuradev.toolbox.forge.inject.network.ChannelId;
import com.almuradev.toolbox.forge.inject.network.Indexed;
import com.google.common.eventbus.Subscribe;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import javax.inject.Inject;

@LifecycleEventBusScope
public final class NetworkTester implements Witness {
    private final Channel channel;

    @Inject
    public NetworkTester(@ChannelId("toolbox:test") @Indexed Channel channel) {
        this.channel = channel;
    }

    @Subscribe
    public void onFMLPreInitilization(final FMLPreInitializationEvent event) {
        this.channel.<MyRequest, MyResponse>bind(MyRequest.class, entry -> entry.handler(MyRequestHandler.class, Side.SERVER));
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
