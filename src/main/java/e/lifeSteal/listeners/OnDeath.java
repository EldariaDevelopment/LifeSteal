package e.lifeSteal.listeners;

import e.lifeSteal.LifeSteal;
import e.lifeSteal.ServiceManager;
import e.lifeSteal.database.SQLite.PlayerDatabase;
import e.lifeSteal.methods.UpdateHearts;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

public class onDeath implements Listener {

    private final PlayerDatabase playerDatabase;
    private final UpdateHearts updateHearts;

    public onDeath(ServiceManager serviceManager) {
        this.playerDatabase = serviceManager.getService(PlayerDatabase.class);
        this.updateHearts = serviceManager.getService(UpdateHearts.class);
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) throws SQLException {
        Player player = e.getEntity();
        if (!(player.getKiller() instanceof Player)) {
            System.out.println("Killer is not a player.");
            return;
        }
        Player killer = player.getKiller();

        if (killer.equals(player)) {
            System.out.println("Killer and victim are the same player.");
            return;
        }
        int TakenHealth = 2;
        int health = playerDatabase.getPlayerHealth(player);

        if(health < TakenHealth) {
            player.sendMessage("§cYou do not have enough hearts to lose.");
            playerDatabase.setPlayerHealth(player, 2); // Set to 0 if not enough hearts
            player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);

            updateHearts.UpdatePlayerDisplayHeart(player, 2);
            playerDatabase.setPlayerGhost(player);

            Bukkit.broadcastMessage("§c" + player.getName() + " has been turned into a ghost due to having no hearts left.");

            return;
        } else {
            playerDatabase.setPlayerHealth(player, health - TakenHealth);
            updateHearts.UpdatePlayerDisplayHeart(player, health - TakenHealth);
            player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);

            Bukkit.broadcastMessage("§c" + player.getName() + " has lost a heart and now has " + (health / 2 - TakenHealth/2) + " hearts left.");
        }

        player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
    }
}

