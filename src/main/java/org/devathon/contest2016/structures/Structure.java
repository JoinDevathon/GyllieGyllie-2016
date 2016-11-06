package org.devathon.contest2016.structures;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;

/**
 * Created by guillian on 06/11/2016.
 */
public interface Structure {

	void spawnStructure(ArmorStand armorStand);
	void destroy();
	Location getLocation();
	ArmorStand getArmorStand();
	void setLinked(ArrayList<Structure> linked);
	ArrayList<Structure> getLinked();
	void removeLinked(Structure structure);
	void addLinked(Structure structure);
}
