package com.blxxdx.araddon;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ARC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.isOp()) return true;
        if(strings.length>0){
            if(strings[0].equals("reload")){
                ARM.getInstance().load();
                commandSender.sendMessage(ChatColor.YELLOW + "Reload successful.");
            }
        }
        else commandSender.sendMessage("Running AntiRelog addon v.1 by " + ChatColor.RED + "BLXXDX\n"
                + ChatColor.WHITE + "Use " + ChatColor.YELLOW + "/araddon reload " + ChatColor.WHITE + " to reload config.");
        return true;
    }
}
