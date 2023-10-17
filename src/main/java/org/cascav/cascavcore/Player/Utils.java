package org.cascav.cascavcore.Player;

import org.bukkit.entity.Player;
import org.cascav.cascavcore.Redis;
import org.cascav.cascavcore.enums.Enums;
import org.redisson.api.RMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils
{
    public static String getModRankAsString(Enums.ModRanks modRank)
    {
        switch(modRank)
        {
            case NONE: return "";
            case OBSERVER: return "&3&l[ &b&l&oOBSERVER &3&l]";
            case MODERATOR: return "&4&l[ &c&l&oMODERATOR &4&l]";
            case ADMIN: return "&5&l[ &d&l&oADMIN &5&l]";
            case OWNER: return "&0&l<< &0&l&kilfsS &0&l&o&nOWNER &0&l&kSsfli &0&l>>";
        }
        return "";
    }
    public static String getSpecialRanksAsString(List<Enums.SpecialRanks> specialRanks)
    {
        String ranks = "";

        if(specialRanks.contains(Enums.SpecialRanks.EARLYSUPPORTER)) { ranks += "&6&l&o| &e&l&oEARLY SUPPORTER &6&l&o|"; }
        if(specialRanks.contains(Enums.SpecialRanks.PARTNER)) { ranks += "&2&l&o| &a&l&oPARTNER &2&l&o|"; }

        return ranks;
    }

    public static PlayerProperties GetFromPlayer(Player p)
    {
        RMap<UUID, PlayerProperties> AllPlayerProperties = Redis.GetAllPlayerProperties();
        UUID uuid = p.getUniqueId();
        if(AllPlayerProperties.containsKey(uuid)){ return AllPlayerProperties.get(uuid); }
        PlayerProperties prop = new PlayerProperties();
        prop.PlayerUUID = uuid;
        prop.Nickname = p.getName();
        AllPlayerProperties.put(uuid, prop);
        UpdatePlayerProperties(uuid, prop);
        return prop;
    }

    public static void UpdatePlayerProperties(UUID id, PlayerProperties props)
    {
        RMap<UUID, PlayerProperties> map = Redis.GetAllPlayerProperties();
        map.put(id, props);
        Redis.UpdatePlayerProperties(map);
    }
    public static void UpdateAllPunishments(UUID id, ArrayList<PlayerProperties.Punishment> puns)
    {
        RMap<UUID, ArrayList<PlayerProperties.Punishment>> map = Redis.GetAllPunishments();
        map.put(id, puns);
        Redis.UpdateAllPunishments(map);
    }

    public static LocalDateTime ParseDuration(String dur)
    {
        dur = dur.toLowerCase();
        String length = dur.replaceAll("[^0-9]", "");
        if(length.length() > 2) return null;
        int len = Integer.parseInt(length);
        String type = dur.replaceAll("[0-9]", "");
        switch (type)
        {
            case "min": case "mi": return LocalDateTime.now().plusMinutes(len);
            case "h": return LocalDateTime.now().plusHours(len);
            case "d": return LocalDateTime.now().plusDays(len);
            case "mon": case "mo": return LocalDateTime.now().plusMonths(len);
            case "y": return LocalDateTime.now().plusYears(len);
        }
        return null;
    }
}
