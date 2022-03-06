package uk.co.paulcodes.thoushallnotspeak.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.co.paulcodes.thoushallnotspeak.ThouShallNotSpeak;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerListener implements Listener {

    ThouShallNotSpeak plugin;
    List<UUID> cantTalk = new ArrayList<>();

    public PlayerListener(ThouShallNotSpeak plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPermission("thoushallnotspeak.bypass")) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(this.plugin, () -> {
                if(!this.cantTalk.contains(player.getUniqueId())) {
                    this.cantTalk.add(player.getUniqueId());
                }
            }, this.plugin.getDelayTime());
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.cantTalk.remove(player.getUniqueId());
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(this.cantTalk.contains(player.getUniqueId())) {
            player.sendMessage(this.plugin.getPrefix() + this.plugin.getDeniedMessage());
            event.setCancelled(true);
        }
    }

}
