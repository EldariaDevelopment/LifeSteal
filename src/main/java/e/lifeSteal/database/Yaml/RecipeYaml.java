package e.lifeSteal.database.Yaml;

import e.lifeSteal.LifeSteal;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Set;

public class RecipeYaml {

    private final static RecipeYaml instance = new RecipeYaml();

    private File file;
    private YamlConfiguration config;

    public FileConfiguration GetConfig() {
        return this.config;
    }



    public void load(){
        file = new File(LifeSteal.getInstance().getDataFolder(), "recipes.yml");

        if(!file.exists())
            LifeSteal.getInstance().saveResource("recipe.yml",false);

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


    public static RecipeYaml getInstance(){
        return instance;
    }
}
