package me.jumper251.replay.listener;

import me.jumper251.replay.ReplaySystem;
import me.jumper251.replay.api.ReplayAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathEvent extends AbstractListener {


    @EventHandler
    public void onDeatEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.hasPermission("vagt") && !player.isOp()) {

            if(DamageEvent.recordingPlayers.containsKey(player)) {

                if (DamageEvent.recordingPlayers.get(player).getRecording()) {

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            //Bukkit.broadcastMessage("§8§l[ §6§l➲ §8§l] §fStoppede med at record for " + player.getName() + "!" + " (" + DamageEvent.recordingPlayers.get(player).getRecording() + ")");
                            ReplayAPI.getInstance().stopReplay(DamageEvent.recordingPlayers.get(player).getName(), true, true);
                            DamageEvent.recordingPlayers.get(player).setRecording(false);
                            DamageEvent.recordingPlayers.remove(player);
                        }
                    }.runTaskLater(ReplaySystem.getInstance(), 60);
                }
            }
        }
    }
}
