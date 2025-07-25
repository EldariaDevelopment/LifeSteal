package e.lifeSteal.listeners;

import e.lifeSteal.ServiceManager;
import e.lifeSteal.database.SQLite.PlayerDatabase;
import e.lifeSteal.methods.UpdateHearts;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class onJoin implements Listener {

    private final PlayerDatabase playerDatabase;
    private final UpdateHearts updateHearts;

    public onJoin(ServiceManager serviceManager) {
        this.playerDatabase = serviceManager.getService(PlayerDatabase.class);
        this.updateHearts = serviceManager.getService(UpdateHearts.class);

    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws SQLException {

//        //if the player is new, add them to the database
        if (!e.getPlayer().hasPlayedBefore()){
            Bukkit.broadcastMessage("Player joined for the first time: " + e.getPlayer().getName());
            //add the player to the database
            this.playerDatabase.addPlayer(e.getPlayer());
        }

        Player player = e.getPlayer();
        int health = this.playerDatabase.getPlayerHealth(player);
        if (this.playerDatabase.isPlayerGhost(player)) {
            this.playerDatabase.setPlayerGhost(player);
            player.sendMessage("§cYou have joined as a ghost.");
        }
        player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        this.updateHearts.UpdatePlayerDisplayHeart(player, health);


    }


}
