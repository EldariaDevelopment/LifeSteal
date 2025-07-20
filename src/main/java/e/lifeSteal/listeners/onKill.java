package e.lifeSteal.listeners;

import e.lifeSteal.LifeSteal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.sql.SQLException;

public class onKill {
    private LifeSteal plugin;

    // This class will handle the logic for when a player is killed
    // It will manage heart transfers and player data updates
    @EventHandler
    public void onPlayerKill(org.bukkit.event.entity.PlayerDeathEvent event) throws SQLException {
        // Logic to handle heart transfer when a player is killed
        Player killer = event.getEntity().getKiller();
        Player victim = event.getEntity();

        if (killer == null || victim == null || killer.equals(victim)) {
            System.out.println("Couldn't find killer or victim, or they are the same player.");
            return;
        }
        int victimHearts = plugin.getHeartsDatabase().getPlayerHealth(victim);
        int killerHearts = plugin.getHeartsDatabase().getPlayerHealth(killer);
        plugin.getHeartsDatabase().setPlayerHealth(victim, victimHearts - 1);
        plugin.getHeartsDatabase().setPlayerHealth(killer, killerHearts + 1);

    }
}
