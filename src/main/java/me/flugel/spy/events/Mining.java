package me.flugel.spy.events;

import me.flugel.spy.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Mining implements Listener {
    @EventHandler
    public void mining(BlockBreakEvent e){
        if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)){
            Material type = e.getBlock().getType();
            if(type.equals(Material.DIAMOND_ORE) || type.equals(Material.ANCIENT_DEBRIS) || type.equals(Material.DEEPSLATE_DIAMOND_ORE)){
                Location location = e.getBlock().getLocation();
                int x = (int) location.getX();
                int y = (int) location.getY();
                int z = (int) location.getZ();
                Main.getInstance().sendTextXray("O Jogador " + e.getPlayer().getName() + " Minerou: " + type + "\n Coordenadas: X: " + x + " Y: " + y + " Z: " + z + "\n Horario: " + data());
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(onlinePlayer.hasPermission("*")){
                        onlinePlayer.sendMessage("§cSpy: §eJogador §6" + e.getPlayer().getName() + " §eminerou §6" + type);
                        onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1,1);
                    }
                }
            }
        }
    }
    public static String data() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

}
