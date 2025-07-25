package e.lifeSteal.database.Yaml;

import e.lifeSteal.LifeSteal;
import e.lifeSteal.ServiceManager;
import e.lifeSteal.database.SQLite.PlayerDatabase;
import e.lifeSteal.methods.UpdateHearts;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ResourceBundle;
import java.util.Set;

public class OptionsYaml {

    private File file;
    private YamlConfiguration config;

    private final LifeSteal LifeSteal;

    public OptionsYaml(ServiceManager serviceManager) {
        this.LifeSteal = serviceManager.getService(LifeSteal.class);
    }


    public void load(){
        file = new File(LifeSteal.getInstance().getDataFolder(), "Settings.yml");

        if(!file.exists())
            LifeSteal.getInstance().saveResource("Settings.yml",false);

        config = new YamlConfiguration();
        config.options().parseComments(true);
        try {
            config.load(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Set<String> GetKeys(){
        return config.getKeys(true);

    }

    public String GetStringConfig(String path){

        return config.getString(path);
    }

    public ItemStack GetItemStackConfig(String path){

        return config.getItemStack(path);
    }

    public void save(){
        try {
            config.save(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void set(String path, Object value){
        config.set(path, value);
        save();
    }

    public OptionsYaml getInstance(){
        return this;
    }

    public String getSplitter() {
        return this.LifeSteal.getConfig().getString("splitter", "&7&m                                                                      ");
    }

    public String getNoPermMod() {
        return this.LifeSteal.getConfig().getString("noperm_mod", "&7[&cLife&4Steal&7] &cYou are lacking the permission node &7[&4ls.mod&7]");
    }
    public String getNoPermAdmin() {
        return this.LifeSteal.getConfig().getString("noperm_admin", "&7[&cLife&4Steal&7] &cYou are lacking the permission node &7[&4ls.admin&7]");
    }
    public String getPrefix() {
        return this.LifeSteal.getConfig().getString("prefix", "&7[&cLife&4Steal&7] ");
    }
    public String getContributers() {
        return this.LifeSteal.getConfig().getString("contributors", "Almondz_");
    }


}
