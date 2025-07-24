package e.lifeSteal.listeners;

import e.lifeSteal.LifeSteal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

public class onKill implements Listener {
    private final LifeSteal plugin;

    public onKill(LifeSteal plugin) {
        this.plugin = plugin;
    }

    // This class will handle the logic for when a player is killed
    // It will manage heart transfers and player data updates
    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) throws SQLException {
        // Logic to handle heart transfer when a player is killed
        Player killer = event.getEntity().getKiller();
        Player victim = event.getEntity();

        if (killer == null || victim == null || killer.equals(victim)) {
            System.out.println("Couldn't find killer or victim, or they are the same player.");
            return;
        }
        int TakenHearts = 2;

        //Victim
        // Check if the victim has enough hearts to lose
        int victimHealth = plugin.getHeartsDatabase().getPlayerHealth(victim);
        plugin.getHeartsDatabase().setPlayerHealth(victim, victimHealth - TakenHearts);
        victimHealth = plugin.getHeartsDatabase().getPlayerHealth(victim);
        victim.sendMessage("You have been killed by " + killer.getName() + "You have " + victimHealth/2 + " hearts left.");

        //Killer
        int killerHealth = plugin.getHeartsDatabase().getPlayerHealth(killer);
        plugin.getHeartsDatabase().setPlayerHealth(killer, killerHealth + TakenHearts);
        killerHealth = plugin.getHeartsDatabase().getPlayerHealth(killer);
        killer.sendMessage("You have killed " + victim.getName() + " You have " + killerHealth/2 + " hearts.");
        //update the hearts of both players
        plugin.getUpdateHearts().UpdatePlayerDisplayHeart(killer, killerHealth);
        plugin.getUpdateHearts().UpdatePlayerDisplayHeart(victim, victimHealth);
    }
}
