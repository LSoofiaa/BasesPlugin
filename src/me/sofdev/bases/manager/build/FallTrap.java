package me.sofdev.bases.manager.build;

import me.sofdev.bases.*;
import me.sofdev.bases.manager.messages.*;
import me.sofdev.bases.utils.chat.*;
import me.sofdev.bases.utils.cuboid.falltrap.*;
import org.apache.commons.lang3.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;

import java.util.*;

@SuppressWarnings("deprecation")
public class FallTrap {

    Location position1 = null;
    Location position2 = null;
    public static Sound sound = Sound.DIG_STONE;
    public ArrayList<UUID> FallConfirmed = new ArrayList<UUID>();
    public MessageManager messages = new MessageManager();
    public ArrayList<Player > building = new ArrayList<>();

    String UNICODE_CAUTION = StringEscapeUtils.unescapeJava("\u26a0");
    String UNICODE_ARROW_LEFT = StringEscapeUtils.unescapeJava("\u25C0");
    String UNICODE_ARROW_RIGHT = StringEscapeUtils.unescapeJava("\u25B6");
    String UNICODE_HEART = StringEscapeUtils.unescapeJava("\u2764");
    String UNICODE_ARROWS_LEFT = StringEscapeUtils.unescapeJava("\u00AB");

    public ArrayList<Player> getBuilding() {
        return building;
    }

    public void setPos1(Location location) {
        position1 = location;
    }

    public void setPos2(Location location) {
        position2 = location;
    }

    public Location getPos1() {
        return position1;
    }

    public Location getPos2() {
        return position2;
    }

    public void build(Location position1, Location position2, Player player, Material walls, byte wallsData) {
        Cuboid cuboid = new Cuboid(getPos2(), getPos1());
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
                player.getLocation().getY() - 0.22  ,
                cuboid.getCenter().getBlockZ());
        ArmorStand entity2 = (ArmorStand) player.getWorld().spawnEntity(skeLoc2, EntityType.ARMOR_STAND);

        entity2.setVisible(false);
        entity2.setCustomNameVisible(true);
        entity2.setGravity(false);

        int TR = 0;
        long TPB = BasesPlugin.get().getConfig().getInt("DEFAULT_OPTIONS.FALLTRAP_TICKS");
        int FallVolume = cuboid.getVolume();

        int volumex = FallVolume;

        int current = 0;

        cuboid.getBlocks().removeIf(block -> block.getType() == Material.AIR);

        for (Block block : cuboid) {

            int finalCurrent = current;
            int finalVolumex = volumex;
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.AIR) {
                        block.setType(Material.AIR);
                        block.getWorld().playEffect(block.getLocation().clone().add(0.5, 0, 0.5), Effect.TILE_DUST, 10);

                        entity.setCustomName(CC.translate(
                                "&9FallTrap &7"
                                        + UNICODE_ARROW_RIGHT + " &8["
                                        + messages.getProgressBar(finalCurrent, FallVolume, 10, '⬛', ChatColor.GREEN, ChatColor.GRAY) + "&8]"));

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

        fillBorders(cuboid, walls, wallsData, player);
    }

    public void fillBorders(Cuboid cuboid, Material material, byte data, Player player) {
        int TR = 0;
        long TPB = BasesPlugin.get().getConfig().getInt("DEFAULT_OPTIONS.FALLTRAP_TICKS");
        
        int playerY = player.getLocation().getBlockY();
        
        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.South).expand(Cuboid.CuboidDirection.South, 1)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.SNOW) {

                        if (cuboid.getBlocks().contains(block)) {
                            return;
                        }
                        
                        if (block.getLocation().getBlockY() == playerY) {
                            return;
                        }

                        block.setType(material);
                        block.setData(data);
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }


        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.East).expand(Cuboid.CuboidDirection.East, 1)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.SNOW) {

                        if (cuboid.getBlocks().contains(block)) {
                            return;
                        }

                        if (block.getLocation().getBlockY() == playerY) {
                            return;
                        }

                        block.setType(material);
                        block.setData(data);
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }

        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.North).expand(Cuboid.CuboidDirection.North, 1)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.SNOW) {

                        if (cuboid.getBlocks().contains(block)) {
                            return;
                        }

                        if (block.getLocation().getBlockY() == playerY) {
                            return;
                        }

                        block.setType(material);
                        block.setData(data);
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }

        for (Block block : cuboid.getFace(Cuboid.CuboidDirection.West).expand(Cuboid.CuboidDirection.West, 1)) {
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.CHEST
                            && block.getType() != Material.TRAPPED_CHEST
                            && block.getType() != Material.HOPPER
                            && block.getType() != Material.ANVIL
                            && block.getType() != Material.SNOW) {

                        if (cuboid.getBlocks().contains(block)) {
                            return;
                        }

                        if (block.getLocation().getBlockY() == playerY) {
                            return;
                        }

                        block.setType(material);
                        block.setData(data);
                    }
                }
            }, (long) TR * TPB);
            TR++;
        }
    }
}
