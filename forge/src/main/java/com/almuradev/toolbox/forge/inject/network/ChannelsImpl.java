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
import net.kyori.membrane.facet.Enableable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChannelsImpl implements Enableable, Channels {
  private final Map<String, ChannelImpl> channels = new HashMap<>();
  private final Injector injector;
  private final ChannelImpl.Factory factory;

  @Inject
  private ChannelsImpl(final Injector injector, final ChannelImpl.Factory factory) {
    this.injector = injector;
    this.factory = factory;
  }

  @Override
  public @NonNull Channel get(final @NonNull String name, final Channel.@NonNull Type type) {
    return this.channels.computeIfAbsent(name, $ -> this.factory.create(name, type));
  }

  @Override
  public void enable() {
    for(final ChannelImpl channel : this.channels.values()) {
      channel.enable(this.injector);
    }
  }

  @Override
  public void disable() {
    // TODO(kashike)
  }
}
