package uk.co.paulcodes.thoushallnotspeak.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import uk.co.paulcodes.thoushallnotspeak.ThouShallNotSpeak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerListener implements Listener {

    ThouShallNotSpeak plugin;
    List<UUID> cantTalk = new ArrayList<>();
    HashMap<UUID, BukkitTask> schedulerId = new HashMap<>();

    public PlayerListener(ThouShallNotSpeak plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPermission("thoushallnotspeak.bypass")) {
            if(!this.cantTalk.contains(player.getUniqueId())) {
                this.cantTalk.add(player.getUniqueId());

                BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(this.plugin, () -> {
                    this.cantTalk.remove(player.getUniqueId());
                    schedulerId.remove(player.getUniqueId());
                }, this.plugin.getDelayTime());
                schedulerId.put(player.getUniqueId(), task);
            }
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.cantTalk.remove(player.getUniqueId());
        if(schedulerId.containsKey(player.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(schedulerId.get(player.getUniqueId()).getTaskId());
            schedulerId.remove(player.getUniqueId());
        }
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
