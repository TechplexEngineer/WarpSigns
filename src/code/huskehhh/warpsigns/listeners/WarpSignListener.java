package code.huskehhh.warpsigns.listeners;

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
                    e.setLine(0, ChatColor.GREEN + "[Warp]");
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
        Material material = e.getMaterial();
        Block block = e.getClickedBlock();

        if (material == Material.SIGN || material == Material.SIGN_POST || material == Material.WALL_SIGN) {

            if (block.getState() instanceof Sign) {

                Sign sign = (Sign) block.getState();
                String[] lines = sign.getLines();

                if (lines[0].equalsIgnoreCase("[Warp]")) {

                    if (p.hasPermission("warpsigns.use.*") || p.hasPermission("warpsign.use." + lines[1])) {
                        p.performCommand("warp " + lines[1]);
                    } else {
                        p.sendMessage(ChatColor.RED + "[WarpSigns] You don't have permission to do that!");
                    }

                }

            }

        }

    }
}
