package com.almuradev.toolbox.inject;

import net.kyori.membrane.facet.Facet;
import net.kyori.membrane.facet.FacetBinder;
import net.kyori.violet.ForwardingBinder;

public interface ToolboxBinder extends ForwardingBinder {

    /**
     * Creates a facet binder.
     *
     * @return a facet binder
     * @see Facet
     */
    default FacetBinder facet() {
        return new FacetBinder(this.binder());
    }
}
