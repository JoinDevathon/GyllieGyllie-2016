package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.commands.TestCommand;
import org.devathon.contest2016.listener.BlockListener;
import org.devathon.contest2016.manager.StructureManager;

import java.util.List;

public class DevathonPlugin extends JavaPlugin {

    private static Plugin plugin;

    private static StructureManager structureManager;

    @Override
    public void onEnable() {

        plugin = this;

        getCommand("test").setExecutor(new TestCommand());

        this.structureManager = new StructureManager();
        this.structureManager.init();

        ShapedRecipe transRecipe = new ShapedRecipe(structureManager.getTransItemstack());
        transRecipe.shape("aba", "aca", "ada");
        transRecipe.setIngredient('a', Material.PURPUR_PILLAR);
        transRecipe.setIngredient('b', Material.END_ROD);
        transRecipe.setIngredient('c', Material.SEA_LANTERN);
        transRecipe.setIngredient('d', Material.REDSTONE_BLOCK);

        getServer().addRecipe(transRecipe);

        ShapedRecipe receiveRecipe = new ShapedRecipe(structureManager.getReceiveItemstack());
        receiveRecipe.shape("aba", "aca", "ada");
        receiveRecipe.setIngredient('a', Material.PURPUR_BLOCK);
        receiveRecipe.setIngredient('b', Material.END_ROD);
        receiveRecipe.setIngredient('c', Material.GOLD_BLOCK);
        receiveRecipe.setIngredient('d', Material.REDSTONE_BLOCK);

        getServer().addRecipe(receiveRecipe);

        getServer().getPluginManager().registerEvents(new BlockListener(), this);

        Bukkit.getLogger().info("Plugin succesfully loaded");
    }

    @Override
    public void onDisable() {
        // put your disable code here
    }

    public static Plugin getInstance() {
        return plugin;
    }

    public static StructureManager getStructureManager() {
        return structureManager;
    }

    public static ItemStack createItem(Material material, int amount, short data, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount, data);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}

