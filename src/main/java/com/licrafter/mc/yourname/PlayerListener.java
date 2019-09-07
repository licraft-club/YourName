package com.licrafter.mc.yourname;

import com.sun.istack.internal.Nullable;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by shell on 2019/9/6.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public class PlayerListener implements Listener {

    private YourNamePlugin plugin;

    public PlayerListener(YourNamePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (plugin.getConfigManager().useHover()) {
            formateChatEventWithHover(event);
        } else {
            formatChatEvent(event);
        }
    }

    public void formatChatEvent(AsyncPlayerChatEvent event) {
        String formatString = getFormatString(event);
        if (formatString != null) {
            event.setFormat(ChatColor.translateAlternateColorCodes('&', formatString));
        }
    }

    public void formateChatEventWithHover(AsyncPlayerChatEvent event) {
        String formatString = getFormatString(event);
        if (formatString != null) {
            event.setCancelled(true);
            TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', formatString));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&e真实ID: &a" + event.getPlayer().getName())).create()));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, event.getPlayer().getName()));

            for (Player player : event.getRecipients()) {
                player.spigot().sendMessage(message);
            }
        }
    }

    @Nullable
    private String getFormatString(AsyncPlayerChatEvent event) {
        String customName = plugin.getUserManager().getCustomName(event.getPlayer().getUniqueId());
        Player player = event.getPlayer();
        if (customName != null) {
            return String.format(event.getFormat().replace("<%1$s>", plugin.getConfigManager().getChatFormat()), player.getDisplayName().replace(player.getName(), customName), event.getMessage());
        } else {
            return null;
        }
    }
}
