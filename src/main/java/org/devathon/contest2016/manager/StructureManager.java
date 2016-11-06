package org.devathon.contest2016.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.structures.ReceiverStructure;
import org.devathon.contest2016.structures.Structure;
import org.devathon.contest2016.structures.TransmitterStructure;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by guillian on 06/11/2016.
 */
public class StructureManager {

	public ArrayList<Structure> structures;
	private ItemStack transItemstack = DevathonPlugin.createItem(Material.PURPUR_PILLAR, 1, (short) 0, ChatColor.DARK_PURPLE + "Transmitter", Arrays.asList(ChatColor.BLUE + "Place to create Transmitter"));
	private ItemStack receiveItemstack = DevathonPlugin.createItem(Material.PURPUR_BLOCK, 1, (short) 0, ChatColor.DARK_PURPLE + "Receiver", Arrays.asList(ChatColor.BLUE + "Place to create Receiver"));

	public StructureManager() {
		this.structures = new ArrayList<>();
	}

	public void init() {
		for (Entity entity : Bukkit.getWorld("world").getEntities()) {
			if (entity instanceof ArmorStand) {
				if (entity.getName().equals("$$**STRUCTURE_TRANS**$$")) {
					TransmitterStructure transmitterStructure = new TransmitterStructure(entity.getLocation().add(0, 0.7D, 0), (ArmorStand) entity);
					this.structures.add(transmitterStructure);
					System.out.print("Found a transmitter on " + transmitterStructure.getLocation());
				} else if (entity.getName().equals("$$**STRUCTURE_RECEIVE**$$")) {
					ReceiverStructure receiverStructure = new ReceiverStructure(entity.getLocation().add(0, 0.7D, 0), (ArmorStand) entity);
					this.structures.add(receiverStructure);
					System.out.print("Found a receiver on " + receiverStructure.getLocation());
				}
			}
		}

		for (Structure structure : structures) {
			structure.setLinked(getNearbyStructure(structure));
		}
	}

	public void addStructure(Structure structure) {
		this.structures.add(structure);
	}

	public void removeStructure(Structure structure) {
		this.structures.remove(structures);
	}

	public ArrayList<Structure> getNearbyStructure(Structure structure) {
		ArrayList<Structure> structures = new ArrayList<>();

		for (Structure s : this.structures) {
			if (structure.getLocation().distance(s.getLocation()) < 20) {
				if (structure.getArmorStand().getName().equalsIgnoreCase("$$**STRUCTURE_TRANS**$$") && s.getArmorStand().getName().equalsIgnoreCase("$$**STRUCTURE_RECEIVE**$$")) {
					structures.add(s);
					s.addLinked(structure);
				} else if (structure.getArmorStand().getName().equalsIgnoreCase("$$**STRUCTURE_RECEIVE**$$") && s.getArmorStand().getName().equalsIgnoreCase("$$**STRUCTURE_TRANS**$$")) {
					structures.add(s);
					s.addLinked(structure);
				}
			}
		}

		return structures;
	}

	public ItemStack getTransItemstack() {
		return this.transItemstack;
	}

	public ItemStack getReceiveItemstack() {
		return this.receiveItemstack;
	}

	public ArrayList<Structure> getStructures() {
		return this.structures;
	}
}
