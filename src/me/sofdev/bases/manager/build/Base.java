package me.sofdev.bases.manager.build;

import me.sofdev.bases.*;
import me.sofdev.bases.manager.messages.*;
import me.sofdev.bases.manager.util.*;
import me.sofdev.bases.utils.chat.*;
import me.sofdev.bases.utils.cuboid.base.*;
import org.apache.commons.lang3.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;

import java.util.*;

@SuppressWarnings("deprecation")
public class Base {

    public static Sound sound = Sound.STEP_WOOL;
    public MessageManager messages = new MessageManager();
    public ArrayList<UUID> BaseConfirmed = new ArrayList<UUID>();
    public UserManager userManager = new UserManager();
    public Location pos1 = null;
    public Location pos2 = null;
    public Player baseOwner = null;
    public ArrayList<Player> building = new ArrayList<>();
    String UNICODE_CAUTION = StringEscapeUtils.unescapeJava("\u26a0");
    String UNICODE_ARROW_LEFT = StringEscapeUtils.unescapeJava("\u25C0");
    String UNICODE_ARROW_RIGHT = StringEscapeUtils.unescapeJava("\u25B6");
    String UNICODE_HEART = StringEscapeUtils.unescapeJava("\u2764");
    String UNICODE_ARROWS_LEFT = StringEscapeUtils.unescapeJava("\u00AB");

    public ArrayList<Player> getBuilding() {
        return building;
    }

    public void setBaseOwner(Player player) {
        baseOwner = player;
    }

    public Player getBaseOwner() {
        return baseOwner;
    }

    public void setPos1(Location location) {
        pos1 = location;
    }

    public void setPos2(Location location) {
        pos2 = location;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public boolean isBothPositions() {
        return pos1 != null && pos2 != null;
    }

    public void build(Player player, Material material, byte data, Material floor, byte bordersData) {
        userManager.SetBasePlaced(player, true);
        Cuboid cuboid = new Cuboid(pos1, pos2);
        setBaseOwner(player);
        building.add(player);

        Location skeLoc = new Location(
                player.getWorld(),
                cuboid.getCenter().getX(),
                player.getLocation().getY(),
                cuboid.getCenter().getBlockZ());
        ArmorStand entity = (ArmorStand) player.getWorld().spawnEntity(skeLoc, EntityType.ARMOR_STAND);

        entity.setVisible(false);
        entity.setCustomNameVisible(true);
        entity.setGravity(false);

        Location skeLoc2 = new Location(
                player.getWorld(),
                cuboid.getCenter().getX(),
                player.getLocation().getY() - 0.22,
                cuboid.getCenter().getBlockZ());
        ArmorStand entity2 = (ArmorStand) player.getWorld().spawnEntity(skeLoc2, EntityType.ARMOR_STAND);

        entity2.setVisible(false);
        entity2.setCustomNameVisible(true);
        entity2.setGravity(false);

        int TR = 0;
        long TPB = BasesPlugin.get().getConfig().getInt("DEFAULT_OPTIONS.BASE_TICKS");
        Cuboid faceNorth = cuboid.getFace(Cuboid.CuboidDirection.North);
        Cuboid faceSouth = cuboid.getFace(Cuboid.CuboidDirection.South);
        Cuboid faceEast = cuboid.getFace(Cuboid.CuboidDirection.East);
        Cuboid faceWest = cuboid.getFace(Cuboid.CuboidDirection.West);
        Cuboid faceUp = cuboid.getFace(Cuboid.CuboidDirection.Up);
        Cuboid faceDown = cuboid.getFace(Cuboid.CuboidDirection.Down);
        int volume =
                faceNorth.getVolume() +
                        faceSouth.getVolume() +
                        faceEast.getVolume() +
                        faceWest.getVolume() +
                        faceUp.getVolume() +
                faceDown.getVolume();
        int volumex = volume;

        int current = 0;

        for (Block block : cuboid.getFaces()) {
            int finalCurrent = current;
            int finalVolumex = volumex;
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.BEDROCK
                            && block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.WOOL
                            && block.getType() != Material.PRISMARINE
                            && block.getType() != Material.SEA_LANTERN) {

                        if (block.getLocation().getBlockZ() == getPos2().getBlockZ()
                                && block.getLocation().getBlockX() == getPos2().getBlockX()) {
                            return;
                        }

                        block.setType(material);
                        block.setData(data);

                        if (block.getLocation().getBlockY() == getPos1().getBlockY()) {
                            block.setType(floor);
                            block.setData(data);
                        }

                        block.getWorld().playEffect(block.getLocation().clone().add(0.5, 0, 0.5), Effect.TILE_DUST, 10);

                        entity.setCustomName(CC.translate(
                                "&9Base &7"
                                        + UNICODE_ARROW_RIGHT + " &8["
                                        + messages.getProgressBar(finalCurrent, volume, 10, '⬛', ChatColor.GREEN, ChatColor.GRAY) + "&8]"));

                        entity2.setCustomName(CC.translate("&7" + UNICODE_ARROW_RIGHT + " &b(&f" + finalVolumex + "&b) &7" + UNICODE_ARROW_LEFT));

                        block.getWorld().playSound(block.getLocation(), Sound.STEP_STONE, 2, 1);

                        if (finalVolumex < 50) {
                            entity.getWorld().playEffect(entity.getLocation(), Effect.EXPLOSION_HUGE, 15, 15);
                            building.remove(player);
                            entity.remove();
                            entity2.remove();
                        }
                    }
                }
            }, (long) TR * TPB);
            TR++;
            current++;
            volumex--;
        }

        fillBorders(cuboid, floor, data, bordersData);
    }

    /**
     * @param cuboid   = the required cuboid
     * @param material = the material to fill
     * @param data     = the data to set for the block
     * @param bordersData = the data to set to the borders (Prismarine & sea lanterns)
     * @example Cuboid cuboid = new Cuboid(location1, location2);
     * @example byte data = (byte) DyeColor.BLACK.getData();
     * @example BaseBuilder.fillBorders(cuboid, Material.WOOL, data);
     */
    public static void fillBorders(Cuboid cuboid, Material material, byte data, byte bordersData) {
        // SOUTH
        int TR = 0;
        long TPB = BasesPlugin.get().getConfig().getInt("DEFAULT_OPTIONS.BASE_TICKS");
        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.South).getFace(Cuboid.CuboidDirection.Up)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.BEDROCK
                            && block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.WOOL) {

                        block.setType(material);
                        if (material == Material.PRISMARINE || material == Material.SEA_LANTERN) {
                            block.setData(bordersData);
                        } else {
                            block.setData(data);
                        }
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }

        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.South).getFace(Cuboid.CuboidDirection.East)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.BEDROCK
                            && block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.WOOL) {

                        block.setType(material);
                        if (material == Material.PRISMARINE || material == Material.SEA_LANTERN) {
                            block.setData(bordersData);
                        } else {
                            block.setData(data);
                        }
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }

        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.South).getFace(Cuboid.CuboidDirection.West)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.BEDROCK
                            && block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.WOOL) {
                        block.setType(material);
                        if (material == Material.PRISMARINE || material == Material.SEA_LANTERN) {
                            block.setData(bordersData);
                        } else {
                            block.setData(data);
                        }
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }

        // NORTH

        // SOUTH
        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.North).getFace(Cuboid.CuboidDirection.Up)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.BEDROCK
                            && block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.WOOL) {

                        block.setType(material);
                        if (material == Material.PRISMARINE || material == Material.SEA_LANTERN) {
                            block.setData(bordersData);
                        } else {
                            block.setData(data);
                        }
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }

        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.North).getFace(Cuboid.CuboidDirection.East)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.BEDROCK
                            && block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.WOOL) {

                        block.setType(material);
                        if (material == Material.PRISMARINE || material == Material.SEA_LANTERN) {
                            block.setData(bordersData);
                        } else {
                            block.setData(data);
                        }
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }

        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.North).getFace(Cuboid.CuboidDirection.West)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.BEDROCK
                            && block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.WOOL) {

                        block.setType(material);
                        if (material == Material.PRISMARINE || material == Material.SEA_LANTERN) {
                            block.setData(bordersData);
                        } else {
                            block.setData(data);
                        }
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }

        // EAST & WEST (UP ONLY

        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.East).getFace(Cuboid.CuboidDirection.Up)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.BEDROCK
                            && block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.WOOL) {

                        block.setType(material);
                        if (material == Material.PRISMARINE || material == Material.SEA_LANTERN) {
                            block.setData(bordersData);
                        } else {
                            block.setData(data);
                        }
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }

        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.West).getFace(Cuboid.CuboidDirection.Up)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.BEDROCK
                            && block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.WOOL) {

                        block.setType(material);
                        if (material == Material.PRISMARINE || material == Material.SEA_LANTERN) {
                            block.setData(bordersData);
                        } else {
                            block.setData(data);
                        }
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }
    }
}
