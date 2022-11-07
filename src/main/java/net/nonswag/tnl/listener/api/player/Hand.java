package net.nonswag.tnl.listener.api.player;

public enum Hand {
    MAIN_HAND, OFF_HAND;

    public boolean isMainHand() {
        return equals(MAIN_HAND);
    }

    public boolean isOffHand() {
        return equals(OFF_HAND);
    }

    public enum Side {
        LEFT, RIGHT;

        public boolean isRightSide() {
            return equals(RIGHT);
        }

        public boolean isLeftSide() {
            return equals(LEFT);
        }
    }
}
