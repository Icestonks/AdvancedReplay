package me.jumper251.replay.replaysystem.data.types;

import org.bukkit.entity.Player;

public class VagtRecording {

    Player player;
    String name;
    boolean isRecording;

    public VagtRecording(Player player, String name, boolean isRecording) {
        this.player = player;
        this.name = name;
        this.isRecording = isRecording;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getName() {
        return this.name;
    }

    public boolean getRecording() {
        return this.isRecording;
    }

    public void setRecording(boolean recording) {
        this.isRecording = recording;
    }

}
