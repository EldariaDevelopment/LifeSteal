package e.lifeSteal.ls;

import e.lifeSteal.LifeSteal;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Status {
    private final LifeSteal plugin;

    public Status(LifeSteal plugin) {
        this.plugin = plugin;
    }

    public void getStatus(Player player, String[] args, String Prefix) throws SQLException {
        if (!player.hasPermission("ls.mod")) {
            player.sendMessage(plugin.getNoPermMod());
            return;
        }
        if (args.length != 2) {
            player.sendMessage("§c/ls Status <player>");
            return;
        }
        Player target = plugin.getServer().getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(Prefix + "§cPlayer not found: " + args[1]);
            return;
        }
        int health = plugin.getPlayerDatabase().getPlayerHealth(target);
        if (plugin.getPlayerDatabase().isPlayerGhost(target)) {
            player.sendMessage(Prefix + "§c" + target.getName() + "§r is a ghost.");
        } else {
            player.sendMessage(Prefix + "§c" + target.getName() + "§r has §c" + health / 2 + "§4 Hearts §ror§c " + health + " §4HP");
        }
    }
}
