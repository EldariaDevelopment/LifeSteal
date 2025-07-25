package e.lifeSteal.commands;

import e.lifeSteal.LifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class SetHealth implements CommandExecutor {
    private final LifeSteal plugin;

    public SetHealth(LifeSteal plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length !=2) {
            sender.sendMessage("Usage: /sethealth <player> <amount>");
            return true;
        }
        String playerName = args[0];
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage("Player not found: " + playerName);
            return true;
        }

        int amount = 0;

        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid health amount: " + args[1]);
            return true;
        }
        // Update the player's health
        try{
            this.plugin.getHeartsDatabase().setPlayerHealth(targetPlayer, amount);
            this.plugin.getUpdateHearts().UpdatePlayerDisplayHeart(targetPlayer, amount);
            sender.sendMessage("Set health of player " + playerName + " to " + amount);
        } catch (SQLException e) {
            e.printStackTrace();
            sender.sendMessage("Failed to set health for player: " + playerName);
        }
        return true; // Return true if the command was processed successfully
    }
}
