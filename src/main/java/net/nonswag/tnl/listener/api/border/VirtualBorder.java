package net.nonswag.tnl.listener.api.border;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nonnull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VirtualBorder {

    public static final int MAX_SIZE = 29999984;

    @Nonnull
    private Center center;
    private long lerpTime;
    private int warningDistance, warningDelay;
    private double damageBuffer, damageAmount, oldSize, newSize;

    public VirtualBorder(@Nonnull Center center) {
        this.center = center;
    }

    public record Center(double x, double z) {
        @Nonnull
        public static final Center NULL = new Center(0, 0);
    }
}
