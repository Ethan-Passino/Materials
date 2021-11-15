package me.TurtlesAreHot.Materials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private static FileConfiguration config;

    public static void reloadConfig() {
        config = JavaPlugin.getPlugin(Main.class).getConfig();
    }

    public static int getCelestialStacks() { return config.getInt("celestial-stacks"); }

    public static int getGodStacks() { return config.getInt("god-stacks"); }

    public static int getTitanStacks() { return config.getInt("titan-stacks"); }

    public static int getLegendStacks() { return config.getInt("legend-stacks"); }

    public static int getHeroStacks() { return config.getInt("hero-stacks"); }

    public static int getEliteStacks() { return config.getInt("elite-stacks"); }

    public static List<String> getMatStrings() { return config.getStringList("valid-mats"); }

    public static List<Material> getValidMats() {
        List<String> strMats = getMatStrings();
        List<Material> mats = new ArrayList<>();
        for(String mat : strMats) {
            if(Material.getMaterial(mat) == null) {
                Bukkit.getLogger().info(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + mat + " is not a valid material.");
                continue;
            }
            mats.add(Material.valueOf(mat));
        }
        return mats;
    }

    public static int getCooldown() { return config.getInt("cooldown"); }
}
