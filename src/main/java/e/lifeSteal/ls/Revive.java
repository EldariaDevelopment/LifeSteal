package e.lifeSteal.ls;

import e.lifeSteal.LifeSteal;
import e.lifeSteal.ServiceManager;
import e.lifeSteal.database.SQLite.PlayerDatabase;
import e.lifeSteal.database.Yaml.OptionsYaml;
import e.lifeSteal.methods.UpdateHearts;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Revive {
    private final PlayerDatabase playerDatabase;
    private final UpdateHearts updateHearts;
    private final OptionsYaml optionsYaml;
    private final LifeSteal plugin;

    public Revive(ServiceManager serviceManager) {
        this.playerDatabase = serviceManager.getService(PlayerDatabase.class);
        this.updateHearts = serviceManager.getService(UpdateHearts.class);
        this.optionsYaml = serviceManager.getService(OptionsYaml.class);
        this.plugin = serviceManager.getService(LifeSteal.class);
    }


    public void revivePlayer(Player player, String[] args, String Prefix) throws SQLException {
        if (!player.hasPermission("ls.mod")) {
            player.sendMessage(optionsYaml.getNoPermMod());
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
        if (!(playerDatabase.getPlayerHealth(target) <= 0)) {
            player.sendMessage(Prefix + "§c" + target.getName() + "§r is not dead.");
        }
        playerDatabase.setPlayerHealth(target, 2); // Revive with 1 heart (2 HP)
        updateHearts.UpdatePlayerDisplayHeart(target, 2);
//        plugin.getGhostSettings().removeGhost(target);
        playerDatabase.removePlayerGhost(target);

        player.sendMessage(Prefix + "§rRevived §c" + target.getName() + "§r with 1 heart.");

    }
}
