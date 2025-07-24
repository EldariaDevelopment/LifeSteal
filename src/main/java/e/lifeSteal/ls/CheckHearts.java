package e.lifeSteal.ls;

import e.lifeSteal.LifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CheckHearts {
    private final LifeSteal plugin;

    public CheckHearts(LifeSteal plugin) {
        this.plugin = plugin;
    }

    public void checkHearts(Player player, String[] args, String Prefix) {
        if (!player.hasPermission("ls.mod")) {
            player.sendMessage(plugin.getNoPermMod());
            return;
        }
        if (args.length < 2 || args[1].isEmpty()) {
            player.sendMessage("§c/ls checkhearts <player>");
            return;
        }

        String playerName = args[1];
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            player.sendMessage(Prefix + playerName);
            return;
        }

        try {
            int targetPlayerHealth=plugin.getHeartsDatabase().getPlayerHealth(targetPlayer);
            player.sendMessage(Prefix +"§c"+targetPlayer.getName()+ "§r has §c" + targetPlayerHealth/2 + "§4 Hearts §ror§c " + targetPlayerHealth + " §4HP");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
