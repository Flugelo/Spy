package me.flugel.spy.events;

import me.flugel.spy.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Locale;

public class ChatMensage implements Listener {
    @EventHandler
    public void digitar(AsyncPlayerChatEvent e){
        String message = e.getMessage().toLowerCase(Locale.ROOT);
        for (String s : Main.filtro) {
            if(message.contains(s)){

                Main.getInstance().senTextMensageReport("Target: " + e.getPlayer().getName() + "\n Mensagem: " + message);
                return;
            }
        }
    }
}
