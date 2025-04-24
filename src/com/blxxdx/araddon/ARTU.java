package com.blxxdx.araddon;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public class ARTU {
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
    public static String applyColor(String message){
        int lastindex = 0;
        while((lastindex = message.indexOf("#", lastindex))<message.length()){
            if(lastindex==-1 && message.lastIndexOf("#")==-1) break;
            message = message.replace(message.substring(lastindex-1, lastindex+7),
                    ChatColor.of(hex2Rgb(message.substring(lastindex, lastindex+7)))+"");
        }
        return message.replace("&",ChatColor.COLOR_CHAR+"");
    }
}
