package e.lifeSteal.Methods;

import e.lifeSteal.LifeSteal;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateHearts {
    private final LifeSteal plugin;

    public UpdateHearts(LifeSteal plugin) {

        this.plugin = plugin;
    }

    public void UpdatePlayerDisplayHeart(Player player, int health) throws SQLException {
        if (health <= 0) {
            return;
        }
        player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);


    }
}
