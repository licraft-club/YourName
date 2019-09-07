package com.licrafter.mc.yourname.utils;

import com.licrafter.mc.yourname.YourNamePlugin;
import com.sun.istack.internal.Nullable;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by shell on 2019/9/7.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public class LangManager {
    private YourNamePlugin plugin;
    private FileConfiguration configuration;

    public final static String BASIC_PREFIX = "basic.prefix";
    public final static String BASIC_ONLY_PLAYER_EXC = "basic.onlyplayer.exc";
    public final static String BASIC_NOPERMS_EXC = "basic.noperms.exc";
    public final static String BASIC__LACK_MONEY = "basic.lackmoney";
    public final static String BASIC_TRANS_FAILED = "basic.transfailed";
    public final static String BASIC_FORBIDDEN_WORD = "basic.forbidden.word";
    public final static String BASIC_FORBIDDEN_COLOR = "basic.forbidden.color";
    public final static String BASIC_NAME_TAKEUP = "basic.name.takeup";
    public final static String BASIC_NAME_TOOLONG = "basic.name.toolong";
    public final static String SET_SUCCESS = "set.success";
    public final static String SET_DRAW_SUCESS = "set.drawsuccess";
    public final static String QUERY_NO_PLAYER = "query.noplayer";
    public final static String QUERY_SUCCESS = "query.success";
    public final static String DEL_SUCCESS = "delete.success";
    public final static String CMD_QUERY_ORIGIN = "cmd.query.origin";
    public final static String CMD_QUERY_CUSTOM = "cmd.query.custom";
    public final static String CMD_HELP = "cmd.help";
    public final static String CMD_SET_SELF = "cmd.set.self";
    public final static String CMD_SET_OTHER = "cmd.set.other";
    public final static String CMD_DELETE_SELF = "cmd.delete.self";
    public final static String CMD_DELETE_OTHER = "cmd.delete.other";

    public LangManager(YourNamePlugin plugin) {
        this.plugin = plugin;
        this.configuration = plugin.getLangConfig().getConfig();
    }

    public String getString(String key) {
        return configuration.getString(key);
    }

    public String getColorString(String key, Object... args) {
        return getColorStringV2(key, true, args);
    }

    public String getColorStringV2(String key, boolean prefix, Object... args) {
        String message = configuration.getString(key);
        if (message == null) {
            message = "&e语言文件配置错误: " + key;
        }
        if (prefix) {
            message = getString(BASIC_PREFIX) + message;
        }
        if (args != null && args.length > 0) {
            message = String.format(message, args);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void reload() {
        this.configuration = plugin.getLangConfig().getConfig();
    }
}
