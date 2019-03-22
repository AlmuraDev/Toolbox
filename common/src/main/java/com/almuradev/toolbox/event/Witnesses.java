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
package com.almuradev.toolbox.event;

import com.google.inject.Injector;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class Witnesses {
    private final Map<Class<? extends Annotation>, WitnessRegistrar> registars = new HashMap<>();
    @Inject private Injector injector;

    public void register(final Witness witness) {
        final boolean[] registered = new boolean[1];
        Arrays.stream(witness.getClass().getAnnotations())
            .filter(annotation -> annotation.annotationType().isAnnotationPresent(WitnessScope.class))
            .map(Annotation::annotationType)
            .map(this::registrar)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(registar -> {
                registered[0] = true;
                registar.register(witness);
            });
        if(!registered[0]) {
            throw new IllegalStateException("No scopes specified, nothing registered");
        }
    }

    private Optional<WitnessRegistrar> registrar(final Class<? extends Annotation> annotation) {
        final WitnessScope scope = annotation.getAnnotation(WitnessScope.class);
        if (scope == null) {
            return Optional.empty();
        }
        WitnessRegistrar registrar = this.registars.get(annotation);
        if (registrar != null) {
            return Optional.of(registrar);
        }
        registrar = this.injector.getInstance(scope.registrar());
        this.registars.put(annotation, registrar);
        return Optional.of(registrar);
    }
}
