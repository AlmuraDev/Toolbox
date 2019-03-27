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
package com.almuradev.toolbox.sponge.inject.event;

import com.almuradev.toolbox.event.Witness;
import org.spongepowered.api.GameState;

import java.util.function.Predicate;

/**
 * A witness whose activation depends on a game lifecycle state.
 */
public interface LifecycleWitness extends Witness {
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
  static Predicate<LifecycleWitness> predicate(final GameState state) {
    return Witness.<LifecycleWitness>predicate().and(witness -> witness.lifecycleSubscribable(state));
  }
}
