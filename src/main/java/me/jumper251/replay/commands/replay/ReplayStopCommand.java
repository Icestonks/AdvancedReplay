package me.jumper251.replay.commands.replay;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import me.jumper251.replay.ReplaySystem;
import me.jumper251.replay.commands.AbstractCommand;
import me.jumper251.replay.commands.SubCommand;
import me.jumper251.replay.filesystem.saving.DefaultReplaySaver;
import me.jumper251.replay.filesystem.saving.ReplaySaver;
import me.jumper251.replay.replaysystem.Replay;
import me.jumper251.replay.utils.ReplayManager;

public class ReplayStopCommand extends SubCommand {

	public ReplayStopCommand(AbstractCommand parent) {
		super(parent, "stop", "Stopper og gemmer et replay", "stop <Navn> [Muligheder]", false);
	}

	@Override
	public boolean execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length > 3 || args.length < 2) return false;

		String name = args[1];
		boolean isForce = args.length == 3 && args[2].equals("-force");
		boolean isNoSave = args.length == 3 && args[2].equals("-nosave");

		if (ReplayManager.activeReplays.containsKey(name) && ReplayManager.activeReplays.get(name).isRecording()) {
			Replay replay = ReplayManager.activeReplays.get(name);

			if (isNoSave || replay.getRecorder().getData().getActions().size() == 0) {
				replay.getRecorder().stop(false);

				cs.sendMessage(ReplaySystem.PREFIX + "§7Replayet §e" + name + "§7 blev stoppet succesfuldt.");
			} else {
				if (ReplaySaver.exists(name) && !isForce) {
					cs.sendMessage(ReplaySystem.PREFIX + "§cReplayet eksisterer allerede. Brug §o-force §r§cfor at overskrive eller §o-nosave §r§cfor at slette optagelsen.");
					return true;
				}

				cs.sendMessage(ReplaySystem.PREFIX + "Gemmer replayet §e" + name + "§7...");
				replay.getRecorder().stop(true);

				String path = ReplaySaver.replaySaver instanceof DefaultReplaySaver ? ReplaySystem.getInstance().getDataFolder() + "/replays/" + name + ".replay" : null;
				cs.sendMessage(ReplaySystem.PREFIX + "§7Replayet blev gemt succesfuldt" + (path != null ? " til §o" + path : ""));
			}

		} else {
			cs.sendMessage(ReplaySystem.PREFIX + "§cReplayet blev ikke fundet.");
		}

		return true;
	}

	@Override
	public List<String> onTab(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 3) return Arrays.asList("-nosave", "-force");

		return ReplayManager.activeReplays.keySet().stream()
				.filter(name -> StringUtil.startsWithIgnoreCase(name, args.length > 1 ? args[1] : null))
				.collect(Collectors.toList());
	}




}
