package org.devathon.contest2016.structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.DevathonPlugin;

import java.util.ArrayList;

/**
 * Created by guillian on 06/11/2016.
 */
public class TransmitterStructure extends BukkitRunnable implements Structure {

	private Location location;
	private ArmorStand armorStand;
	private ArrayList<Structure> linkedStructures = new ArrayList<>();
	private boolean powered = false;

	public TransmitterStructure(Location location, ArmorStand armorStand) {
		this.location = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
		spawnStructure(armorStand);
		DevathonPlugin.getStructureManager().addStructure(this);
		this.linkedStructures = DevathonPlugin.getStructureManager().getNearbyStructure(this);
	}

	public void spawnStructure(ArmorStand armorStand) {

		Block pillar = this.location.getBlock();
		pillar.setType(Material.PURPUR_PILLAR);
		pillar.setData((byte) 0);

		Block endrod = this.location.clone().add(0, 1, 0).getBlock();
		endrod.setType(Material.END_ROD);
		endrod.setData((byte) 0);

		if (armorStand == null) {
			armorStand = (ArmorStand) this.location.getWorld().spawnEntity(this.location.clone().add(0.5, -0.7D, 0.5), EntityType.ARMOR_STAND);
		}

		armorStand.setHelmet(new ItemStack(Material.SEA_LANTERN));
		armorStand.setVisible(false);
		armorStand.setInvulnerable(true);
		armorStand.getLocation().setYaw(0);
		armorStand.setGravity(false);
		armorStand.setCustomName("$$**STRUCTURE_TRANS**$$");
		this.armorStand = armorStand;

		this.runTaskTimer(DevathonPlugin.getInstance(), 0L, 1L);
	}

	public void destroy() {
		this.cancel();

		this.armorStand.remove();
		this.location.getBlock().setType(Material.AIR);
		this.location.clone().add(0, 1, 0).getBlock().setType(Material.AIR);
		DevathonPlugin.getStructureManager().removeStructure(this);

		this.getLocation().getWorld().dropItemNaturally(this.location, DevathonPlugin.getStructureManager().getTransItemstack());

		for (Structure structure : this.linkedStructures) {
			structure.removeLinked(this);
		}
	}

	public Location getLocation() {
		return this.location;
	}

	public ArmorStand getArmorStand() {
		return this.armorStand;
	}

	public void setLinked(ArrayList<Structure> linked) {
		this.linkedStructures = linked;
	}

	public ArrayList<Structure> getLinked() {
		return this.linkedStructures;
	}

	public void removeLinked(Structure structure) {
		this.linkedStructures.remove(structure);
	}

	public void addLinked(Structure structure) {
		this.linkedStructures.add(structure);
	}

	public boolean isPowered() {
		return this.powered;
	}

	@Override
	public void run() {
		this.armorStand.teleport(this.location.clone().add(0.5, -0.7D, 0.5));

		Block block = this.location.getBlock();

		if (block.isBlockFacePowered(BlockFace.DOWN) ||
				block.isBlockFacePowered(BlockFace.NORTH) ||
				block.isBlockFacePowered(BlockFace.EAST) ||
				block.isBlockFacePowered(BlockFace.SOUTH) ||
				block.isBlockFacePowered(BlockFace.WEST)) {
			this.powered = true;
		} else {
			this.powered = false;
		}
	}

}
