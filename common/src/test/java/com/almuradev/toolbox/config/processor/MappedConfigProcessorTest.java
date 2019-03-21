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

import static org.junit.Assert.assertEquals;

import com.almuradev.toolbox.config.tag.ConfigTag;
import com.google.common.collect.ImmutableMap;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.SimpleConfigurationNode;
import org.junit.Test;

public class MappedConfigProcessorTest {

    @Test
    public void testTagged() {
        final ConfigurationNode config = SimpleConfigurationNode.root();
        config.getNode("sound").setValue(ImmutableMap.of(
                "cat", "meow"
        ));

        new MappedConfigProcessor<Object, ConfigTag>() {
            @Override
            public ConfigTag tag() {
                return ConfigTag.create("cat");
            }

            @Override
            public ConfigurationNode config(ConfigurationNode config) {
                return config.getNode("sound");
            }

            @Override
            public void processRoot(final ConfigurationNode config, final Object context) {
                assertEquals("meow", config.getNode("sound", "cat").getString());
            }

            @Override
            public void processTagged(final String id, final ConfigurationNode config, final Object context) {
                assertEquals("meow", config.getString());
            }
        }.process(config, null);
    }
}
