package me.jumper251.replay.commands.replay;


import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import me.jumper251.replay.ReplaySystem;
import me.jumper251.replay.commands.AbstractCommand;
import me.jumper251.replay.commands.SubCommand;
import me.jumper251.replay.filesystem.saving.ReplaySaver;

public class ReplayDeleteCommand extends SubCommand {

	public ReplayDeleteCommand(AbstractCommand parent) {
		super(parent, "delete", "Sletter en genafspilning", "delete <Navn>", false);
	}

	@Override
	public boolean execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length != 2) return false;

		String name = args[1];

		if (ReplaySaver.exists(name)) {
			ReplaySaver.delete(name);
			cs.sendMessage(ReplaySystem.PREFIX + "§aSuccesfuldt slettet genafspilning.");
		} else {
			cs.sendMessage(ReplaySystem.PREFIX + "§cGenafspilning ikke fundet.");
		}

		return true;
	}

	@Override
	public List<String> onTab(CommandSender cs, Command cmd, String label, String[] args) {
		return ReplaySaver.getReplays().stream()
				.filter(name -> StringUtil.startsWithIgnoreCase(name, args.length > 1 ? args[1] : null))
				.collect(Collectors.toList());
	}




}
