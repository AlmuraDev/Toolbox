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
package com.almuradev.toolbox.inject.network.packet.indexed;

import org.spongepowered.api.Platform;
import org.spongepowered.api.network.Message;
import org.spongepowered.api.network.MessageHandler;

/**
 * A binding entry.
 *
 * @param <M> the packet type
 */
public interface IndexedPacketEntry<M extends Message> {
    /**
     * Sets the packet handler.
     *
     * @param handler the handler
     * @param side the side
     */
    default void handler(final Class<? extends MessageHandler<M>> handler, final Platform.Type side) {
        this.handler(handler, side, false);
    }

    /**
     * Sets the packet handler.
     *
     * @param handler the handler
     * @param side the side
     * @param strictSide if the side is strict
     */
    void handler(final Class<? extends MessageHandler<M>> handler, final Platform.Type side, final boolean strictSide);
}
