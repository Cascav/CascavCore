package org.cascav.cascavcore;

import org.cascav.cascavcore.Player.PartyProperties;
import org.cascav.cascavcore.Player.PlayerProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.api.RMap;

import java.util.UUID;

public class Redis {
    private static final String node = "redis://redis-cascav:6379";
    private static final Config config = new Config();
    private static RedissonClient redisson;
    private static boolean initialized = false;
    public void Initialize()
    {
        if(initialized) return;
        initialized = true;
        config.useClusterServers().addNodeAddress(node);
        redisson = Redisson.create(config);
    }

    //Maps
    private static final String PlayerProperties = "PlayerProperties";
    private static final String PartyProperties = "PartyProperties";

    public static RMap<UUID, PlayerProperties> GetAllPlayerProperties() { return redisson.getMap(PlayerProperties); }
    public static void UpdatePlayerProperties(RMap<UUID, PlayerProperties> map) { redisson.getMap(PlayerProperties).putAll(map); }

    public static RMap<UUID, PartyProperties> GetAllPartyProperties() { return redisson.getMap(PartyProperties); }
    public static void UpdatePartyProperties(RMap<UUID, PartyProperties> map) { redisson.getMap(PartyProperties).putAll(map); }




}

