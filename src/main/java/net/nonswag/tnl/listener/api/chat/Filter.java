package net.nonswag.tnl.listener.api.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.BitSet;

@Getter
@Setter
@AllArgsConstructor
public class Filter {
    private BitSet mask;
    private Filter.Type status;

    public enum Type {
        PASS_THROUGH, FULLY_FILTERED, PARTIALLY_FILTERED
    }
}
