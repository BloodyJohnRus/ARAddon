package com.blxxdx.araddon;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ARM extends JavaPlugin {
    private FileConfiguration config;
    private ARConfig local;
    private String path;
    private static ARM inst;
    public void load(){
        config = YamlConfiguration.loadConfiguration(new File(path));
        if(!config.contains("expCooldown"))
            config.set("expCooldown", local.expCooldown);
        if(!config.contains("teleportErrorMessageOrigin"))
            config.set("teleportErrorMessageOrigin", local.teleportErrorMessageOrigin);
        if(!config.contains("teleportErrorMessageSender"))
            config.set("teleportErrorMessageSender", local.teleportErrorMessageSender);
        if(!config.contains("ECErrorMessage"))
            config.set("ECErrorMessage", local.ECErrorMessage);
        local.ECErrorMessage = config.getString("ECErrorMessage");
        local.expCooldown = config.getInt("expCooldown");
        local.teleportErrorMessageOrigin = config.getString("teleportErrorMessageOrigin");
        local.teleportErrorMessageSender = config.getString("teleportErrorMessageSender");
        save();
    }
    public void save(){
        try {
            config.save(new File(path));
        }
        catch (Exception e){
            getLogger().info(ChatColor.RED + "Unexpected error when tried to save the config!");
            getLogger().info(ChatColor.RED + "Stack trace: " + e.getMessage() + "\n"+e.getCause());
        }
        local.ECErrorMessage = config.getString("ECErrorMessage");
        local.expCooldown = config.getInt("expCooldown");
        local.teleportErrorMessageOrigin = config.getString("teleportErrorMessageOrigin");
        local.teleportErrorMessageSender = config.getString("teleportErrorMessageSender");
    }
    public static ARM getInstance(){
        return inst;
    }
    public ARConfig getLocalConfig(){
        return local;
    }
    public void onEnable(){
        inst = this;
        path = getDataFolder() + "/config.yml";
        local = new ARConfig();
        getCommand("araddon").setExecutor(new ARC());
        load();
        getServer().getPluginManager().registerEvents(new ARL(), this);
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI")!=null) new ARP(this).register();
    }
}
