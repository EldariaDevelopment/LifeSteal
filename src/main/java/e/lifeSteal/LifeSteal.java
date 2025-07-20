package e.lifeSteal;

import e.lifeSteal.commands.SetHealth;
import e.lifeSteal.database.HeartDatabase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class LifeSteal extends JavaPlugin {

    private HeartDatabase heartDatabase;


    @Override
    public void onEnable() {
        try {
            // Ensure the plugin's data folder exists
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            heartDatabase = new HeartDatabase(getDataFolder().getAbsolutePath() + "/hearts.db");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to database! " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getCommand("sethealth").setExecutor(new SetHealth(this));

    }

    @Override
    public void onDisable() {
        try {
            heartDatabase.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HeartDatabase getHeartsDatabase() {
        return heartDatabase;
    }
}
//heartDatabase = new HeartDatabase("plugins/LifeSteal/hearts.db");
//getLogger().info("Heart database initialized successfully.");
//        } catch (Exception e) {
//getLogger().severe("Failed to initialize heart database: " + e.getMessage());
//        e.printStackTrace();