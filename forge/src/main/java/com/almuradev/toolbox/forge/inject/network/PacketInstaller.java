package com.almuradev.toolbox.forge.inject.network;

import com.almuradev.toolbox.forge.inject.network.indexed.IndexedPacketEntry;
import com.google.inject.Injector;
import net.kyori.membrane.facet.Enableable;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Set;

import javax.inject.Inject;

public final class PacketInstaller implements Enableable {

    private final Side side;
    private final Injector injector;
    private final SimpleNetworkWrapper channel;
    private final Set<IndexedPacketEntry<?, ?>> entries;

    @Inject
    public PacketInstaller(final Side side, final Injector injector, final SimpleNetworkWrapper channel,
        final Set<IndexedPacketEntry<? extends IMessage, ? extends IMessage>> entries) {
        this.side = side;
        this.injector = injector;
        this.channel = channel;
        this.entries = entries;
    }

    @Override
    public void enable() {
        this.entries.forEach(entry -> entry.register(this.side, this.injector, this.channel));
    }
}
