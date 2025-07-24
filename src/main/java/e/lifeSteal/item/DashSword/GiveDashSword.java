package e.lifeSteal.item.DashSword;

import e.lifeSteal.LifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;

public class GiveDashSword implements CommandExecutor {

    private final LifeSteal plugin;

    public GiveDashSword(LifeSteal plugin) {

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /GiveDashSword <player>");
            return true;
        }
        String playerName = args[0];
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            sender.sendMessage("Player not found: " + playerName);
            return true;
        }

        player.getInventory().addItem(getDashSword(plugin));
        player.sendMessage("You have been given a Dash Sword!");
        return false;
    }
    public static ItemStack getDashSword(LifeSteal plugin) {
        ItemStack sword = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(true);
            NamespacedKey keys = new NamespacedKey(plugin, "DashSword");
            meta.getPersistentDataContainer().set(keys, PersistentDataType.BYTE, (byte) 1);
            sword.setItemMeta(meta);
        }
        return sword;
    }


}
