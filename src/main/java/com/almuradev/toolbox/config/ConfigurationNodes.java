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

import net.kyori.lunar.function.BooleanConsumer;
import ninja.leaping.configurate.ConfigurationNode;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;

/**
 * A collection of utilities for working with configuration nodes.
 */
public interface ConfigurationNodes {
    /**
     * Provide a consumer with the configuration node if it is not {@link ConfigurationNode#isVirtual() virtual}.
     *
     * @param node the node
     * @param consumer the consumer
     */
    static void whenReal(final ConfigurationNode node, final Consumer<ConfigurationNode> consumer) {
        if (!node.isVirtual()) {
            consumer.accept(node);
        }
    }
    /**
     * Provide a consumer with the configuration node if it is {@link ConfigurationNode#isVirtual() virtual}.
     *
     * @param node the node
     * @param consumer the consumer
     */
    static void whenNotReal(final ConfigurationNode node, final Consumer<ConfigurationNode> consumer) {
        if (node.isVirtual()) {
            consumer.accept(node);
        }
    }

    /**
     * Provide a consumer with the {@link ConfigurationNode#getBoolean() boolean value} of
     * a configuration node if it is not {@link ConfigurationNode#isVirtual() virtual}.
     *
     * @param node the node
     * @param consumer the consumer
     */
    static void whenRealBoolean(final ConfigurationNode node, final BooleanConsumer consumer) {
        if (!node.isVirtual()) {
            consumer.accept(node.getBoolean());
        }
    }

    /**
     * Provide a consumer with the {@link ConfigurationNode#getDouble() double value} of
     * a configuration node if it is not {@link ConfigurationNode#isVirtual() virtual}.
     *
     * @param node the node
     * @param consumer the consumer
     */
    static void whenRealDouble(final ConfigurationNode node, final DoubleConsumer consumer) {
        if (!node.isVirtual()) {
            consumer.accept(node.getDouble());
        }
    }

    /**
     * Provide a consumer with the {@link ConfigurationNode#getFloat() float value} of
     * a configuration node if it is not {@link ConfigurationNode#isVirtual() virtual}.
     *
     * @param node the node
     * @param consumer the consumer
     */
    static void whenRealFloat(final ConfigurationNode node, final DoubleConsumer consumer) {
        if (!node.isVirtual()) {
            consumer.accept(node.getFloat());
        }
    }

    /**
     * Provide a consumer with the {@link ConfigurationNode#getInt() int value} of
     * a configuration node if it is not {@link ConfigurationNode#isVirtual() virtual}.
     *
     * @param node the node
     * @param consumer the consumer
     */
    static void whenRealInt(final ConfigurationNode node, final IntConsumer consumer) {
        if (!node.isVirtual()) {
            consumer.accept(node.getInt());
        }
    }

    /**
     * Provide a consumer with the {@link ConfigurationNode#getString() string value} of
     * a configuration node if it is not {@link ConfigurationNode#isVirtual() virtual}.
     *
     * @param node the node
     * @param consumer the consumer
     */
    static void whenRealString(final ConfigurationNode node, final Consumer<String> consumer) {
        if (!node.isVirtual()) {
            consumer.accept(node.getString());
        }
    }
}
