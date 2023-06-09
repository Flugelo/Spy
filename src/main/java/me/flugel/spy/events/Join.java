package me.flugel.spy.events;

import me.flugel.spy.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent e) {
        for (Player player : Main.spy) {
            e.getPlayer().hidePlayer(Main.getInstance(), player);
        }
        e.getPlayer().getInventory().clear();

    }
}
