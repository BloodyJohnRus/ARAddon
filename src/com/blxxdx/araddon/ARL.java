package com.blxxdx.araddon;

import net.essentialsx.api.v2.events.TeleportRequestResponseEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import ru.leymooo.antirelog.Antirelog;

import java.util.HashMap;
import java.util.Map;

public class ARL implements Listener {
    private Map<String, Long> lastUsedBottle;
    public ARL(){
        lastUsedBottle = new HashMap<>();
    }
    //Checking if user in cooldown and clicks on enderchest
    //This function includes checking on exp bottle cooldown
    @EventHandler
    public void throwing(PlayerInteractEvent event){
        var player = event.getPlayer();
        //OP are bypassing restrictions
        if(player.hasPermission("araddon.op")) return;
        //First, checking if player clicks on ender chest
        if(event.getAction()== Action.RIGHT_CLICK_BLOCK){
            //Removing duplicate interact(often bug on 1.16.5 core)
            if(event.getHand()== EquipmentSlot.HAND){
                var block = event.getClickedBlock();
                //Check, just for safety(we know that block isn't null, but code doesn't)
                if(block==null) return;
                if(block.getType()== Material.ENDER_CHEST){
                    //Getting AntiRelog instance(if exists)
                    var ar = Bukkit.getServer().getPluginManager().getPlugin("AntiRelog");
                    if(ar==null) return;
                    if(((Antirelog) ar).getPvpManager().isInPvP(player)){
                        event.setCancelled(true);
                        player.sendMessage(ARTU.applyColor(ARM.getInstance().getLocalConfig().ECErrorMessage));
                        return;
                    }
                }
            }
        }
        //Next, checking if player uses exp bottle
        if(event.getItem()!=null){
            var item = event.getItem();
            if(item.getType()==Material.EXPERIENCE_BOTTLE){
                if(!lastUsedBottle.containsKey(player.getName())) {
                    lastUsedBottle.put(player.getName(), System.currentTimeMillis());
                    return;
                }
                long lastCooldown = lastUsedBottle.get(player.getName());
                if(System.currentTimeMillis()-lastCooldown<=ARM.getInstance().getLocalConfig().expCooldown)
                    event.setCancelled(true);
                else lastUsedBottle.put(player.getName(), System.currentTimeMillis());
            }
        }
        //We're good with this function!
    }

    @EventHandler
    public void commandUse(TeleportRequestResponseEvent event){
        //Connecting to Essentials. Check, if event was cancelled.
        if(event.isCancelled()) return;
        //If not accepted - skip.
        if(!event.isAccept()) return;
        //Checking for AntiRelog
        var ar = Bukkit.getServer().getPluginManager().getPlugin("AntiRelog");
        if(ar==null) return;
        //Getting information about users
        var player1 = Bukkit.getPlayer(event.getRequestee().getUUID());
        var player2 = Bukkit.getPlayer(event.getRequester().getUUID());
        //There's no sense if some of players ain't alive, right?
        if(player1==null || player2==null) return;
        //OP are bypassing restrictions
        if(player1.hasPermission("araddon.op")) return;
        //Checking if someone has cooldown
        if(((Antirelog) ar).getPvpManager().isInPvP(player1) || ((Antirelog) ar).getPvpManager().isInPvP(player2)){
            //Cancelling event
            event.setCancelled(true);
            //This boolean if for duplicate error.
            boolean sended = false;
            //If not player1, then player2!
            if(((Antirelog) ar).getPvpManager().isInPvP(player1)) {
                player1.sendMessage(ARTU.applyColor(ARM.getInstance().getLocalConfig().teleportErrorMessageOrigin));
                player2.sendMessage(ARTU.applyColor(ARM.getInstance().getLocalConfig().teleportErrorMessageSender));
                sended = true;
            }
            if(((Antirelog) ar).getPvpManager().isInPvP(player2) && !sended) {
                player2.sendMessage(ARTU.applyColor(ARM.getInstance().getLocalConfig().teleportErrorMessageOrigin));
                player1.sendMessage(ARTU.applyColor(ARM.getInstance().getLocalConfig().teleportErrorMessageSender));
            }
        }
        //Congrats! Now we're not dumb as fuck. Join my server's Discord - VexMine(ru, eng from me) https://discord.gg/56A4nTEZR7
    }
}
