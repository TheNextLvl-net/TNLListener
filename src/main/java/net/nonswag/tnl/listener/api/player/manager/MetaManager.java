package net.nonswag.tnl.listener.api.player.manager;

public abstract class MetaManager extends Manager {

    public abstract void setFlag(int flag, boolean value);

    public abstract boolean getFlag(int flag);
}
