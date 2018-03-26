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
package com.almuradev.toolbox.inject.event;

import net.kyori.membrane.facet.Activatable;
import net.kyori.membrane.facet.Facet;
import org.spongepowered.api.GameState;
import org.spongepowered.api.event.Listener;

import java.util.function.Predicate;

/**
 * Something that can listen to events.
 *
 * @see Listener
 */
public interface Witness extends Facet {
    /**
     * Tests if this witness is subscribable.
     *
     * @return {@code true} if this witness is subscribable
     */
    default boolean subscribable() {
        return true;
    }

    /**
     * Creates a predicate which may be used to check if a witness is subscribable.
     *
     * @param <W> the witness type
     * @return the predicate
     */
    static <W extends Witness> Predicate<W> predicate() {
        return witness -> Activatable.predicate().test(witness) && witness.subscribable();
    }

    /**
     * A witness whose activation depends on a game lifecycle state.
     */
    interface Lifecycle extends Witness {
        /**
         * Tests if this witness is subscribable.
         *
         * @param state the game state
         * @return {@code true} if this witness is subscribable
         */
        boolean lifecycleSubscribable(final GameState state);

        @Override
        default boolean subscribable() {
            return false; // default by false
        }

        /**
         * Creates a predicate which may be used to check if a lifecycle witness is subscribable.
         *
         * @param state the game state
         * @return the predicate
         */
        static Predicate<Lifecycle> predicate(final GameState state) {
            return Witness.<Lifecycle>predicate().and(witness -> witness.lifecycleSubscribable(state));
        }
    }

    /**
     * An abstract helper implementation of a witness.
     *
     * <p>It is not necessary to extend this class.</p>
     */
    abstract class Impl implements Witness {
        boolean subscribed;

        @Override
        public boolean subscribable() {
            return !this.subscribed;
        }
    }
}
