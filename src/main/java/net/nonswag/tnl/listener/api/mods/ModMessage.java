package net.nonswag.tnl.listener.api.mods;

import com.google.gson.JsonElement;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record ModMessage(String channel, String key, JsonElement message) {
}
