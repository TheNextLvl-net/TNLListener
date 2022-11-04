package net.nonswag.tnl.listener.api.gui;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class GUIOverflowException extends RuntimeException {

    private final List<GUIItem> overflow;

    public GUIOverflowException(GUIItem... overflow) {
        this.overflow = Arrays.asList(overflow);
    }

    public GUIOverflowException(List<GUIItem> overflow) {
        this.overflow = overflow;
    }
}
