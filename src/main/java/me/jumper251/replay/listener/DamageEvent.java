package me.jumper251.replay.listener;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.jumper251.replay.ReplaySystem;
import me.jumper251.replay.api.ReplayAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DamageEvent extends AbstractListener {

    public static Map<Player, Boolean> recordingPlayers = new HashMap<>();
    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();

        if (!player.hasPermission("group.vagt") || player.isOp()) {
            return;
        }

        double absorptionHealth = (player.getMaxHealth() - player.getHealth()) * 2;
        if(absorptionHealth <= 0) {
            if (recordingPlayers.containsKey(player)) {
                return;
            }
            recordingPlayers.put(player, false);
            //Bukkit.broadcastMessage("§8§l[ §6§l➲ §8§l] §fEn vagt blev slået og begynder at record!");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDate = now.format(formatter).replace(" ", "-");
            String name = player.getName() + "-" + formattedDate;

            List<Player> toRecord = new ArrayList<>(Bukkit.getOnlinePlayers());
            ReplayAPI.getInstance().recordReplay(name, player, toRecord);


            new BukkitRunnable() {
                @Override
                public void run() {
                    ReplayAPI.getInstance().stopReplay(name, recordingPlayers.get(player), true);
                    //Bukkit.broadcastMessage("§8§l[ §6§l➲ §8§l] §fStoppede med at record for " + player.getName() + "!" + " (" + recordingPlayers.get(player) + ")");
                    recordingPlayers.remove(player);
                }
            }.runTaskLater(ReplaySystem.getInstance(), 1200);
        }
    }
}
