package com.almuradev.toolbox.forge.inject.network;

import com.almuradev.toolbox.forge.inject.ModInjectionPoint;

import javax.inject.Inject;
import javax.inject.Provider;

final class ChannelProvider implements Provider<Channel> {
  private final Provider<ModInjectionPoint> point;
  private final Channels channels;

  @Inject
  private ChannelProvider(final Provider<ModInjectionPoint> point, final Channels channels) {
    this.point = point;
    this.channels = channels;
  }

  @Override
  public Channel get() {
    final ModInjectionPoint point = this.point.get();
    final Channel.Type type;
    if(point.isAnnotationPresent(Indexed.class)) {
      type = Channel.Type.INDEXED;
    } else {
      throw new IllegalStateException();
    }
    return this.channels.get(point.getAnnotation(ChannelId.class).value(), type);
  }
}
