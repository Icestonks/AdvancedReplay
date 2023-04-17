package me.jumper251.replay.listener;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.jumper251.replay.ReplaySystem;
import me.jumper251.replay.api.ReplayAPI;
import me.jumper251.replay.replaysystem.data.types.VagtRecording;
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

    public static Map<Player, VagtRecording> recordingPlayers = new HashMap<>();
    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();

        if (!player.hasPermission("vagt") || player.isOp()) {
            //Bukkit.broadcastMessage("returner");
            return;
        }

        if(player.getHealth() <= 14) {
            if (recordingPlayers.containsKey(player)) {
                return;
            }
            //Bukkit.broadcastMessage("§8§l[ §6§l➲ §8§l] §fEn vagt blev slået og begynder at record!");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDate = now.format(formatter).replace(" ", "-");
            String name = player.getName() + "-" + formattedDate;

            VagtRecording vagtRecording = new VagtRecording(player, name, true);
            recordingPlayers.put(player, vagtRecording);

            List<Player> toRecord = new ArrayList<>(Bukkit.getOnlinePlayers());
            ReplayAPI.getInstance().recordReplay(name, player, toRecord);


            new BukkitRunnable() {
                @Override
                public void run() {
                    if (recordingPlayers.containsKey(player)) {
                        if (recordingPlayers.get(player).getRecording()) {

                            ReplayAPI.getInstance().stopReplay(name, false, true);
                            //Bukkit.broadcastMessage("§8§l[ §6§l➲ §8§l] §fStoppede med at record for " + player.getName() + "!" + " (GEMTE IKKE)");
                            recordingPlayers.remove(player);
                        }
                        else {
                            recordingPlayers.remove(player);
                        }
                    }
                }
            }.runTaskLaterAsynchronously(ReplaySystem.getInstance(), 1200);
        }
    }
}
