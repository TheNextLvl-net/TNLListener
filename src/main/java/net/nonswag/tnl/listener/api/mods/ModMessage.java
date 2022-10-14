package net.nonswag.tnl.listener.api.mods;

import com.google.gson.JsonElement;

import javax.annotation.Nonnull;

public record ModMessage(@Nonnull String channel, @Nonnull String key, @Nonnull JsonElement message) {
}
