package e.lifeSteal.items.DashSword;

import e.lifeSteal.LifeSteal;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class DashSwordLogic implements Listener {

    private final LifeSteal plugin;
    private final NamespacedKey keys;
    private final Map<UUID, Long> cooldowns = new HashMap<>();


    public DashSwordLogic(LifeSteal plugin) {
        this.plugin = plugin;
        this.keys = new NamespacedKey(plugin, "DashSword");
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!(event.getAction() == RIGHT_CLICK_AIR || event.getAction() == RIGHT_CLICK_BLOCK)) {
            return;
        }
        if(event.getHand() != null && isDashSword(player.getInventory().getItem(event.getHand()))){
            long now = System.currentTimeMillis();
            long lastDash = cooldowns.getOrDefault(player.getUniqueId(), 0L);
            if (now - lastDash < 3000) {
                player.sendMessage("Dash Sword is on cooldown!");
                return;
            }
            cooldowns.put(player.getUniqueId(), now);
            player.sendMessage("Dash Sword used!");
            Dash(player);

        }

    }

    private boolean isDashSword(ItemStack item) {
        // Check if the item is a Dash Sword
        if (item != null && item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer().has(keys)){
            return true;
        }

        return false; // Example custom model data
    }

    private void Dash(Player player){

        double m = 1.5*player.getVelocity().length()+2; // Dash distance multiplier
        //player.getVelocity().normalize();
        player.sendMessage(player.getVelocity().normalize().toString());
        Vector direction = player.getVelocity().add(player.getEyeLocation().getDirection().multiply(m));
        player.setVelocity(direction);
        player.sendMessage(player.getVelocity().normalize().toString());
    }

}
