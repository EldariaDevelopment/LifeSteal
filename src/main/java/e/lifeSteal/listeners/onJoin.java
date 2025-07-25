package e.lifeSteal.listeners;

import e.lifeSteal.LifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class onJoin implements Listener {

    private final LifeSteal plugin;

    public onJoin(LifeSteal plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws SQLException {

//        //if the player is new, add them to the database
        if (!e.getPlayer().hasPlayedBefore()){
            Bukkit.broadcastMessage("Player joined for the first time: " + e.getPlayer().getName());
            //add the player to the database
            this.plugin.getPlayerDatabase().addPlayer(e.getPlayer());
        }

        Player player = e.getPlayer();
        int health = plugin.getPlayerDatabase().getPlayerHealth(player);
        if(plugin.getGhostSettings().isGhost()){
            plugin.getGhostSettings().setGhost(player);
            player.sendMessage("§cYou have joined as a ghost.");
        }
        player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        plugin.getUpdateHearts().UpdatePlayerDisplayHeart(player, health);


    }


}
