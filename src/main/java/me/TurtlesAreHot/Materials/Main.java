package me.TurtlesAreHot.Materials;

import me.TurtlesAreHot.Materials.commands.Mats;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private static File dataFolder;

    @Override
    public void onEnable() {
        dataFolder = getDataFolder();
        createCooldownFolder();
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        List<String> mats = new ArrayList<>();
        mats.add("STONE");
        mats.add("DIRT");
        // There is 36 inventory slots
        config.addDefault("celestial-stacks", 7);
        config.addDefault("god-stacks", 6);
        config.addDefault("titan-stacks", 5);
        config.addDefault("legend-stacks", 4);
        config.addDefault("hero-stacks", 3);
        config.addDefault("elite-stacks", 2);
        config.addDefault("valid-mats", mats);
        config.addDefault("cooldown", 10);
        config.options().copyDefaults(true);
        this.saveConfig();
        Config.reloadConfig();
        getCommand("materials").setExecutor(new Mats());
    }

    @Override
    public void onDisable() {

    }

    public static File getFolder() { return dataFolder; }

    private void createCooldownFolder() {
        File cooldownFolder = new File(getFolder(), "/cooldown/");
        if(!(cooldownFolder.exists())) {
            cooldownFolder.mkdirs();
        }
    }
}
