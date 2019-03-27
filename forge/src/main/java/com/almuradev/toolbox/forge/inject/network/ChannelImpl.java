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
package com.almuradev.toolbox.forge.inject.network;

import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

public class ChannelImpl implements Channel {
  private final String name;
  private final Type type;
  private final Side side;
  private SimpleNetworkWrapper indexed;
  private int nextPacketId;
  private List<Consumer<Injector>> onEnable;

  @Inject
  ChannelImpl(final @Assisted String name, final @Assisted Type type, final Side side) {
    this.name = name;
    this.type = type;
    this.side = side;
  }

  @Override
  public @NonNull String name() {
    return this.name;
  }

  @Override
  public @NonNull Type type() {
    return this.type;
  }

  @Override
  public <I extends IMessage, O extends IMessage> @NonNull Channel bind(final @NonNull Class<I> inboundPacket, final @NonNull Consumer<PacketEntry<I, O>> consumer) {
    consumer.accept((handler, side, strictSide) -> {
      if(this.onEnable == null) {
        this.onEnable = new ArrayList<>();
      }
      this.onEnable.add(injector -> {
        if(this.side == side || (!strictSide && side == Side.CLIENT)) {
          this.indexed().registerMessage(injector.getInstance(handler), inboundPacket, this.nextPacketId++, side);
        } else {
          this.indexed().registerMessage(new DummyMessageHandler<>(), inboundPacket, this.nextPacketId++, side);
        }
      });
    });
    return this;
  }

  void enable(final Injector injector) {
    if(this.onEnable != null) {
      for(final Consumer<Injector> runnable : this.onEnable) {
        runnable.accept(injector);
      }
    }
    this.onEnable = null;
  }

  private @NonNull SimpleNetworkWrapper indexed() {
    if(this.indexed == null) {
      this.indexed = NetworkRegistry.INSTANCE.newSimpleChannel(this.name);
    }
    return this.indexed;
  }

  private static final class DummyMessageHandler<I extends IMessage, O extends IMessage> implements IMessageHandler<I, O> {
    @Override
    public O onMessage(final I request, final MessageContext context) {
      return null;
    }
  }

  public interface Factory {
    ChannelImpl create(final String name, final Type type);
  }
}
