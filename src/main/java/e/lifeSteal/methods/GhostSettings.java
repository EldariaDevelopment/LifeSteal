package e.lifeSteal.methods;

import e.lifeSteal.LifeSteal;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class GhostSettings {

    private final LifeSteal plugin;

    public GhostSettings(LifeSteal plugin) {
        this.plugin = plugin;
    }
    public void checkGhost() {
        // This method checks if the player is a ghost
        // Implementation goes here
    }
    public void setGhost(Player player) {
        player.setGameMode(org.bukkit.GameMode.SPECTATOR);
    }
    public void removeGhost(Player player) {
        player.setGameMode(org.bukkit.GameMode.SURVIVAL);
        player.teleport(player.getWorld().getSpawnLocation());
    }
    public boolean isGhost(Player player) throws SQLException {

        // This method checks if the player is currently a ghost
        // Implementation goes here
        return plugin.getPlayerDatabase().isPlayerGhost(player); // Placeholder return value
    }
}
