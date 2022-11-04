package net.nonswag.tnl.listener.api.registrations;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.core.api.object.MutualGetter;
import net.nonswag.tnl.listener.api.mods.ModPlayer;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.manager.Manager;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RegistrationManager {

    private static final HashMap<Class<? extends Manager>, MutualGetter<TNLPlayer, ? extends Manager>> managers = new HashMap<>();
    private static final HashMap<Class<? extends ModPlayer>, MutualGetter<TNLPlayer, ? extends ModPlayer>> modHandlers = new HashMap<>();

    private final List<Class<? extends Manager>> managerClasses = new ArrayList<>();
    private final List<Class<? extends ModPlayer>> modHandlerClasses = new ArrayList<>();
    private final Plugin plugin;

    public RegistrationManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public <M extends Manager> void registerManager(Class<M> clazz, MutualGetter<TNLPlayer, M> manager) {
        if (!managerClasses.contains(clazz)) managerClasses.add(clazz);
        managers.put(clazz, manager);
    }

    public <M extends ModPlayer> void registerModHandler(Class<M> clazz, MutualGetter<TNLPlayer, M> modHandler) {
        if (!modHandlerClasses.contains(clazz)) modHandlerClasses.add(clazz);
        modHandlers.put(clazz, modHandler);
    }

    public void unregisterManager(Class<? extends Manager> clazz) {
        managers.remove(clazz);
        managerClasses.remove(clazz);
    }

    public void unregisterModHandler(Class<? extends ModPlayer> clazz) {
        modHandlers.remove(clazz);
        modHandlerClasses.remove(clazz);
    }

    public void unregisterAll() {
        modHandlerClasses.forEach(modHandlers::remove);
        managerClasses.forEach(managers::remove);
        modHandlerClasses.clear();
        managerClasses.clear();
    }

    public List<Class<? extends Manager>> getManagerClasses() {
        return ImmutableList.copyOf(managerClasses);
    }

    public List<Class<? extends ModPlayer>> getModHandlers() {
        return ImmutableList.copyOf(modHandlerClasses);
    }

    @Nullable
    public static MutualGetter<TNLPlayer, ? extends Manager> getManager(Class<? extends Manager> clazz) {
        return managers.get(clazz);
    }

    @Nullable
    public static MutualGetter<TNLPlayer, ? extends ModPlayer> getModHandler(Class<? extends ModPlayer> clazz) {
        return modHandlers.get(clazz);
    }
}
