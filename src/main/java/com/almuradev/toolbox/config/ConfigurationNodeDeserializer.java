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
package com.almuradev.toolbox.config;

import ninja.leaping.configurate.ConfigurationNode;

import java.util.Optional;

import javax.annotation.Nullable;

/**
 * A configuration node deserializer.
 *
 * @param <T> the type
 */
@FunctionalInterface
public interface ConfigurationNodeDeserializer<T> {

    /**
     * Deserialize a {@code T} from a configuration node.
     *
     * @param node the configuration node
     * @return an optional possibly containing {@code T}
     */
    Optional<T> deserialize(final ConfigurationNode node);

    /**
     * Deserialize a {@code T} from a configuration node.
     *
     * @param node the configuration node
     * @param defaultValue the default value
     * @return the value
     */
    @Nullable
    default T deserialize(final ConfigurationNode node, @Nullable final T defaultValue) {
        return this.deserialize(node).orElse(defaultValue);
    }
}
