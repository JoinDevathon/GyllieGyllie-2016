package org.devathon.contest2016.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.devathon.contest2016.structures.ReceiverStructure;
import org.devathon.contest2016.structures.TransmitterStructure;

/**
 * Created by guillian on 06/11/2016.
 */
public class TestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String args[]) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Player command only!");
			return true;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage("noob");
			return true;
		} else if (args[0].equalsIgnoreCase("trans")) {
			new TransmitterStructure(player.getLocation(), null);
		} else if (args[0].equalsIgnoreCase("receive")) {
			new ReceiverStructure(player.getLocation(), null);
		} else {
			for (Entity entity : Bukkit.getWorld("world").getEntities()) {
				if (entity instanceof ArmorStand) {
					entity.remove();
				}
			}
		}

		return true;
	}
}
