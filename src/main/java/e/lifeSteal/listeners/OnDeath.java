package e.lifeSteal.listeners;

import e.lifeSteal.LifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

public class onDeath implements Listener {

    private final LifeSteal plugin;

    public onDeath(LifeSteal plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) throws SQLException {
        Player player = e.getEntity();
        if (e.getEntity().getKiller() == null) {
            System.out.println("Killer is not a player.");
            return;
        }
        Player killer = e.getEntity().getKiller();

        if (killer.equals(player)) {
            System.out.println("Killer and victim are the same player.");
            return;
        }
        int TakenHealth = 2;
        int health = plugin.getPlayerDatabase().getPlayerHealth(player);

        if(health < TakenHealth) {
            player.sendMessage("§cYou do not have enough hearts to lose.");
            plugin.getPlayerDatabase().setPlayerHealth(player, 2); // Set to 0 if not enough hearts
            player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);

            plugin.getUpdateHearts().UpdatePlayerDisplayHeart(player, 2);
            plugin.getPlayerDatabase().setPlayerGhost(player);

            Bukkit.broadcastMessage("§c" + player.getName() + " has been turned into a ghost due to having no hearts left.");

            return;
        } else {
            plugin.getPlayerDatabase().setPlayerHealth(player, health - TakenHealth);
            plugin.getUpdateHearts().UpdatePlayerDisplayHeart(player, health - TakenHealth);
            player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);

            Bukkit.broadcastMessage("§c" + player.getName() + " has lost a heart and now has " + (health / 2 - TakenHealth/2) + " hearts left.");
        }

        player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
    }
}

