package net.nonswag.tnl.listener.api.registrations;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import net.nonswag.core.api.object.MutualGetter;
import net.nonswag.tnl.listener.api.mods.ModPlayer;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.manager.Manager;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class RegistrationManager {

    @Nonnull
    private static final HashMap<Class<? extends Manager>, MutualGetter<TNLPlayer, ? extends Manager>> managers = new HashMap<>();
    @Nonnull
    private static final HashMap<Class<? extends ModPlayer>, MutualGetter<TNLPlayer, ? extends ModPlayer>> modHandlers = new HashMap<>();

    @Nonnull
    private final List<Class<? extends Manager>> managerClasses = new ArrayList<>();
    @Nonnull
    private final List<Class<? extends ModPlayer>> modHandlerClasses = new ArrayList<>();
    @Nonnull
    private final Plugin plugin;

    public RegistrationManager(@Nonnull Plugin plugin) {
        this.plugin = plugin;
    }

    public <M extends Manager> void registerManager(@Nonnull Class<M> clazz, @Nonnull MutualGetter<TNLPlayer, M> manager) {
        if (!managerClasses.contains(clazz)) managerClasses.add(clazz);
        managers.put(clazz, manager);
    }

    public <M extends ModPlayer> void registerModHandler(@Nonnull Class<M> clazz, @Nonnull MutualGetter<TNLPlayer, M> modHandler) {
        if (!modHandlerClasses.contains(clazz)) modHandlerClasses.add(clazz);
        modHandlers.put(clazz, modHandler);
    }

    public void unregisterManager(@Nonnull Class<? extends Manager> clazz) {
        managers.remove(clazz);
        managerClasses.remove(clazz);
    }

    public void unregisterModHandler(@Nonnull Class<? extends ModPlayer> clazz) {
        modHandlers.remove(clazz);
        modHandlerClasses.remove(clazz);
    }

    public void unregisterAll() {
        modHandlerClasses.forEach(modHandlers::remove);
        managerClasses.forEach(managers::remove);
        modHandlerClasses.clear();
        managerClasses.clear();
    }

    @Nonnull
    public List<Class<? extends Manager>> getManagerClasses() {
        return ImmutableList.copyOf(managerClasses);
    }

    @Nonnull
    public List<Class<? extends ModPlayer>> getModHandlers() {
        return ImmutableList.copyOf(modHandlerClasses);
    }

    @Nullable
    public static MutualGetter<TNLPlayer, ? extends Manager> getManager(@Nonnull Class<? extends Manager> clazz) {
        return managers.get(clazz);
    }

    @Nullable
    public static MutualGetter<TNLPlayer, ? extends ModPlayer> getModHandler(@Nonnull Class<? extends ModPlayer> clazz) {
        return modHandlers.get(clazz);
    }
}
