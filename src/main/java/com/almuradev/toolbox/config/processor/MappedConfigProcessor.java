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

import java.util.Map;

/**
 * A mapped configuration node processor.
 *
 * @param <C> the context type
 * @param <T> the tag type
 */
public interface MappedConfigProcessor<C, T extends ConfigTag> extends AbstractTaggedConfigProcessor<C, T>, ConfigProcessor<C> {

    /**
     * Gets the configuration node to map from.
     *
     * @param config the original configuration node
     * @return the mapping configuration node
     */
    default ConfigurationNode config(final ConfigurationNode config) {
        return config;
    }

    @Override
    default void process(final ConfigurationNode config, final C context) {
        this.processRoot(config, context);

        for (final Map.Entry<Object, ? extends ConfigurationNode> entry : this.config(config).getChildrenMap().entrySet()) {
            final String id = String.valueOf(entry.getKey());
            final ConfigurationNode tagged = this.tag().in(entry.getValue());
            if (tagged.isVirtual()) {
                if (this.required()) {
                    this.missingRequired();
                }
            } else {
                this.processTagged(id, tagged, context);
            }
        }
    }

    /**
     * Process a tagged configuration node and context.
     *
     * @param id the map key
     * @param config the tagged configuration node
     * @param context the context
     */
    void processTagged(final String id, final ConfigurationNode config, final C context);

    @Override
    default void postProcess(final ConfigurationNode config, final C context) {
        this.postProcessRoot(config, context);

        for (final Map.Entry<Object, ? extends ConfigurationNode> entry : this.config(config).getChildrenMap().entrySet()) {
            final String id = String.valueOf(entry.getKey());
            final ConfigurationNode tagged = this.tag().in(entry.getValue());
            if (tagged.isVirtual()) {
                if (this.required()) {
                    this.missingRequired();
                }
            } else {
                this.postProcessTagged(id, tagged, context);
            }
        }
    }

    /**
     * Process a tagged configuration node and context.
     *
     * @param id the map key
     * @param config the tagged configuration node
     * @param context the context
     */
    default void postProcessTagged(final String id, final ConfigurationNode config, final C context) {
    }
}
