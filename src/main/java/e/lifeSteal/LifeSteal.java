package e.lifeSteal;

import e.lifeSteal.database.Yaml.OptionsYaml;
import e.lifeSteal.items.DashSword.DashSwordLogic;
import e.lifeSteal.items.DashSword.GiveDashSword;
import e.lifeSteal.items.Hyperion.HyperionGive;
import e.lifeSteal.items.Hyperion.HyperionLogic;
import e.lifeSteal.listeners.onDeath;
import e.lifeSteal.ls.Revive;
import e.lifeSteal.methods.SendMultiline;
import e.lifeSteal.commands.ResetHearts;
import e.lifeSteal.ls.*;
import e.lifeSteal.database.SQLite.PlayerDatabase;
import e.lifeSteal.listeners.onJoin;
import e.lifeSteal.listeners.onKill;
import e.lifeSteal.methods.UpdateHearts;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public final class LifeSteal extends JavaPlugin {

    public static LifeSteal plugin;
    public LifeSteal() {
        plugin = this;
    }
    private PlayerDatabase playerDatabase;

    File configFile = new File(getDataFolder(), "config.yml");


    public static Plugin getInstance() {
        return plugin;
    }


    @Override
    public void onEnable() {
        ServiceManager serviceManager = new ServiceManager();
        serviceManager.registerService(LifeSteal.class, this);
        serviceManager.registerService(UpdateHearts.class, new UpdateHearts(this));
        serviceManager.registerService(SendMultiline.class, new SendMultiline(this));
        serviceManager.registerService(CheckHearts.class, new CheckHearts(serviceManager));
        serviceManager.registerService(EditHearts.class, new EditHearts(serviceManager));
        serviceManager.registerService(Revive.class, new Revive(serviceManager));
        serviceManager.registerService(OptionsYaml.class, new OptionsYaml(serviceManager));
        serviceManager.registerService(Status.class, new Status(serviceManager)); // Placeholder, will be set after database connection


        try {
            // Ensure the plugin's data folder exists
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            playerDatabase = new PlayerDatabase(getDataFolder().getAbsolutePath() + "/PlayerData.db");


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to database! " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getCommand("GiveDashSword").setExecutor(new GiveDashSword(this));
        getCommand("GiveHyperion").setExecutor(new HyperionGive(this));
        getCommand("ResetHearts").setExecutor(new ResetHearts(serviceManager));

        getServer().getPluginManager().registerEvents(new DashSwordLogic(this), this);
        getServer().getPluginManager().registerEvents(new onKill(serviceManager), this);
        getServer().getPluginManager().registerEvents(new onJoin(serviceManager), this);
        getServer().getPluginManager().registerEvents(new HyperionLogic(this), this);
        getServer().getPluginManager().registerEvents(new onDeath(serviceManager), this);

        getCommand("ls").setExecutor(new ls(serviceManager));
        getCommand("ls").setTabCompleter(new ls(serviceManager));

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        try {
            playerDatabase.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Object getSetting(String path) {
        return getConfig().get(path);
    }

    public void setSetting(String path, Object value) {
        getConfig().set(path, value);
        saveConfig();
    }
    public String getVersion() {
        return getDescription().getVersion();
    }

    //move to yaml config

//    public edithearts getEditHearts() {
//        return new edithearts();
//    }

}

//heartDatabase = new HeartDatabase("plugins/LifeSteal/hearts.db");
//getLogger().info("Heart database initialized successfully.");
//        } catch (Exception e) {
//getLogger().severe("Failed to initialize heart database: " + e.getMessage());
//        e.printStackTrace();