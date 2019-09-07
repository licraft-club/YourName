package com.licrafter.mc.yourname.cmds;

import com.licrafter.mc.yourname.YourNamePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static com.licrafter.mc.yourname.utils.LangManager.*;

/**
 * Created by shell on 2019/9/6.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public class PlayerCmds implements CommandExecutor {

    private YourNamePlugin plugin;

    public PlayerCmds(YourNamePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            //yn
            sender.sendMessage(plugin.getLangManager().getColorStringV2(CMD_HELP, false));
            sender.sendMessage(plugin.getLangManager().getColorStringV2(CMD_SET_SELF, false));
            if (sender.hasPermission("yourname.other")) {
                sender.sendMessage(plugin.getLangManager().getColorStringV2(CMD_SET_OTHER, false));
            }
            sender.sendMessage(plugin.getLangManager().getColorStringV2(CMD_QUERY_ORIGIN, false));
            sender.sendMessage(plugin.getLangManager().getColorStringV2(CMD_QUERY_CUSTOM, false));
            sender.sendMessage(plugin.getLangManager().getColorStringV2(CMD_DELETE_SELF, false));
            if (sender.hasPermission("yourname.delete.other")) {
                sender.sendMessage(plugin.getLangManager().getColorStringV2(CMD_DELETE_OTHER, false));
            }
        } else if (args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            //yn reload
            plugin.reload();
            sender.sendMessage("reload success");
        } else if (args[0].equalsIgnoreCase("delete")) {
            return new DeleteCmd().onCommand(plugin, sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("query")) {
            return new QueryCmd().onCommand(plugin, sender, command, label, args);
        } else {
            return new SetCmd().onCommand(plugin, sender, command, label, args);
        }
        return true;
    }
}
