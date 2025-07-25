package e.lifeSteal.methods;

import e.lifeSteal.LifeSteal;
import org.bukkit.entity.Player;

public class SendMultiline {
    private final LifeSteal plugin;

    public SendMultiline(LifeSteal plugin) {
        this.plugin = plugin;
    }

    public static void sendMultiline(Player player, String... lines) {
        for (String line : lines) {
            player.sendMessage(line);
        }
    }
}
