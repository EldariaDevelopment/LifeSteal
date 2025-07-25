package e.lifeSteal.listeners;

import e.lifeSteal.LifeSteal;
import e.lifeSteal.ServiceManager;
import e.lifeSteal.database.SQLite.PlayerDatabase;
import e.lifeSteal.methods.UpdateHearts;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

public class onKill implements Listener {
    
    private final PlayerDatabase playerDatabase;
    private final UpdateHearts updateHearts;

    public onKill(ServiceManager serviceManager) {
        this.playerDatabase = serviceManager.getService(PlayerDatabase.class);
        this.updateHearts = serviceManager.getService(UpdateHearts.class);

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

        int TakenHealth = 2;

        int victimHealth = this.playerDatabase.getPlayerHealth(victim);
        int killerHealth = this.playerDatabase.getPlayerHealth(killer);

        if (victimHealth < TakenHealth) {
            killer.sendMessage("You cannot take hearts from " + victim.getName() + " because they do not have enough hearts." + " You will get " + victimHealth/2 + " hearts instead.");
            killer.sendMessage("You have " + (killerHealth/2+victimHealth/2) + " hearts now.");
            this.playerDatabase.setPlayerHealth(victim, 2); // Set to 0 if not enough hearts
        } else{
            this.playerDatabase.setPlayerHealth(victim, killerHealth + TakenHealth);
//            killer.sendMessage("You have taken " + (TakenHealth / 2) + " hearts from " + victim.getName() + ".");
            killer.sendMessage("You have killed " + victim.getName() + " You have " + killerHealth/2 + " hearts.");
            this.updateHearts.UpdatePlayerDisplayHeart(killer, killerHealth);
        }

    }
}
