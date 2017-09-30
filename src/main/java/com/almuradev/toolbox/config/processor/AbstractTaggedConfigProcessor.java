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
package com.almuradev.toolbox.config.processor;

import com.almuradev.toolbox.config.tag.ConfigTag;
import ninja.leaping.configurate.ConfigurationNode;

/**
 * An abstract tagged configuration node processor.
 *
 * @param <C> the context type
 * @param <T> the tag type
 */
@FunctionalInterface
public interface AbstractTaggedConfigProcessor<C, T extends ConfigTag> {

    /**
     * Gets the tag this processor processes.
     *
     * @return the tag
     */
    T tag();

    /**
     * Tests if this tag is required.
     *
     * @return {@code true} if this tag is required, {@code false} otherwise
     */
    default boolean required() {
        return false;
    }

    /**
     * Throws an exception when a required tag is missing.
     */
    default void missingRequired() {
        throw new IllegalArgumentException("Missing required tag '" + this.tag() + '\'');
    }

    /**
     * Process a configuration node and context.
     *
     * @param config the configuration node
     * @param context the context
     */
    default void processRoot(final ConfigurationNode config, final C context) {
        // NOOP
    }

    /**
     * Post-process a configuration node and context.
     *
     * @param config the configuration node
     * @param context the context
     */
    default void postProcessRoot(final ConfigurationNode config, final C context) {
        // NOOP
    }
}
