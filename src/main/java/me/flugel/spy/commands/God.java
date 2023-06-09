package me.flugel.spy.commands;

import me.flugel.spy.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class God implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String lb, @NotNull String[] args) {

        if(!(sender instanceof Player))
            return false;

        Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("spy")){
            if(p.hasPermission("*")){
                if(Main.spy.contains(p)){
                    Main.spy.remove(p);
                    p.setGameMode(GameMode.CREATIVE);
                    Bukkit.getOnlinePlayers().forEach(player -> player.showPlayer(Main.getInstance(), p));
                    p.sendMessage("§cSpy: §eVocê saiu do modo Spy.");
                }else{
                    Main.spy.add(p);
                    p.setGameMode(GameMode.SPECTATOR);
                    Bukkit.getOnlinePlayers().forEach(player -> player.hidePlayer(Main.getInstance(), p));
                    p.sendMessage("§cSpy: §eVocê entrou em modo Spy.");
                }
            }
        }
        if(cmd.getName().equalsIgnoreCase("inv")){
            if(p.hasPermission("*")){
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        p.openInventory(target.getInventory());
                    }else p.sendMessage("§cSpy: §ePlayer não existe");
                }
            }
        }

        if(cmd.getName().equalsIgnoreCase("einv")){
            if(p.hasPermission("*")){
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        p.openInventory(target.getEnderChest());
                    }else p.sendMessage("§cSpy: §ePlayer não existe");
                }
            }
        }

        if(cmd.getName().equalsIgnoreCase("ajuda")){
            if(args.length == 0){
                p.sendMessage("§eUse §f/ajuda <mensagem>, §elogo depois espere um staff responder");
                return false;
            }else if(args.length > 0){
                String mensagem = convertArgs(args);
                Main.getInstance().sendTextMensageAjuda("\n" + mensagem, p.getName());
                p.sendMessage("§aENVIADO COM SUCESSO! §eAgora espere um administrador entrar em contato com você");
                return true;
            }
        }

        if(cmd.getName().equalsIgnoreCase("reportar")){
            if(args.length == 0){
                p.sendMessage("§Use §f/reportar <nick> <motivos> §ee depois espera um staff examinar");
                return false;
            }
            if(args.length >= 1){
                String target = args[0];
                String mensage = convertArgs(args).replace(target, "");
                Main.getInstance().sendTextMensageReportes(mensage,p.getName(), target);
                p.sendMessage("§aENVIADO COM SUCESSO! §eObrigado por reportar, isso ajuda de mais.");
                return true;
            }
        }

        if(cmd.getName().equalsIgnoreCase("getitens")){
            if(p.hasPermission("*")){
                if(args.length == 1){
                    String target = args[0];
                    Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(),() ->{
                        String string = Main.getInstance().getUsers().getConfig().getString("Users." + target);

                        if(string == null){
                            p.sendMessage("§eEsse jogador n existe na db");
                            return;
                        }
                        try {
                            ItemStack[] base64 = getBase64(string);
                            p.getInventory().setContents(base64);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
        return false;
    }

    public String convertArgs(String[] args){
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg + " ");
        }
        return builder.toString();
    }

    public ItemStack[] getBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("ERRO ao retornar o inventario", e);
        }
    }
}
