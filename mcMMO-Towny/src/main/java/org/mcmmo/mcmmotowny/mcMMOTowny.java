package org.mcmmo.mcmmotowny;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.mcmmo.mcmmotowny.config.Config;
import org.mcmmo.mcmmotowny.listeners.ExperienceListener;
import org.mcmmo.mcmmotowny.util.LogFilter;

public class mcMMOTowny extends JavaPlugin {
    public static mcMMOTowny p;

    private boolean mcMMOEnabled = false;
    private boolean townyEnabled = false;

    /**
     * Things to be run when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        try {
            p = this;
            getLogger().setFilter(new LogFilter(this));

            setupMcMMO();
            setupTowny();

            if (!isMcMMOEnabled()) {
                this.getLogger().warning("mcMMO-Towny requires mcMMO to run, please download mcMMO. http://dev.bukkit.org/server-mods/mcmmo/");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }

            if (!isTownyEnabled()) {
                this.getLogger().warning("mcMMO-Towny requires Towny to run, please download Towny. http://palmergames.com/towny/");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }

            Config.getInstance();

            registerEvents();
        }
        catch (Throwable t) {
            getLogger().severe("There was an error while enabling mcMMO-Towny!");

            if (!(t instanceof ExceptionInInitializerError)) {
                t.printStackTrace();
            }
            else {
                getLogger().info("Please do not replace the mcMMO-Towny jar while the server is running.");
            }

            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void setupMcMMO() {
        if (getServer().getPluginManager().isPluginEnabled("mcMMO")) {
            mcMMOEnabled = true;
        }
    }

    private void setupTowny() {
        if (getServer().getPluginManager().isPluginEnabled("Towny")) {
            townyEnabled = true;
        }
    }

    /**
     * Things to be run when the plugin is disabled.
     */
    @Override
    public void onDisable() {
    }

    public void debug(String message) {
        getLogger().info("[Debug] " + message);
    }

    public boolean isMcMMOEnabled() {
        return mcMMOEnabled;
    }

    public boolean isTownyEnabled() {
        return townyEnabled;
    }

    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();

        // Register events
        pluginManager.registerEvents(new ExperienceListener(), this);
    }
}
