package me.sofdev.bases.menu;

import me.sofdev.bases.BasesPlugin;
import me.sofdev.bases.listeners.builds.BaseListener;
import me.sofdev.bases.manager.build.Base;
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

public class BaseMenu {

    public static MessageManager messages = new MessageManager();
    public static Base base = BaseListener.base;

    public static void Color(Player player) {
        String[] setup = {
                "b m u g b",
                "b p c r b",
                "b o k y b"
        };

        ItemStack blank = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta blankMeta = blank.getItemMeta();
        blankMeta.setDisplayName(CC.translate("&7 "));
        blankMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
        blankMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        blank.setItemMeta(blankMeta);

        ItemStack lightblue = new ItemStack(Material.STAINED_GLASS, 1, (short) 3);
        ItemStack pink = new ItemStack(Material.STAINED_GLASS, 1, (short) 6);
        ItemStack red = new ItemStack(Material.STAINED_GLASS, 1, (short) 14);
        ItemStack brown = new ItemStack(Material.STAINED_GLASS, 1, (short) 12);
        ItemStack green = new ItemStack(Material.STAINED_GLASS, 1, (short) 5);
        ItemStack yellow = new ItemStack(Material.STAINED_GLASS, 1, (short) 4);
        ItemStack blue = new ItemStack(Material.STAINED_GLASS, 1, (short) 11);

        ItemStack prismarine = new ItemStack(Material.PRISMARINE);
        ItemStack sealantern = new ItemStack(Material.SEA_LANTERN);

        InventoryGui inventoryGui = new InventoryGui(BasesPlugin.get(),
                CC.translate("&cChoose base color."), setup);

        inventoryGui.addElement(new StaticGuiElement('p', pink, click -> {
            base.build(player, Material.STAINED_GLASS, (byte) 6, Material.WOOL, (byte) 6);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('u', prismarine, click -> {
            base.build(player, Material.STAINED_GLASS, (byte) 9, Material.PRISMARINE, (byte) 0);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('k', sealantern, click -> {
            base.build(player, Material.STAINED_GLASS, (byte) 9, Material.SEA_LANTERN, (byte) 0);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('n', prismarine, click -> {
            base.build(player, Material.STAINED_GLASS, (byte) 9, Material.PRISMARINE, (byte) 0);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('c', lightblue, click -> {
            base.build(player, Material.STAINED_GLASS, (byte) 3, Material.WOOL, (byte) 3);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('r', red, click -> {
            base.build(player, Material.STAINED_GLASS, (byte) 14, Material.WOOL, (byte) 14);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('m', brown, click -> {
            base.build(player, Material.STAINED_GLASS, (byte) 12, Material.WOOL, (byte) 12);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('g', green, click -> {
            base.build(player, Material.STAINED_GLASS, (byte) 5, Material.WOOL, (byte) 5);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('y', yellow, click -> {
            base.build(player, Material.STAINED_GLASS, (byte) 4, Material.WOOL, (byte) 4);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('o', blue, click -> {
            base.build(player, Material.STAINED_GLASS, (byte) 11, Material.WOOL, (byte) 11);
            player.sendMessage(messages.BaseProcessStarted());
            inventoryGui.close();
            return true;
        }));

        inventoryGui.addElement(new StaticGuiElement('b', blank, click -> {
            return true;
        }));

        inventoryGui.show(player);
    }
}
