package e.lifeSteal.items.Hyperion;

import e.lifeSteal.LifeSteal;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class HyperionLogic implements Listener {
    private final LifeSteal plugin;
    private final NamespacedKey keys;
    private final Map<UUID, Long> cooldowns = new HashMap<>();


    public HyperionLogic(LifeSteal plugin) {
        this.plugin = plugin;
        this.keys = new NamespacedKey(plugin, "Hyperion");
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getHand() == null) {
            return; // Ensure the hand is not null
        }
        if (!(event.getHand() == org.bukkit.inventory.EquipmentSlot.HAND)){
            return;
        }

        if (!(event.getAction() == RIGHT_CLICK_AIR || event.getAction() == RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (event.getHand() != null && isHyperion(player.getInventory().getItem(event.getHand()))) {
            if (!player.hasPermission("hyperion.use")) {
                player.sendMessage("You do not have permission to use Hyperion.");
                return;
            }
            // Check if right click and holding the correct item here (not shown)
            witherImpact(player);
        }

    }

    private boolean isHyperion(ItemStack item) {
            // Check if the item is a Dash Sword
            if (item != null && item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer().has(keys)){
                return true;
            }

            return false; // Example custom model data
        }

    public void witherImpact(Player player) {
        Location dest;
        if (player.getTargetBlockExact(10) != null) {
            dest = player.getTargetBlockExact(10).getLocation().add(0.5, 1, 0.5);
        } else {
            Vector direction = player.getLocation().getDirection().normalize().multiply(10);
            dest = player.getLocation().clone().add(direction);
        }

        // If inside a block, move up until not inside a solid block, but stop if a ceiling is found
        int maxY = player.getWorld().getMaxHeight() - 2;
        while (dest.getBlock().getType().isSolid()) {
            if (dest.clone().add(0, 1, 0).getBlock().getType().isSolid()) {
                // Ceiling found, move down until air or original Y
                while (dest.getBlock().getType().isSolid() && dest.getY() > player.getLocation().getY()) {
                    dest.add(0, -1, 0);
                }
                break;
            }
            dest.add(0, 1, 0);
            if (dest.getY() > maxY) break;
        }

        // Preserve pitch and yaw
        dest.setPitch(player.getLocation().getPitch());
        dest.setYaw(player.getLocation().getYaw());

        player.teleport(dest);

        dest.getWorld().spawnParticle(org.bukkit.Particle.EXPLOSION_HUGE, dest, 1);

        double radius = 5.0;
        double damage = 10.0;
        for (Entity entity : dest.getWorld().getNearbyEntities(dest, radius, radius, radius)) {
            if (entity instanceof LivingEntity && !entity.equals(player)) {
                ((LivingEntity) entity).damage(damage, player);
            }
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 5, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 1));
    }
}
