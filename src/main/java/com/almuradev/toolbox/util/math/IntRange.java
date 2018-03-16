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
package com.almuradev.toolbox.util.math;

import com.almuradev.toolbox.config.ConfigurationNodeDeserializer;
import com.google.common.base.MoreObjects;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.annotation.concurrent.Immutable;

import static com.google.common.base.Preconditions.checkState;

@Immutable
public final class IntRange {

    public static final ConfigurationNodeDeserializer<IntRange> PARSER = config -> {
        if (config.isVirtual()) {
            return Optional.empty();
        }
        if (config.getValue() instanceof Map) {
            if (!config.getNode("min").isVirtual()) {
                return Optional.of(range(
                    config.getNode("min").getInt(),
                    config.getNode("max").getInt()
                ));
            } else if (!config.getNode("value").isVirtual()) {
                return Optional.of(fixed(config.getNode("value").getInt()));
            }
        } else if (config.getValue() instanceof Number) {
            return Optional.of(fixed(config.getInt()));
        }
        throw new IllegalArgumentException("Unknown type '" + config.getValue() + "'");
    };
    /**
     * The minimum value.
     */
    private final int min;
    /**
     * The maximum value.
     */
    private final int max;

    /**
     * Create a new int range with a minimum and maximum.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the range
     */
    public static IntRange range(final int min, final int max) {
        return new IntRange(min, max);
    }

    /**
     * Create a new int range with a fixed value.
     *
     * @param value the value
     * @return the range
     */
    public static IntRange fixed(final int value) {
        return new IntRange(value, value);
    }

    private IntRange(final int min, final int max) {
        checkState(min <= max, "min (%s) is greater than max (%s)", min, max);
        this.min = min;
        this.max = max;
    }

    /**
     * Gets the minimum value.
     *
     * <p>This may be the same as {@link #max()}.</p>
     *
     * @return the minimum value
     */
    public int min() {
        return this.min;
    }

    /**
     * Gets the maximum value.
     *
     * <p>This may be the same as {@link #min()}.</p>
     *
     * @return the maximum value
     */
    public int max() {
        return this.max;
    }

    /**
     * Tests if this range contains {@code value}.
     *
     * @param value the value
     * @return {@code true} if this range contains the value, {@code false} otherwise
     */
    public boolean contains(final int value) {
        return value >= this.min && value <= this.max;
    }

    /**
     * Gets a random value within this range.
     *
     * @param random the random
     * @return the value
     */
    public int random(final Random random) {
        return random.nextInt((this.max - this.min) + 1) + this.min;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IntRange)) {
            return false;
        }
        final IntRange that = (IntRange) other;
        return Integer.compare(this.min, that.min) == 0
            && Integer.compare(this.max, that.max) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.min, this.max);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("min", this.min)
            .add("max", this.max)
            .toString();
    }
}
