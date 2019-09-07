package com.licrafter.mc.yourname.utils;

import com.licrafter.mc.yourname.YourNamePlugin;
import com.sun.istack.internal.Nullable;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 * Created by shell on 2019/9/6.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public class ConfigManager {

    private YourNamePlugin plugin;
    private FileConfiguration configuration;

    public ConfigManager(YourNamePlugin plugin) {
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
    }

    public int getMaxLength() {
        return configuration.getInt("maxlength", 5);
    }

    public String getChatFormat() {
        String format = configuration.getString("format", null);
        if (format == null || format.isEmpty()) {
            return "&f<&a%s&f>&7%s";
        }
        return format;
    }

    public boolean useHover() {
        return configuration.getBoolean("hover", false);
    }

    public boolean useTitlebar() {
        return configuration.getBoolean("useTitlebar", false);
    }

    public boolean useEco() {
        return configuration.getBoolean("economy.enable", false);
    }

    public int needMoney() {
        return configuration.getInt("economy.money", 0);
    }

    public List<String> getForbiddenWord() {
        return configuration.getStringList("forbiddenWords");
    }

    public List<String> getForbiddenColor() {
        return configuration.getStringList("forbiddenColor");
    }

    @Nullable
    public String isColorForbidden(String customName) {
        for (String color : getForbiddenColor()) {
            if (customName.contains(color)) {
                return color;
            }
        }
        return null;
    }

    public void reload() {
        configuration = plugin.getConfig();
    }
}
