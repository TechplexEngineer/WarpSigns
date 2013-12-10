package com.huskehhh.warpsigns.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WarpSignListener implements Listener {

    @EventHandler
    public void signChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        String[] lines = e.getLines();

        if (p.hasPermission("warpsigns.create")) {

            if (lines[0].equalsIgnoreCase("[Warp]")) {

                if (!lines[1].isEmpty()) {
                    e.setLine(0, "§1[Warp]");
                    p.sendMessage(ChatColor.GREEN + "Successfully made a Warp sign.");
                } else {
                    p.sendMessage(ChatColor.RED + "[WarpSigns] Please state a warp on the second line.");
                    e.setCancelled(true);
                }

            }

        } else {
            p.sendMessage(ChatColor.RED + "[WarpSigns] You don't have permission to do that!");
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void clickSign(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();
        
        if (block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();
                String[] lines = sign.getLines();
                
                if (sign.getLine(0).equalsIgnoreCase("§1[Warp]")) {
                    if (sign.getLine(3).startsWith("{") && sign.getLine(3).endsWith("}")) {
                    	String Fperm = sign.getLine(4).replace("{", "");
                    	String perm = Fperm.replace("}", "");
                    	if(p.hasPermission("warpsigns.use." + perm) || p.hasPermission("warpsigns.use.*")) {
                    		Bukkit.getServer().dispatchCommand(p, "warp " + sign.getLine(1));
                    	} else {
                    		p.sendMessage(ChatColor.DARK_RED + "You need the permission node " + ChatColor.RED + "warpsigns.use." + perm + ChatColor.DARK_RED + " to use this" + ChatColor.GREEN + " WarpSign" + ChatColor.DARK_RED + "!");
                    	}
                    } else {
                    	if(p.hasPermission("warpsigns.use")) {
                    		Bukkit.getServer().dispatchCommand(p, "warp " + sign.getLine(1));
                    	} else {
                    		p.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this" + ChatColor.GREEN + " WarpSign" + ChatColor.DARK_RED + "!");
                    	}
                    }
                    	
                    }
                    	
                }

            }

        }
    	
    }
