package net.nonswag.tnl.listener.api.gui;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class GUIOverflowException extends RuntimeException {

    @Nonnull
    private final List<GUIItem> overflow;

    public GUIOverflowException(@Nonnull GUIItem... overflow) {
        this.overflow = Arrays.asList(overflow);
    }

    public GUIOverflowException(@Nonnull List<GUIItem> overflow) {
        this.overflow = overflow;
    }

    @Nonnull
    public List<GUIItem> getOverflow() {
        return overflow;
    }
}
