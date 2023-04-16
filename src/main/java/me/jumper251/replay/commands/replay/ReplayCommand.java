package me.jumper251.replay.commands.replay;

import me.jumper251.replay.ReplaySystem;
import me.jumper251.replay.commands.AbstractCommand;
import me.jumper251.replay.commands.MessageFormat;
import me.jumper251.replay.commands.SubCommand;

public class ReplayCommand extends AbstractCommand {

	public ReplayCommand() {
		super("Replay", ReplaySystem.PREFIX + " §fv" + ReplaySystem.getInstance().getDescription().getVersion(), "replay.command");
	}

	@Override
	protected MessageFormat setupFormat() {
		return new MessageFormat()
				.overview("§8▍ §7/{command} §f{args} §7 - §f{desc}")
				.syntax(ReplaySystem.PREFIX + "\n&8▍ §7Usage: §f/{command} {args}")
				.permission(ReplaySystem.PREFIX + "§CDette har du ikke permission til")
				.notFound(ReplaySystem.PREFIX + "§7Kommando blev ikke fundet!");
	}

	@Override
	protected SubCommand[] setupCommands() {
		
		return new SubCommand[] { new ReplayStartCommand(this), 
				new ReplayStopCommand(this).addAlias("save"), 
				new ReplayPlayCommand(this), 
				new ReplayDeleteCommand(this).addAlias("remove"),
				new ReplayJumpCommand(this),
				new ReplayLeaveCommand(this),
				new ReplayInfoCommand(this),
				new ReplayListCommand(this), 
				new ReplayReloadCommand(this),
				new ReplayReformatCommand(this),
				new ReplayMigrateCommand(this) };
	}

}
