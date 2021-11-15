package me.TurtlesAreHot.Materials.commands;

import me.TurtlesAreHot.Materials.Config;
import me.TurtlesAreHot.Materials.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Mats implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getLogger().info("Only players can run this command.");
            return false;
        }
        if(label.equalsIgnoreCase("materials")) {
            Player p = (Player) sender;
            if(args.length != 3) {
                p.sendMessage(ChatColor.RED + "You must provided 3 materials in order to run this command. Valid materials: " + Config.getMatStrings().toString());
                return false;
            }
            // Check the cooldown to make sure they don't have a cooldown
            long cooldown = (long) Cooldown.checkCooldown(p)/1000;
            // cooldown is the number of seconds that has passed since the cooldown started
            if(cooldown < Config.getCooldown() && cooldown != -1) {
                // This is the case the player is still on cooldown.
                p.sendMessage(ChatColor.RED + "You are still on cooldown. You can run this command in: " + (Config.getCooldown() - cooldown));
                return false;
            }
            if(cooldown >= Config.getCooldown()) {
                Cooldown.removeCooldown(p);
            }
            List<Material> validMats = Config.getValidMats();
            List<Material> mats = new ArrayList<>();
            for(String mat : args) {
                if(Material.getMaterial(mat.toUpperCase()) == null) {
                    p.sendMessage(ChatColor.RED + "You provided an invalid material. Please try again. Valid materials: " + Config.getMatStrings().toString());
                    return false;
                }
                if(!(validMats.contains(Material.getMaterial(mat.toUpperCase())))) {
                    p.sendMessage(ChatColor.RED + "You provided a material that is not in the material list. Valid materials: " + Config.getMatStrings().toString());
                    return false;
                }
                mats.add(Material.valueOf(mat.toUpperCase()));
            }
            int stacks = 0;
            if(p.hasPermission("materials.celestial")) {
                stacks = Config.getCelestialStacks();
            } else if(p.hasPermission("materials.god")) {
                stacks = Config.getGodStacks();
            } else if(p.hasPermission("materials.titan")) {
                stacks = Config.getTitanStacks();
            } else if(p.hasPermission("materials.legend")) {
                stacks = Config.getLegendStacks();
            } else if(p.hasPermission("materials.hero")) {
                stacks = Config.getHeroStacks();
            } else if(p.hasPermission("materials.elite")) {
                stacks = Config.getEliteStacks();
            } else {
                p.sendMessage(ChatColor.RED + "You do not have permission for this command.");
                return false;
            }
            List<ItemStack> items = calculateItemStacks(mats, stacks);
            ItemStack[] itemsArr = new ItemStack[items.size()];
            for(int i = 0; i < itemsArr.length; i++) {
                itemsArr[i] = items.get(i);
            }
            p.getInventory().addItem(itemsArr);
            Cooldown.addCooldown(p, System.currentTimeMillis());
            p.sendMessage(ChatColor.RED + "We have given you the materials you requested!");
        }
        return false;
    }

    private List<ItemStack> calculateItemStacks(List<Material> mats, int stacks) {
        List<ItemStack> addToInv = new ArrayList<>();
        for(Material mat : mats) {
            for(int i = 0; i < stacks; i++) {
                addToInv.add(new ItemStack(mat, 64));
            }
        }
        return addToInv;
    }
}
