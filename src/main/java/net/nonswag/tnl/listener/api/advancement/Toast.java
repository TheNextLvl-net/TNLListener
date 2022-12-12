package net.nonswag.tnl.listener.api.advancement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@Getter
@Setter
@AllArgsConstructor
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Toast {
    private ItemStack icon;
    private String title;
    private Advancement.FrameType type;
}
