package e.lifeSteal;

import e.lifeSteal.methods.UpdateHearts;
import e.lifeSteal.methods.SendMultiline;
import e.lifeSteal.commands.CheckHealth;
import e.lifeSteal.commands.ResetHearts;
import e.lifeSteal.commands.SetHealth;
import e.lifeSteal.ls.CheckHearts;
import e.lifeSteal.ls.EditHearts;
import e.lifeSteal.ls.ls;
import e.lifeSteal.database.SQLite.PlayerDatabase;
import e.lifeSteal.items.DashSword.DashSwordLogic;
import e.lifeSteal.items.DashSword.GiveDashSword;
import e.lifeSteal.items.Hyperion.HyperionGive;
import e.lifeSteal.items.Hyperion.HyperionLogic;
import e.lifeSteal.listeners.onJoin;
import e.lifeSteal.listeners.onKill;
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

    File configFile = new File(getDataFolder(), "config.yml");


    private PlayerDatabase playerDatabase;
    private UpdateHearts updateHearts; // Change type here
    private SendMultiline sendMultiline; // Change type here

    private CheckHearts checkHearts;
    private EditHearts editHearts; // Change type here

    public static Plugin getInstance() {
        return plugin;
    }


    @Override
    public void onEnable() {
        this.checkHearts = new CheckHearts(this);
        this.updateHearts = new UpdateHearts(this);
        this.sendMultiline = new SendMultiline(this);
        this.editHearts = new EditHearts(this);


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

        getCommand("sethealth").setExecutor(new SetHealth(this));
        getCommand("GiveDashSword").setExecutor(new GiveDashSword(this));
        getCommand("GiveHyperion").setExecutor(new HyperionGive(this));
        getCommand("ResetHearts").setExecutor(new ResetHearts(this));
        getCommand("CheckHealth").setExecutor(new CheckHealth(this));
        getServer().getPluginManager().registerEvents(new DashSwordLogic(this), this);
        getServer().getPluginManager().registerEvents(new onKill(this), this);
        getServer().getPluginManager().registerEvents(new onJoin(this), this);
        getServer().getPluginManager().registerEvents(new HyperionLogic(this), this);

        getCommand("ls").setExecutor(new ls(this));
        getCommand("ls").setTabCompleter(new ls(this));

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

    public PlayerDatabase getHeartsDatabase() {
        return playerDatabase;
    }
    public UpdateHearts getUpdateHearts() {
        return updateHearts;
    }
    public SendMultiline sendMultiline() {
        return sendMultiline;
    }
    public CheckHearts getCheckHearts() {
        return checkHearts;
    }
    public EditHearts getEditHearts() {
        return editHearts;
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
//        return getConfig().getString("version", "3.01");
    }
    public String getSplitter() {
        return getConfig().getString("splitter", "&7&m                                                                      ");
    }
    public String getNoPermMod() {
        return getConfig().getString("noperm_mod", "&7[&cLife&4Steal&7] &cYou are lacking the permission node &7[&4ls.mod&7]");
    }
    public String getNoPermAdmin() {
        return getConfig().getString("noperm_admin", "&7[&cLife&4Steal&7] &cYou are lacking the permission node &7[&4ls.admin&7]");
    }
    public String getPrefix() {
        return getConfig().getString("prefix", "&7[&cLife&4Steal&7] ");
    }
    public String getContributers() {
        return getConfig().getString("contributors", "Almondz_");
    }
//    public edithearts getEditHearts() {
//        return new edithearts();
//    }

}

//heartDatabase = new HeartDatabase("plugins/LifeSteal/hearts.db");
//getLogger().info("Heart database initialized successfully.");
//        } catch (Exception e) {
//getLogger().severe("Failed to initialize heart database: " + e.getMessage());
//        e.printStackTrace();