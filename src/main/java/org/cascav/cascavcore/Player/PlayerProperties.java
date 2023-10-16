package org.cascav.cascavcore.Player;

import org.bukkit.entity.Player;
import org.cascav.cascavcore.Redis;
import org.redisson.api.RMap;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerProperties
{
    //PlayerProperties is a class stored in redis that contains all information a player should have.
    //These values are defaults; They can be modified later.
    //All of these are backed up in the database.+

    //player
    public UUID PlayerUUID = null;

    // Region: Properties
        //Ranks and titles
        public Ranks.ModRanks modRank = Ranks.ModRanks.NONE;
        public ArrayList<Ranks.SpecialRanks> specialRanks = new ArrayList<>();
        public String Nickname = null;

        public Currency PlayerCurrency = new Currency();



    //Static methods

    public static PlayerProperties GetFromPlayer(Player p)
    {
        RMap<UUID, PlayerProperties> AllPlayerProperties = Redis.GetAllPlayerProperties();
        UUID uuid = p.getUniqueId();
        if(AllPlayerProperties.containsKey(uuid)){ return AllPlayerProperties.get(uuid); }
        PlayerProperties prop = new PlayerProperties();
        prop.PlayerUUID = uuid;
        prop.Nickname = p.getName();
        AllPlayerProperties.put(uuid, prop);
        Update(uuid, prop);
        return prop;
    }

    public static void Update(UUID id, PlayerProperties props)
    {
        RMap<UUID, PlayerProperties> map = Redis.GetAllPlayerProperties();
        map.put(id, props);
        Redis.UpdatePlayerProperties(map);
    }
}
