package com.licrafter.mc.yourname.cmds;

import com.licrafter.mc.yourname.YourNamePlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.licrafter.mc.yourname.utils.LangManager.BASIC_NOPERMS_EXC;
import static com.licrafter.mc.yourname.utils.LangManager.BASIC_ONLY_PLAYER_EXC;
import static com.licrafter.mc.yourname.utils.LangManager.DEL_SUCCESS;

/**
 * Created by shell on 2019/9/7.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public class DeleteCmd implements PlayerCmdInterface {
    @Override
    public boolean onCommand(YourNamePlugin plugin, CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            //yn delete
            if (sender instanceof Player) {
                plugin.getUserManager().deleteUser(((Player) sender).getUniqueId());
                sender.sendMessage(plugin.getLangManager().getColorString(DEL_SUCCESS, sender.getName()));
            } else {
                sender.sendMessage(plugin.getLangManager().getColorString(BASIC_ONLY_PLAYER_EXC));
            }
            return true;
        } else if (args.length > 1) {
            //yn delete [player]
            if (!sender.hasPermission("yourname.delete.other")) {
                sender.sendMessage(plugin.getLangManager().getColorString(BASIC_NOPERMS_EXC));
                return true;
            }
            String target = args[1];
            Player player = plugin.getServer().getPlayer(target);
            if (player != null) {
                plugin.getUserManager().deleteUser(player.getUniqueId());
                sender.sendMessage(plugin.getLangManager().getColorString(DEL_SUCCESS, player.getName()));

                return true;
            }
            OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(target);
            plugin.getUserManager().deleteUser(offlinePlayer.getUniqueId());
            sender.sendMessage(plugin.getLangManager().getColorString(DEL_SUCCESS, offlinePlayer.getName()));
            return true;
        }
        return false;
    }
}
