package me.sofdev.bases.utils.player;

import lombok.experimental.UtilityClass;
import me.sofdev.staff.utils.chat.CC;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@UtilityClass
public class Server {

    public final String SERVER_VERSION =
            Bukkit.getServer()
                    .getClass().getPackage()
                    .getName().split("\\.")[3]
                    .substring(1);

    public final int SERVER_VERSION_INT = Integer.parseInt(
            SERVER_VERSION
                    .replace("1_", "")
                    .replaceAll("_R\\d", ""));

    public String getIP() {
        URL url;
        BufferedReader in;
        String ipAddress = "";

        try {
            url = new URL("http://bot.whatismyipaddress.com");
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            ipAddress = in.readLine().trim();

            if (ipAddress.length() <= 0)
                try {
                    InetAddress ip = InetAddress.getLocalHost();
                    ipAddress = ip.getHostAddress().trim();
                } catch (Exception exp) {
                    ipAddress = "ERROR";
                }
        } catch (Exception ignored) {
        }

        return ipAddress;
    }

    public String getNiceTPS() {
        String TPS = "";
        double[] tps = MinecraftServer.getServer().recentTps;
        DecimalFormat format = new DecimalFormat("0.##");
        if (tps[1] > 16) {
            TPS = ChatColor.GREEN.toString() + format.format(tps[1]);
        } else if (tps[1] > 12) {
            TPS = ChatColor.GOLD.toString() + format.format(tps[1]);
        } else if (tps[1] < 12) {
            TPS = ChatColor.RED.toString() + format.format(tps[1]);
        }
        return TPS;
    }


    public String getDate(String dateFormat, String timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat.format(new Date());
    }

    public String getHour(String hourFormat, String timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(hourFormat);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat.format(new Date());
    }
}
