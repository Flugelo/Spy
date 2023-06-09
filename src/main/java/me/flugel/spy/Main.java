package me.flugel.spy;

import me.flugel.spy.commands.God;
import me.flugel.spy.commands.Users;
import me.flugel.spy.events.BlockCommands;
import me.flugel.spy.events.ChatMensage;
import me.flugel.spy.events.Mining;
import me.flugel.spy.events.Sair;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.security.auth.login.LoginException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main extends JavaPlugin {

    public static Main instance;

    public static JDA jda;

    public static String XRAY_CHANNEL = "863184377500663818";
    public static String MENSAGE_CHANNEL = "863184332469567518";
    public static String MENSAGE_REPORTE = "865806343918321675";
    public static String MENSAGE_AJUDA = "865806264751620137";
    public static ArrayList<String> filtro;
    public static HashSet<Player> spy = new HashSet<>();
    private Users users;


    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        users = new Users();
        filtro = new ArrayList<>();
        filtro.add("viado");
        filtro.add("gayzinho");
        filtro.add("pnc");
        filtro.add("lixo");
        filtro.add("seu merda");
        filtro.add("fdp");
        filtro.add("filho da puta");
        Bukkit.getPluginManager().registerEvents(new Mining(), this);
        Bukkit.getPluginManager().registerEvents(new ChatMensage(), this);
        Bukkit.getPluginManager().registerEvents(new BlockCommands(), this);
        Bukkit.getPluginManager().registerEvents(new Sair(), this);

        getCommand("spy").setExecutor(new God());
        getCommand("inv").setExecutor(new God());
        getCommand("einv").setExecutor(new God());
        getCommand("ajuda").setExecutor(new God());
        getCommand("reportar").setExecutor(new God());
        getCommand("getitens").setExecutor(new God());


        try {
            jda = JDABuilder.create("ODYzMjMyNTg2NDA2ODIxOTA4.YOj5_g.f5Uu6e-Z5ypsWQhZSb2JHTC_LXg", GatewayIntent.GUILD_MESSAGES).build();
            Bukkit.getConsoleSender().sendMessage("§2Discord: §eBot §cSPY §efoi conectado com sucesso!");
        } catch (LoginException e) {
            Bukkit.getConsoleSender().sendMessage("§cErro ao iniciar o bot");
            e.printStackTrace();
        }


    }

    public  void sendTextXray(String text){
        TextChannel textChannelById = jda.getTextChannelById(XRAY_CHANNEL);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.appendDescription("\n"+text+"\n");
        MessageChannel messageChannel = (MessageChannel) textChannelById;
        messageChannel.sendMessage(embedBuilder.build()).queue();
    }

    public void senTextMensageReport(String text){
        TextChannel textChannelById = jda.getTextChannelById(MENSAGE_CHANNEL);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.appendDescription("\n"+text+"\n");
        MessageChannel messageChannel = (MessageChannel) textChannelById;
        messageChannel.sendMessage(embedBuilder.build()).queue();
    }

    public void sendTextMensageAjuda(String text, String playerName){
        TextChannel textChannelById = jda.getTextChannelById(MENSAGE_AJUDA);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(playerName);
        embedBuilder.setColor(300);
        embedBuilder.setFooter(data());
        embedBuilder.appendDescription("\n"+text+"\n");
        MessageChannel messageChannel = (MessageChannel) textChannelById;
        messageChannel.sendMessage(embedBuilder.build()).queue();
    }

    public void sendTextMensageReportes(String text, String vitima ,String target){
        TextChannel textChannelById = jda.getTextChannelById(MENSAGE_REPORTE);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Jogador reportado:  " + target );
        embedBuilder.setColor(1);
        embedBuilder.setFooter(data() + "      Reportador: "  + vitima);
        embedBuilder.appendDescription("\n"+text+"\n");
        MessageChannel messageChannel = (MessageChannel) textChannelById;
        messageChannel.sendMessage(embedBuilder.build()).queue();
    }

    public static Main getInstance() {
        return instance;
    }

    public static String data() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    public Users getUsers() {
        return users;
    }
}
