package me.sofdev.bases.manager.messages;

import com.google.common.base.*;
import me.sofdev.bases.utils.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;

public class MessageManager {

    /**
     * MISC
     */

    public String ClickToMount() {
        return CC.translate("&CLeft-Click me &7(Hit to despawn)");
    }

    public String MiniBuildersName() {
        return CC.translate("&CMini Builder &7(Hit to despawn)");
    }

    public String NoPermission() {
        return CC.translate("  &4\u2502 &cThat command was not found.");
    }

    public String CantOnWarzone() {
        return CC.translate("  &4\u2502 &cYou can't use this command on &4Warzone&c.");
    }

    public String GetLower() {
        return CC.translate("  &4\u2502 &CYou can't execute this command in this height!");
    }

    public String RevokedCooldowns(Player target) {
        return CC.translate("  &4\u2502 &CSuccessfully revoked cooldown to &4" + target.getName());
    }

    public String RevokedAllCooldowns() {
        return CC.translate("  &4\u2502 &CSuccessfully revoked all cooldowns.");
    }

    public String NotOnline(Player player) {
        return CC.translate("  &4\u2502 &CThe player &4" + player.getName() + " &cis not online.");
    }

    public String StraightLine() {
        return CC.translate("&d&m---*----*----*----*----*----*---");
    }

    /**
     * POSITIONS
     */

    public String BreakPos2() {
        return CC.translate("  &4\u2502 &bBreak a block to set position 2.");
    }

    public String Position1(Location location) {
        return CC.translate("  &4\u2502 &bYou have set the location 1 to: &f(" + location.getX() + ", " + location.getZ() + ")");
    }

    public String Position2(Location location) {
        return CC.translate("  &4\u2502 &bYou have set the location 2 to: &f(" + location.getX() + ", " + location.getZ() + ")");
    }

    /**
     * BASES
     */

    public String BaseProcessStarted() {
        return CC.translate("  &4\u2502 &bYour base process has started.");
    }

    public List<String> BaseModeEnabled() {
        List<String> stringList = new ArrayList<>();

        stringList.add(StraightLine());
        stringList.add(CC.translate("&c\u2502 &cBase mode has been enabled"));
        stringList.add(CC.translate("&c\u2502 &cBreak a block to start."));
        stringList.add(StraightLine());

        return stringList;
    }

    public String BasePlaced() {
        return CC.translate("  &4\u2502 &CYour already placed base.");
    }

    public String BaseConfirmExpired() {
        return CC.translate("  &4\u2502 &cYour time to confirm has expired.");
    }

    public String BaseMessageToConfirm() {
        return "Confirm-Base";
    }

    public String BaseMessageConfirm() {
        return CC.translate("  &4\u2502 &cYou have 30 seconds to confirm by typing: \"&f" + BaseMessageToConfirm() + "&c\"");
    }

    /**
     * FALLTRAP
     */

    public String FallPlaced() {
        return CC.translate("  &4\u2502 &CYour already placed falltrap.");
    }

    public String FallTrapProcessStarted() {
        return CC.translate("  &4\u2502 &bYour falltrap process has started.");
    }

    public String FallConfirmExpired() {
        return CC.translate("  &4\u2502 &cYour time to confirm falltrap has expired");
    }

    public String FallMessageToConfirm() {
        return "Confirm-FallTrap";
    }

    public String FallMessageConfirm() {
        return CC.translate("  &4\u2502 &cYou have 30 seconds to confirm by typing: \"&f" + FallMessageToConfirm() + "&c\"");
    }

    public List<String> FallModeEnabled() {
        List<String> stringList = new ArrayList<>();
        stringList.add(StraightLine());
        stringList.add(CC.translate("&c\u2502 &cFallTrap mode has been enabled"));
        stringList.add(CC.translate("&c\u2502 &cBreak a block to start."));
        stringList.add(StraightLine());

        return stringList;
    }

    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }
}
