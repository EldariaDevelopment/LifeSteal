package e.lifeSteal.ls;

import e.lifeSteal.LifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class EditHearts {
    private final LifeSteal plugin;

    public EditHearts(LifeSteal plugin) {
        this.plugin = plugin;
    }

    public void editHearts(Player player, String[] args, String Prefix) throws SQLException {
        if (!player.hasPermission("ls.mod")) {
            player.sendMessage(plugin.getNoPermMod());
            return;
        }
        if (args.length != 3) {
            player.sendMessage("§c/ls edithearts <player> <amount>");
            return;
        }
        int amount = Integer.parseInt(args[2]);
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(Prefix + "§cPlayer not found: " + args[1]);
            return;
        }
        if (amount <= 0) {
            player.sendMessage(Prefix + "§cInvalid amount");
            return;
        }
        int oldHealth = plugin.getPlayerDatabase().getPlayerHealth(target);
        plugin.getPlayerDatabase().setPlayerHealth(target, amount * 2);
        plugin.getUpdateHearts().UpdatePlayerDisplayHeart(target, amount * 2);
        player.sendMessage(Prefix + "§rUpdated§c " + target.getName() + "§r's hearts: §c" + oldHealth + "§7->§c" + amount);

    }
//    public String[] getEditHeartsAutoFill(String[] args) throws SQLException {

//List<String> completions = new ArrayList<>();
//        if (args.length == 2) {
//        } else if (args.length == 3) {
//            return new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
//        }
//        return new String[]{};
//    }
}
