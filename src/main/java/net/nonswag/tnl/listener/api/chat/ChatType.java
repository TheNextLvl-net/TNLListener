package net.nonswag.tnl.listener.api.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;

import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor
public class ChatType {
    private int chatType;
    private Component name;
    @Nullable
    Component targetName;
}
