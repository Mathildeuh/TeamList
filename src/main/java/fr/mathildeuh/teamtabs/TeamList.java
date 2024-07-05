package fr.mathildeuh.teamtabs;

import fr.mathildeuh.teamtabs.commands.MetierCommand;
import fr.mathildeuh.teamtabs.commands.MetierReloadCommand;
import fr.mathildeuh.teamtabs.commands.MonMetierCommand;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class TeamList extends JavaPlugin implements Listener {

    FileConfiguration descriptions;
    public static HashMap<String, String> descriptionsMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        try {
            loadDescriptions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        saveDefaultConfig();

        getCommand("metier").setExecutor(new MetierCommand());
        getCommand("metierreloadconfig").setExecutor(new MetierReloadCommand());
        getCommand("monmetier").setExecutor(new MonMetierCommand());

        getServer().getPluginManager().registerEvents(this, this);

    }

    private void loadDescriptions() throws IOException {
        File descciptionFile = new File(getDataFolder(), "descriptions.yml");
        descriptions = YamlConfiguration.loadConfiguration(descciptionFile);
        if (!descciptionFile.exists()) {
            descciptionFile.getParentFile().mkdirs();
            descciptionFile.createNewFile();
        }

        for (String metier : descriptions.getKeys(false)) {
            descriptionsMap.put(metier, descriptions.getString(metier));
            System.out.printf("metier: %s, description: %s", metier, descriptionsMap.get(metier));
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void reloadTabs(@Nullable Player player){
        if (player == null) {
            Bukkit.getOnlinePlayers().forEach(TeamList::updatePlayerListName);
        } else {
            updatePlayerListName(player);
        }
    }

    private static void updatePlayerListName(Player player) {
        String playerName = player.getName();
        org.bukkit.scoreboard.Team team = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(playerName);
        if (team != null) {
            // If the player is in a team, prepend the team name to the player's name
            player.setPlayerListName(team.getPrefix() + playerName + team.getSuffix());
        } else {
            // Handle players not in any team, if necessary
            player.setPlayerListName(playerName);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BossBar bossBar = Bukkit.createBossBar(player.getName(), BarColor.BLUE, BarStyle.SOLID);
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);
        bossBar.setTitle("§aFaites vos bases proche du Spawn.");
        bossBar.addPlayer(player);
    }
}
