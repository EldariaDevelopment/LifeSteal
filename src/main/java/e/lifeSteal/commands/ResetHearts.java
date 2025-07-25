package e.lifeSteal.commands;

import e.lifeSteal.LifeSteal;
import e.lifeSteal.ServiceManager;
import e.lifeSteal.database.SQLite.PlayerDatabase;
import e.lifeSteal.methods.UpdateHearts;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ResetHearts implements CommandExecutor {

    private final PlayerDatabase playerDatabase;
    private final UpdateHearts updateHearts;

    public ResetHearts(ServiceManager serviceManager) {
        this.playerDatabase = serviceManager.getService(PlayerDatabase.class);
        this.updateHearts = serviceManager.getService(UpdateHearts.class);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /resethearts <player>");
            return true;
        }

        String playerName = args[0];
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage("Player not found: " + playerName);
            return true;
        }
        if (!sender.hasPermission("ls.mod")) {
            sender.sendMessage("You do not have permission to use Hyperion.");
            return true;
        }

        try {
            playerDatabase.setPlayerHealth(targetPlayer, 20); // Reset to default health (20)
            updateHearts.UpdatePlayerDisplayHeart(targetPlayer, 20);
            sender.sendMessage("Reset hearts of player " + playerName + " to default.");
        } catch (SQLException e) {
            e.printStackTrace();
            sender.sendMessage("Failed to reset hearts for player: " + playerName);
        }

        return true; // Return true if the command was processed successfully
    }
}
