package me.flugel.spy.events;

import me.flugel.spy.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Sair implements Listener {
    @EventHandler
    public void aosair(PlayerQuitEvent e){
        try {

            if(!e.getPlayer().getInventory().isEmpty()){
                ItemStack[] contents = e.getPlayer().getInventory().getContents();
                String base64 = setBase64(contents);

                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () ->{
                    Main.getInstance().getUsers().getConfig().set("Users." + e.getPlayer().getName(), base64);
                    Main.getInstance().getUsers().saveConfig();
                });

                Bukkit.getConsoleSender().sendMessage("§eInventario salvo.");
            }
        }catch (Exception ex){
            Bukkit.getConsoleSender().sendMessage("§cERRO AO SALVAR O INVENTARIO!");
            ex.printStackTrace();

        }
    }

    public String setBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("ERRO ao salvar o inventario!", e);
        }
    }

}
