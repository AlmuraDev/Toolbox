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

import ninja.leaping.configurate.ConfigurationNode;

import java.util.List;

/**
 * An abstract array configuration node processor.
 *
 * @param <C> the context type
 */
@Deprecated
public interface AbstractArrayConfigProcessor<C> {

    default void processChildren(final ConfigurationNode config, final C context) {
        final List<? extends ConfigurationNode> children = config.getChildrenList();
        for (int i = 0, size = children.size(); i < size; i++) {
            this.processChild(children.get(i), i, context);
        }
    }

    void processChild(final ConfigurationNode config, final int index, final C context);

    default void postProcessChildren(final ConfigurationNode config, final C context) {
        final List<? extends ConfigurationNode> children = config.getChildrenList();
        for (int i = 0, size = children.size(); i < size; i++) {
            this.postProcessChild(children.get(i), i, context);
        }
    }

    default void postProcessChild(final ConfigurationNode config, final int index, final C context) {
    }

    /**
     * An abstract array configuration node processor that treats a single (non-array)
     * configuration node the same as a list entry.
     *
     * @param <C> the context type
     */
    interface EqualTreatment<C> extends AbstractArrayConfigProcessor<C> {

        @Override
        default void processChildren(final ConfigurationNode config, final C context) {
            if (config.getValue() instanceof List<?>) {
                final List<? extends ConfigurationNode> children = config.getChildrenList();
                for (int i = 0, size = children.size(); i < size; i++) {
                    this.processChild(children.get(i), i, context);
                }
            } else {
                this.processChild(config, 0, context);
            }
        }

        @Override
        default void postProcessChildren(final ConfigurationNode config, final C context) {
            if (config.getValue() instanceof List<?>) {
                final List<? extends ConfigurationNode> children = config.getChildrenList();
                for (int i = 0, size = children.size(); i < size; i++) {
                    this.postProcessChild(children.get(i), i, context);
                }
            } else {
                this.postProcessChild(config, 0, context);
            }
        }
    }
}
