package com.licrafter.mc.yourname.cmds;

import com.licrafter.mc.yourname.YourNamePlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.licrafter.mc.yourname.utils.LangManager.BASIC_NOPERMS_EXC;
import static com.licrafter.mc.yourname.utils.LangManager.BASIC_ONLY_PLAYER_EXC;
import static com.licrafter.mc.yourname.utils.LangManager.SET_SUCCESS;

/**
 * Created by shell on 2019/9/6.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public class SetCmd implements PlayerCmdInterface {

    @Override
    public boolean onCommand(YourNamePlugin plugin, CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            //yn [昵称]
            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.getLangManager().getColorString(BASIC_ONLY_PLAYER_EXC));
                return true;
            }
            if (sender.hasPermission("yourname.self") || sender.isOp()) {
                String customName = args[0];
                Player player = (Player) sender;
                plugin.setPlayerCustomName(player, player.getUniqueId(), player.getName(), customName);
            } else {
                sender.sendMessage(plugin.getLangManager().getColorString(BASIC_NOPERMS_EXC));
            }
        } else if (args.length >= 2) {
            //yn [player] [昵称]
            Player player = plugin.getServer().getPlayer(args[0]);
            String customName = args[1];
            if (player != null) {
                plugin.setPlayerCustomName(sender, player.getUniqueId(), player.getName(), customName);
            } else {
                //可能会添加进去不存在的玩家
                OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[0]);
                plugin.setPlayerCustomName(sender, offlinePlayer.getUniqueId(), offlinePlayer.getName(), customName);
            }
            sender.sendMessage(plugin.getLangManager().getColorString(SET_SUCCESS, args[0]));
            return true;
        }
        return true;
    }
}
