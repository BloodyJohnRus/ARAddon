package com.blxxdx.araddon;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.bukkit.entity.Player;
import ru.leymooo.antirelog.Antirelog;

import java.util.List;
public class ARP extends PlaceholderExpansion {
    private final ARM instance;

    public ARP(ARM instance) {
        this.instance = instance;
    }

    public @NotNull String getIdentifier() {
        return "araddon";
    }

    public @NotNull String getAuthor() {
        return "BLXXDX";
    }

    public @NotNull String getVersion() {
        return "1";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if(player==null) return "false";
        var ar = instance.getServer().getPluginManager().getPlugin("AntiRelog");
        if(ar==null) return "false";
        if(params.equals("in_pvp"))
            return ((Antirelog) ar).getPvpManager().isInPvP(player.getPlayer())+"";
        if(params.startsWith("in_pvp_")){
            String nick = params.replace("in_pvp_", "");
            if(Bukkit.getPlayer(nick)!=null)
                return ((Antirelog) ar).getPvpManager().isInPvP(Bukkit.getPlayer(nick))+"";
        }
        return "false";
    }
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if(player==null) return "false";
        var ar = instance.getServer().getPluginManager().getPlugin("AntiRelog");
        if(ar==null) return "false";
        if(params.equals("in_pvp"))
            return ((Antirelog) ar).getPvpManager().isInPvP(player.getPlayer())+"";
        if(params.startsWith("in_pvp_")){
            String nick = params.replace("in_pvp_", "");
            if(Bukkit.getPlayer(nick)!=null)
                return ((Antirelog) ar).getPvpManager().isInPvP(Bukkit.getPlayer(nick))+"";
        }
        return "false";
    }
}
