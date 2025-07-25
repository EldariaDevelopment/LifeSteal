package e.lifeSteal.ls;

import e.lifeSteal.LifeSteal;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class revive {
    private final LifeSteal plugin;

    public revive(LifeSteal plugin) {
        this.plugin = plugin;
    }

    public void revivePlayer(Player player, String[] args, String Prefix) throws SQLException {
        if (!player.hasPermission("ls.mod")) {
            player.sendMessage(plugin.getNoPermMod());
            return;
        }
        if (args.length != 2) {
            player.sendMessage("§c/ls revive <player>");
            return;
        }
        Player target = plugin.getServer().getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(Prefix + "§cPlayer not found: " + args[1]);
            return;
        }
        if (!(plugin.getPlayerDatabase().getPlayerHealth(target) <= 0)) {
            player.sendMessage(Prefix + "§c" + target.getName() + "§r is not dead.");
        }
        plugin.getPlayerDatabase().setPlayerHealth(target, 2); // Revive with 1 heart (2 HP)
        plugin.getUpdateHearts().UpdatePlayerDisplayHeart(target, 2);
        plugin.getGhostSettings().removeGhost(target);
        player.sendMessage(Prefix + "§rRevived §c" + target.getName() + "§r with 1 heart.");

    }
}
