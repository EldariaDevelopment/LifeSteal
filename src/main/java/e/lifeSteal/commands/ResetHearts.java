package e.lifeSteal.commands;

import e.lifeSteal.LifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ResetHearts implements CommandExecutor {
    private final LifeSteal plugin;

    public ResetHearts(LifeSteal plugin) {
        this.plugin = plugin;
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

        try {
            this.plugin.getHeartsDatabase().setPlayerHealth(targetPlayer, 20); // Reset to default health (20)
            this.plugin.getUpdateHearts().UpdatePlayerDisplayHeart(targetPlayer, 20);
            sender.sendMessage("Reset hearts of player " + playerName + " to default.");
        } catch (SQLException e) {
            e.printStackTrace();
            sender.sendMessage("Failed to reset hearts for player: " + playerName);
        }

        return true; // Return true if the command was processed successfully
    }
}
