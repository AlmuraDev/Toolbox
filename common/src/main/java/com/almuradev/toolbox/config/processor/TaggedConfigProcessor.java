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
package com.almuradev.toolbox.config.processor;

import com.almuradev.toolbox.config.tag.ConfigTag;
import ninja.leaping.configurate.ConfigurationNode;

/**
 * A tagged configuration node processor.
 *
 * @param <C> the context type
 * @param <T> the tag type
 */
@Deprecated
public interface TaggedConfigProcessor<C, T extends ConfigTag> extends AbstractTaggedConfigProcessor<C, T>, ConfigProcessor<C> {

    @Override
    default void process(final ConfigurationNode config, final C context) {
        this.processRoot(config, context);

        final ConfigurationNode tagged = this.tag().in(config);
        if (tagged.isVirtual()) {
            if (this.required()) {
                this.missingRequired();
            }
        } else {
            this.processTagged(tagged, context);
        }
    }

    /**
     * Process a tagged configuration node and context.
     *
     * @param config the tagged configuration node
     * @param context the context
     */
    void processTagged(final ConfigurationNode config, final C context);

    @Override
    default void postProcess(final ConfigurationNode config, final C context) {
        this.postProcessRoot(config, context);

        final ConfigurationNode tagged = this.tag().in(config);
        if (tagged.isVirtual()) {
            if (this.required()) {
                this.missingRequired();
            }
        } else {
            this.postProcessTagged(config, context);
        }
    }

    default void postProcessTagged(final ConfigurationNode config, final C context) {
    }
}
