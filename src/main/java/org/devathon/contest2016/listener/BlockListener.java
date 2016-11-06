package org.devathon.contest2016.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.structures.ReceiverStructure;
import org.devathon.contest2016.structures.Structure;
import org.devathon.contest2016.structures.TransmitterStructure;

/**
 * Created by guillian on 06/11/2016.
 */
public class BlockListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();

		ItemStack itemInHand = player.getItemInHand();
		itemInHand.setAmount(1);

		if (itemInHand.equals(DevathonPlugin.getStructureManager().getReceiveItemstack())) {
			new ReceiverStructure(event.getBlock().getLocation(), null);
		} else if (itemInHand.equals(DevathonPlugin.getStructureManager().getTransItemstack())) {
			new TransmitterStructure(event.getBlock().getLocation(), null);
		}

	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		Location location = new Location(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());

		for (Structure structure : DevathonPlugin.getStructureManager().getStructures()) {
			if (structure.getLocation().equals(location) || structure.getLocation().clone().add(0, 1, 0).equals(location)) {
				structure.destroy();
				event.getBlock().getDrops().clear();
			}
		}
	}

}
