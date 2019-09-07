package com.licrafter.mc.yourname.cmds;

import com.licrafter.mc.yourname.YourNamePlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.licrafter.mc.yourname.utils.LangManager.*;

/**
 * Created by shell on 2019/9/6.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public class QueryCmd implements PlayerCmdInterface {
    @Override
    public boolean onCommand(YourNamePlugin plugin, CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 2) {
            String queryType = args[1];
            String queryContent = args[2];
            switch (queryType) {
                case "origin":
                    String uuidStr = plugin.getUserManager().getUserByCustomName(queryContent);
                    if (uuidStr == null) {
                        sender.sendMessage(plugin.getLangManager().getColorString(QUERY_NO_PLAYER));
                        return true;
                    }
                    UUID uuid = UUID.fromString(uuidStr);
                    Player player = plugin.getServer().getPlayer(uuid);
                    if (player != null) {
                        sender.sendMessage(plugin.getLangManager().getColorString(QUERY_SUCCESS, player.getName(), plugin.getUserManager().getCustomName(uuid)));
                        return true;
                    }
                    OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(uuid);
                    sender.sendMessage(plugin.getLangManager().getColorString(QUERY_SUCCESS, offlinePlayer.getName(), plugin.getUserManager().getCustomName(uuid)));
                    return true;
                case "custom":
                    Player queryPlayer = plugin.getServer().getPlayer(queryContent);
                    if (queryPlayer != null) {
                        String customName = plugin.getUserManager().getCustomName(queryPlayer.getUniqueId());
                        if (customName != null) {
                            sender.sendMessage(plugin.getLangManager().getColorString(QUERY_SUCCESS, queryPlayer.getName(), customName));
                            return true;
                        } else {
                            sender.sendMessage(plugin.getLangManager().getColorString(QUERY_NO_PLAYER));
                            return true;
                        }
                    }
                    OfflinePlayer queryOfflinePlayer = plugin.getServer().getOfflinePlayer(queryContent);
                    String customName = plugin.getUserManager().getCustomName(queryOfflinePlayer.getUniqueId());
                    if (customName != null) {
                        sender.sendMessage(plugin.getLangManager().getColorString(QUERY_SUCCESS, queryOfflinePlayer.getName(), customName));
                        return true;
                    } else {
                        sender.sendMessage(plugin.getLangManager().getColorString(QUERY_NO_PLAYER));
                        return true;
                    }
            }
        }
        sender.sendMessage(plugin.getLangManager().getColorString(CMD_QUERY_ORIGIN));
        sender.sendMessage(plugin.getLangManager().getColorString(CMD_QUERY_CUSTOM));
        return true;
    }
}
