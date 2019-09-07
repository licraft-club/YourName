package com.licrafter.mc.yourname;

import com.licrafter.mc.yourname.cmds.PlayerCmds;
import com.licrafter.mc.yourname.utils.ConfigManager;
import com.licrafter.mc.yourname.utils.LangManager;
import com.licrafter.mc.yourname.utils.UserManager;
import com.licrafter.mc.yourname.utils.YmlMaker;
import com.sun.istack.internal.Nullable;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

import static com.licrafter.mc.yourname.utils.LangManager.*;

/**
 * Created by shell on 2019/9/6.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public class YourNamePlugin extends JavaPlugin {

    private YmlMaker userData;
    private YmlMaker langConfig;
    private ConfigManager configManager;
    private LangManager langManager;
    private UserManager userManager;
    @Nullable
    private Economy econ = null;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        PluginCommand pluginCommand = getCommand("yn");
        if (pluginCommand != null) {
            pluginCommand.setExecutor(new PlayerCmds(this));
        }

        langConfig = new YmlMaker(this, "lang.yml");
        langConfig.saveDefaultConfig();
        saveDefaultConfig();
        userData = new YmlMaker(this, "users.yml");
        configManager = new ConfigManager(this);
        userManager = new UserManager(this);
        langManager = new LangManager(this);

        if (configManager.useEco()) {
            setupEconomy();
        }
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "&a[&eYourName&a]&7Vault not found!!"));
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "&a[&eYourName&a]&7Vault setup failed!!"));
            return;
        }
        econ = rsp.getProvider();
    }

    public void setPlayerCustomName(CommandSender sender, UUID uuid, String originName, String customName) {
        //检测金钱
        boolean drawSuccess = false;
        if (configManager.useEco() && sender instanceof Player && econ != null) {
            if (econ.getBalance((Player) sender) < configManager.needMoney()) {
                sender.sendMessage(getLangManager().getColorString(BASIC__LACK_MONEY, configManager.needMoney() + ""));
                return;
            }
            drawSuccess = withDraw((Player) sender, configManager.needMoney());
            if (!drawSuccess) {
                sender.sendMessage(getLangManager().getColorString(BASIC_TRANS_FAILED));
                return;
            }
        }
        //检测禁用颜色
        String forbiddenColor = getConfigManager().isColorForbidden(customName);
        if (forbiddenColor != null) {
            sender.sendMessage(getLangManager().getColorString(BASIC_FORBIDDEN_COLOR, forbiddenColor.replaceAll("&", "")));
            return;
        }
        String stripName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', customName));
        //检测长度
        if (stripName.length() > configManager.getMaxLength()) {
            sender.sendMessage(getLangManager().getColorString(BASIC_NAME_TOOLONG));
            return;
        }
        //检测禁用词
        for (String world : getConfigManager().getForbiddenWord()) {
            if (stripName.contains(world)) {
                sender.sendMessage(getLangManager().getColorString(BASIC_FORBIDDEN_WORD));
                return;
            }
        }
        //检测是否有重名
        boolean isRepeat = getUserManager().isNameUsed(customName);
        if (isRepeat) {
            sender.sendMessage(getLangManager().getColorString(BASIC_NAME_TAKEUP));
            return;
        }
        getUserManager().setUserCustomName(uuid, originName, customName);

        String successMsg = getLangManager().getColorString(SET_SUCCESS, originName, customName);
        if (drawSuccess) {
            successMsg += getLangManager().getColorStringV2(SET_DRAW_SUCESS, false, getConfigManager().needMoney() + "");
        }
        sender.sendMessage(successMsg);
    }

    public boolean withDraw(Player player, double amount) {
        double balance = econ.getBalance(player);
        if (balance >= amount) {
            econ.withdrawPlayer(player, amount);
            return true;
        } else {
            return false;
        }
    }

    public YmlMaker getUserData() {
        return userData;
    }

    public YmlMaker getLangConfig() {
        return langConfig;
    }

    public Economy getEcon() {
        return econ;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public LangManager getLangManager() {
        return langManager;
    }

    public void reload() {
        reloadConfig();
        userData.reloadConfig();
        langConfig.reloadConfig();
        configManager.reload();
        userManager.reload();
        langManager.reload();
    }

    @Override
    public void onDisable() {
        saveConfig();
        userData.saveConfig();
        langConfig.saveConfig();
    }
}
