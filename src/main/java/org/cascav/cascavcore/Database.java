package org.cascav.cascavcore;

import org.bukkit.entity.Player;
import org.cascav.cascavcore.Player.PlayerProperties;
import org.redisson.api.RMap;

import jakarta.persistence.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Database
{
    private static Database instance;
    private EntityManagerFactory entityManagerFactory;

    private Database() {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        entityManagerFactory = Persistence.createEntityManagerFactory("cascav-database");
    }

    public void loadPlayerPropertiesToRedis() {
        RMap<UUID, PlayerProperties> Loaded = Redis.GetAllPlayerProperties();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery( "SELECT * FROM PlayerProperties p", PlayerProperties.class);
        List<PlayerProperties> results = query.getResultList();
        for(PlayerProperties p : results)
        {
            Loaded.put(p.PlayerUUID, p);
        }

        entityManager.getTransaction().commit();
        entityManager.close();

        Redis.UpdatePlayerProperties(Loaded);
    }
}
