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
package com.almuradev.toolbox.config.map;

import net.kyori.lunar.exception.Exceptions;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * An abstract implementation of a mapped configuration.
 *
 * <p>The default implementations of {@link #load()} and {@link #save()} will re-throw
 * any exceptions caught. You can override these methods to handle the exceptions your
 * own way, if you so choose.</p>
 *
 * @param <T> the configuration type
 */
public abstract class AbstractMappedConfiguration<T> implements MappedConfiguration<T> {

    protected final Class<T> type;
    protected final Path path;
    private final ConfigurationLoader<? extends ConfigurationNode> loader;
    private final ObjectMapper<T>.BoundInstance mapper;
    private ConfigurationNode root;
    private T config;

    protected AbstractMappedConfiguration(final Class<T> type, final Path path) {
        this.type = type;
        this.path = path;
        this.loader = this.createLoader();
        this.mapper = this.createMapper();
        this.root = SimpleConfigurationNode.root(this.loader.getDefaultOptions());
        this.construct();
    }

    /**
     * Creates the configuration loader.
     *
     * @return the configuration loader
     */
    protected abstract ConfigurationLoader<? extends ConfigurationNode> createLoader();

    /**
     * Creates the configuration mapper.
     *
     * @return the configuration mapper
     */
    protected ObjectMapper<T>.BoundInstance createMapper() {
        try {
            return ObjectMapper.forClass(this.type).bindToNew();
        } catch (final ObjectMappingException e) {
            throw Exceptions.rethrow(e);
        }
    }

    protected void construct() {
        final Path parent = this.path.getParent();
        if (Files.notExists(parent)) {
            try {
                Files.createDirectories(parent);
            } catch (final IOException e) {
                throw Exceptions.rethrow(e);
            }
            this.save();
        }
        this.load();
    }

    @Override
    public void load() {
        try {
            this.root = this.loader.load();
            this.config = this.mapper.populate(this.root);
        } catch (final IOException | ObjectMappingException e) {
            throw Exceptions.rethrow(e);
        }
    }

    @Override
    public void save() {
        try {
            this.mapper.serialize(this.root);
            this.loader.save(this.root);
        } catch (final IOException | ObjectMappingException e) {
            throw Exceptions.rethrow(e);
        }
    }

    @Override
    public T get() {
        return this.config;
    }
}
