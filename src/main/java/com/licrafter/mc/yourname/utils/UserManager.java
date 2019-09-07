package com.licrafter.mc.yourname.utils;

import com.licrafter.mc.yourname.YourNamePlugin;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by shell on 2019/9/6.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public class UserManager {
    private YourNamePlugin plugin;
    private FileConfiguration userData;

    public UserManager(YourNamePlugin plugin) {
        this.plugin = plugin;
        this.userData = plugin.getUserData().getConfig();
    }

    @Nullable
    public String getCustomName(UUID uuid) {
        return userData.getString(uuid.toString() + ".custom", null);
    }

    @Nullable
    public String getUserByCustomName(String queryName) {
        for (String uuid : userData.getKeys(false)) {
            String customName = userData.getString(uuid + ".custom");
            if (customName == null) {
                continue;
            }
            String strippedName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', customName));
            if (strippedName.equalsIgnoreCase(queryName)) {
                return uuid;
            }
        }
        return null;
    }

    public void setUserCustomName(@NotNull UUID uuid, String originName, String customName) {
        userData.set(uuid.toString() + ".origin", originName);
        userData.set(uuid.toString() + ".custom", customName);
    }

    public void deleteUser(UUID uuid) {
        userData.set(uuid.toString(), null);
    }

    public boolean isNameUsed(String name) {
        String stripName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name));
        //检测别人设置的自定义昵称
        for (String uuid : userData.getKeys(false)) {
            String customName = userData.getString(uuid + ".custom", null);
            if (customName != null && ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', customName)).equalsIgnoreCase(stripName)) {
                return true;
            }
        }

        //检测在线玩家的真实名字
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            String stripedPlayerName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', player.getName()));
            if (stripedPlayerName.equalsIgnoreCase(stripName)) {
                return true;
            }
        }

        //检测离线玩家的真实名字
        for (OfflinePlayer player : plugin.getServer().getOfflinePlayers()) {
            String offlineName = player.getName();
            if (offlineName != null) {
                String stripedPlayerName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', player.getName()));
                if (stripedPlayerName.equalsIgnoreCase(stripName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void reload() {
        userData = plugin.getUserData().getConfig();
    }
}
