package e.lifeSteal.ls;

import e.lifeSteal.ServiceManager;
import e.lifeSteal.database.SQLite.PlayerDatabase;
import e.lifeSteal.database.Yaml.OptionsYaml;
import e.lifeSteal.methods.UpdateHearts;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CheckHearts {

    private final PlayerDatabase playerDatabase;
    private final UpdateHearts updateHearts;
    private final OptionsYaml optionsYaml;

    public CheckHearts(ServiceManager serviceManager) {
        this.playerDatabase = serviceManager.getService(PlayerDatabase.class);
        this.updateHearts = serviceManager.getService(UpdateHearts.class);
        this.optionsYaml = serviceManager.getService(OptionsYaml.class);
    }

    public void checkHearts(Player player, String[] args, String Prefix) {
        if (!player.hasPermission("ls.mod")) {
            player.sendMessage(optionsYaml.getNoPermMod());
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
            int targetPlayerHealth=this.playerDatabase.getPlayerHealth(targetPlayer);
            player.sendMessage(Prefix +"§c"+targetPlayer.getName()+ "§r has §c" + targetPlayerHealth/2 + "§4 Hearts §ror§c " + targetPlayerHealth + " §4HP");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
