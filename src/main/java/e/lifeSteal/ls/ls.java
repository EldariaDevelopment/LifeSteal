package e.lifeSteal.ls;

import e.lifeSteal.LifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static e.lifeSteal.Methods.SendMultiline.sendMultiline;

public class ls implements CommandExecutor, TabCompleter {
    private final LifeSteal plugin;
    private final String splitter;
    private final String version;
    private final String Prefix;
    private static final List<String> SUBCOMMANDS = Arrays.asList(
            "checkhearts", "config", "edithearts", "eliminate", "givehearts", "help", "recipe", "revive", "status"
    );

    public ls(LifeSteal plugin) {
        this.plugin = plugin;
        this.splitter = ChatColor.translateAlternateColorCodes('&', plugin.getSplitter());
        this.version = plugin.getDescription().getVersion();
        this.Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getPrefix());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendMultiline(player,
                    splitter,
                    "§cLife§4Steal",
                    "§fRequires: §cSkBee",
                    "§fVersion: §c" + version,
                    "§7",
                    "§fA §cLife§4Steal §fskript made by §cnovystxr§f on spigot.",
                    "§fType §c/ls help§f for a list of commands",
                    "§4§n§oContributers <3",
                    splitter
            );
            return true;
        }

        String sub = args[0].toLowerCase();

        try {
            switch (sub) {
                case "checkhearts":
                    plugin.getCheckHearts().checkHearts(player, args, Prefix);
                    break;
                case "config":
                    sender.sendMessage("§eConfig command...");
                    break;
                case "edithearts":
                    plugin.getEditHearts().editHearts(player, args, Prefix);
                    break;
                case "eliminate":
                    sender.sendMessage("§eEliminating player...");
                    break;
                case "givehearts":
                    sender.sendMessage("§eGiving hearts...");
                    break;
                case "help":
                    sendMultiline(player,
                            "§4§nCommands:",
                "§7§c/withdraw <amount>§7 - §fWithdraw physical hearts§c/ls recipe §7- §fView the crafting recipe for hearts",
                "§c/ls status §7- §fView the current lifesteal settings§c/ls config§7 [§4ls.admin§7] - §fOpen the config gui",
                "§c/ls givehearts <amount>§7 [§4ls.admin§7] §fGive yourself physical hearts§c/ls edithearts <player> [+/-]<amount>§7 - [§4ls.mod§7] §fEdit a player's hearts",
                "§c/ls checkhearts <player>§7 [§4ls.mod§7] - §fCheck a player's hearts§c/ls revive <player>§7 [§4ls.mod§7] - §fRevive a player who is eliminated§c/ls eliminate <player>§7 [§4ls.mod§7] - §fEliminate a player",
                splitter);
                    break;
                case "recipe":
                    sender.sendMessage("§eRecipe command...");
                    break;
                case "revive":
                    sender.sendMessage("§eReviving player...");
                    break;
                case "status":
                    sender.sendMessage("§eStatus command...");
                    break;
                default:
                    sender.sendMessage("§cUnknown subcommand. Use /" + label + " help");
                    break;
            }
        } catch (Exception e) {
            sender.sendMessage("§cAn error occurred while executing the command. Please check the console for details.");
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String current = args[0].toLowerCase();
            List<String> completions = new ArrayList<>();
            for (String sub : SUBCOMMANDS) {
                if (sub.startsWith(current)) {
                    completions.add(sub);
                }
            }
            return completions;
        }
        return Collections.emptyList();
    }
}