package uk.co.paulcodes.thoushallnotspeak;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.paulcodes.thoushallnotspeak.commands.TSNSCommand;
import uk.co.paulcodes.thoushallnotspeak.listeners.PlayerListener;

public class ThouShallNotSpeak extends JavaPlugin {

    // DECLARE VARIABLES
    private int delayTime = 0;
    private String deniedMessage;
    private String prefix;
    private TSNSCommand tsnsCommand;

    @Override
    public void onEnable() {
        // LOAD CONFIG
        if(!this.getConfig().isInt("delayTime")) {
            this.getConfig().set("delayTime", 300);
            this.saveConfig();
            this.delayTime = 300;
        }else{
            this.delayTime = this.getConfig().getInt("delayTime");
        }

        if(!this.getConfig().isString("deniedMessage")) {
            this.getConfig().set("deniedMessage", "&cYou are not permitted to talk for {time}");
            this.saveConfig();
        }else{
            this.deniedMessage = this.getConfig().getString("deniedMessage");
        }

        if(!this.getConfig().isString("prefix")) {
            this.getConfig().set("prefix", "&c&lTSNS &8&l> &f");
            this.saveConfig();
        }else{
            this.prefix = this.getConfig().getString("prefix");
        }

        // REGISTER LISTENERS
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        // REGISTER COMMANDS
        this.tsnsCommand = new TSNSCommand(this);
        this.getServer().getPluginCommand("thoushallnotspeak").setExecutor(tsnsCommand);
        this.getServer().getPluginCommand("thoushallnotspeak").setTabCompleter(tsnsCommand);
    }

    @Override
    public void onDisable() {
        // NULLIFY COMMAND INSTANCE
        this.tsnsCommand = null;
    }

    public void reloadConfigs() {
        reloadConfig();
        this.delayTime = this.getConfig().getInt("delayTime");
        this.deniedMessage = this.getConfig().getString("deniedMessage");
        this.prefix = this.getConfig().getString("prefix");
    }

    public int getDelayTime() {
        return this.delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.getConfig().set("delayTime", delayTime);
        this.saveConfig();
        this.delayTime = delayTime;
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', this.prefix);
    }

    public String getDeniedMessage() {
        return ChatColor.translateAlternateColorCodes('&', this.deniedMessage);
    }
}
