package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.core.api.reflection.Reflection;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.packets.EntityStatusPacket;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.ServerOperator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class PermissionManager extends Manager implements ServerOperator {

    @Nonnull
    protected final PermissionAttachment attachment;

    protected PermissionManager() {
        this.attachment = getPlayer().bukkit().addAttachment(Bootstrap.getInstance());
    }

    @Nonnull
    public abstract Map<String, Boolean> getPermissions();

    @Nonnull
    public List<String> getAllowedPermissions() {
        List<String> permissions = new ArrayList<>();
        getPermissions().forEach((permission, allowed) -> {
            if (allowed) permissions.add(permission);
        });
        return permissions;
    }

    @Nonnull
    public List<String> getDeniedPermissions() {
        List<String> permissions = new ArrayList<>();
        getPermissions().forEach((permission, allowed) -> {
            if (!allowed) permissions.add(permission);
        });
        return permissions;
    }

    public void setPermissions(@Nonnull Map<String, Boolean> permissions) {
        Reflection.Field.set(attachment, "permissions", permissions);
        updatePermissions();
    }

    public void setPermission(@Nonnull String permission, boolean allowed) {
        attachment.setPermission(permission, allowed);
        updatePermissions();
    }

    public void addPermissions(@Nonnull String... permissions) {
        for (String permission : permissions) addPermission(permission);
    }

    public void addPermission(@Nonnull String permission) {
        setPermission(permission, true);
    }

    public void removePermissions(@Nonnull String... permissions) {
        for (String permission : permissions) removePermission(permission);
    }

    public void removePermission(@Nonnull String permission) {
        setPermission(permission, false);
    }

    public void unsetPermissions(@Nonnull String... permissions) {
        for (String permission : permissions) unsetPermission(permission);
    }

    public void unsetPermission(@Nonnull String permission) {
        attachment.unsetPermission(permission);
    }

    public boolean hasPermission(@Nonnull String permission) {
        return attachment.getPermissible().hasPermission(permission);
    }

    public boolean isPermissionSet(@Nonnull String permission) {
        return attachment.getPermissible().isPermissionSet(permission);
    }

    public void updatePermissions() {
        attachment.getPermissible().recalculatePermissions();
        getPlayer().pipeline().updateCommands();
        EntityStatusPacket.create(getPlayer().getEntityId(), EntityStatusPacket.Status.PERMISSION_LEVEL_4).send(getPlayer());
    }

    @Override
    public boolean isOp() {
        return attachment.getPermissible().isOp();
    }

    @Override
    public void setOp(boolean op) {
        attachment.getPermissible().setOp(op);
    }
}
