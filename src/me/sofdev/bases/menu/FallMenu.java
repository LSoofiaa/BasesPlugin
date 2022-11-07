package me.sofdev.bases.menu;

import me.sofdev.bases.BasesPlugin;
import me.sofdev.bases.listeners.builds.FallTrapListener;
import me.sofdev.bases.manager.build.FallTrap;
import me.sofdev.bases.manager.messages.MessageManager;
import me.sofdev.bases.utils.chat.CC;
import me.sofdev.bases.utils.menus.InventoryGui;
import me.sofdev.bases.utils.menus.StaticGuiElement;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FallMenu {

    public static MessageManager messages = new MessageManager();

    public static void FallColor(Player player) {
        String[] setup = {
                "b qhilu b",
                "b vexgw b",
                "b njkmr b"
        };

        ItemStack blank = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta blankMeta = blank.getItemMeta();
        blankMeta.setDisplayName(CC.translate("&7 "));
        blankMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
        blankMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        blank.setItemMeta(blankMeta);

        FallTrap fall = FallTrapListener.Fall;

        ItemStack lightblue = new ItemStack(Material.STAINED_GLASS, 1, (short) 3);
        ItemStack pink = new ItemStack(Material.STAINED_GLASS, 1, (short) 6);
        ItemStack red = new ItemStack(Material.STAINED_GLASS, 1, (short) 14);
        ItemStack brown = new ItemStack(Material.STAINED_GLASS, 1, (short) 12);
        ItemStack green = new ItemStack(Material.STAINED_GLASS, 1, (short) 5);
        ItemStack yellow = new ItemStack(Material.STAINED_GLASS, 1, (short) 4);
        ItemStack blue = new ItemStack(Material.STAINED_GLASS, 1, (short) 11);
        ItemStack primarine = new ItemStack(Material.PRISMARINE);
        ItemStack primarineO = new ItemStack(Material.PRISMARINE, 1, (short) 2);
        ItemStack netherbrick = new ItemStack(Material.NETHER_BRICK);
        ItemStack sealantern = new ItemStack(Material.SEA_LANTERN);

        ItemStack endstone = new ItemStack(Material.ENDER_STONE);
        ItemStack pumpkin = new ItemStack(Material.PUMPKIN);
        ItemStack cuartz = new ItemStack(Material.QUARTZ_BLOCK);
        ItemStack jukebox = new ItemStack(Material.JUKEBOX);

        InventoryGui inventoryGui = new InventoryGui(BasesPlugin.get(),
                CC.translate("&CChoose falltrap color."), setup);

        inventoryGui.addElement(new StaticGuiElement('x', jukebox, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.JUKEBOX, (byte) 0);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('q', cuartz, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.QUARTZ_BLOCK, (byte) 0);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('w', pumpkin, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.PUMPKIN, (byte) 0);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('v', sealantern, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.SEA_LANTERN, (byte) 0);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('h', endstone, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.ENDER_STONE, (byte) 0);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('l', netherbrick, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.NETHER_BRICK, (byte) 0);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('i', primarineO, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.PRISMARINE, (byte) 2);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('u', primarine, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.PRISMARINE, (byte) 0);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('g', pink, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.WOOL, (byte) 6);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));
        inventoryGui.addElement(new StaticGuiElement('k', lightblue, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.WOOL, (byte) 3);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('r', red, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.WOOL, (byte) 14);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('m', brown, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.WOOL, (byte) 12);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('n', green, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.WOOL, (byte) 5);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('j', yellow, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.WOOL, (byte) 4);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('e', blue, click -> {
            fall.build(fall.getPos1(), fall.getPos2(), player, Material.WOOL, (byte) 11);
            player.sendMessage(messages.FallTrapProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('b', blank, click -> {
            return true;
        }));

        inventoryGui.show(player);
    }
}
