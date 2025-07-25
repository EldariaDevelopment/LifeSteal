package e.lifeSteal.database.SQLite;

import org.bukkit.entity.Player;

import java.sql.*;

public class PlayerDatabase {
    private final Connection connection;

    public PlayerDatabase(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS players (" +
                    "uuid TEXT PRIMARY KEY, " +
                    "username TEXT NOT NULL, " +
                    "health INTEGER NOT NULL DEFAULT 20, " +
                    "ghost BOOL NOT NULL DEFAULT 0)"
            );

            //this is health and not hearts since integers and not float >:)
            // I'll probably set the max to 25 hearts or 50 health or something

        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    public void addPlayer(Player player) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (uuid, username) VALUES (?, ?)")){
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, player.getDisplayName());
            preparedStatement.executeUpdate();
        }

    }

    public boolean playerExists(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public void setPlayerHealth(Player player, int health) throws SQLException{

        //if the player doesn't exist, add them
        if (!playerExists(player)){
            addPlayer(player);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET health = ? WHERE uuid = ?")) {
            preparedStatement.setInt(1, health);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }

    public int getPlayerHealth(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT health FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("health");
            } else {
                return 0; // Return 0 if the player has no points
            }
        }
    }
    public void setPlayerGhost(Player player, boolean IsGhost) throws SQLException{

        //if the player doesn't exist, add them
        if (!playerExists(player)){
            addPlayer(player);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET ghost = ? WHERE uuid = ?")) {
            preparedStatement.setBoolean(1, IsGhost);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }

    public boolean getPlayerGhost(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT ghost FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("ghost");
            }
        }
        return false;
    }

}
