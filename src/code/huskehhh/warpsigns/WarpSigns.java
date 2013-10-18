package code.huskehhh.warpsigns;

import code.huskehhh.warpsigns.listeners.WarpSignListener;
import code.huskehhh.warpsigns.metrics.Metrics;
import code.huskehhh.warpsigns.updater.Updater;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class WarpSigns extends JavaPlugin {

    YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/WarpSigns/config.yml"));
    Updater updater = null;

    public void onEnable() {

        getServer().getPluginManager().registerEvents(new WarpSignListener(), this);
        createConfig();

        if (config.getBoolean("auto-update")) {
            updater = new Updater(this, 41513, this.getFile(), Updater.UpdateType.DEFAULT, false);
        }

        if (config.getBoolean("metrics")) {
            try {
                Metrics metrics = new Metrics(this);
                metrics.start();
            } catch (IOException e) {
                // Failed to submit the stats :-(
            }
        }

    }

    public void onDisable() {

    }

    private void createConfig() {
        File f = new File("plugins/WarpSigns/config.yml");

        if (!f.exists()) {

            config.options().header("WarpSigns, made by Husky and Smithey!");
            config.set("auto-update", true);
            config.set("metrics", true);

            try {
                config.save("plugins/WarpSigns/config.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
