package com.licrafter.mc.yourname.cmds;

import com.licrafter.mc.yourname.YourNamePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by shell on 2019/9/6.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public interface PlayerCmdInterface {
    boolean onCommand(YourNamePlugin plugin, CommandSender sender, Command command, String label, String[] args);
}
