package me.flugel.spy.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BlockCommands implements Listener {
    @EventHandler
    public void commands(PlayerCommandPreprocessEvent e){
        String[] message = e.getMessage().split(" ");
        if(message[0].equalsIgnoreCase("/pl") || message[0].equalsIgnoreCase("/plugins") | message[0].contains("bukkit")){
            e.getPlayer().sendMessage("§cVocê não pode executar esse comando...");
            e.setCancelled(true);
        }
    }
}
