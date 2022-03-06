package uk.co.paulcodes.thoushallnotspeak.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import uk.co.paulcodes.thoushallnotspeak.ThouShallNotSpeak;

import java.util.ArrayList;
import java.util.List;

public class TSNSCommand implements CommandExecutor, TabCompleter {

    ThouShallNotSpeak plugin;

    public TSNSCommand(ThouShallNotSpeak plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("thoushallnotspeak")) {
            if(args.length == 1) {
                if(sender.hasPermission("thoushallnotspeak.reload")) {
                    if(args[0].equalsIgnoreCase("reload")) {
                        this.plugin.reloadConfigs();
                        sender.sendMessage(this.plugin.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&eSuccessfully reloaded config!"));
                    }
                }else{
                    sender.sendMessage(this.plugin.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission for this command!"));
                }
            }else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("delaytime")) {
                    if(sender.hasPermission("thoushallnotspeak.reload")) {
                        int delayTime = Integer.parseInt(args[1]);
                        this.plugin.setDelayTime(delayTime);
                        sender.sendMessage(this.plugin.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&eDelay time set to &6" + args[1] + " &eseconds."));
                    }else{
                        sender.sendMessage(this.plugin.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission for this command!"));
                    }
                }
            }else{
                sender.sendMessage(this.plugin.getPrefix() + ChatColor.RED + ChatColor.BOLD + "Incorrect arguments, /thoushallnotspeak <reload/delaytime> [delayTime]");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> responses = new ArrayList<>();
        if(cmd.getName().equalsIgnoreCase("thoushallnotspeak")) {
            responses.add("reload");
            responses.add("delaytime");
        }
        return responses;
    }
}
