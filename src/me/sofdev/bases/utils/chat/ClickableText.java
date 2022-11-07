package me.sofdev.bases.utils.chat;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

/**
 * Created by SofDev w/Apreciada
 * 14/06/2022 - 02:52:27
 */
public class ClickableText {
    public static void clickableText(Player p, String msg, String cmd) {
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(msg));

        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + cmd));

        p.spigot().sendMessage(component);
    }

    public static void clickableSuggest(Player p, String msg, String cmd) {
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(msg));

        component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + cmd));

        p.spigot().sendMessage(component);
    }
}
