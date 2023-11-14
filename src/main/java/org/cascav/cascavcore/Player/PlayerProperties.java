package org.cascav.cascavcore.Player;

import org.cascav.cascavcore.Enums;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerProperties
{
    //PlayerProperties is a class stored in redis that contains all information a player should have.
    //These values are defaults; They can be modified later.
    //All of these are backed up in the database.

    //player
    public UUID PlayerUUID = null;

    // Region: Properties
        //Ranks and titles
        public Enums.ModRanks modRank = Enums.ModRanks.NONE;
        public ArrayList<Enums.SpecialRanks> specialRanks = new ArrayList<>();
        public String Nickname = null;

        public String getRanksAsString()
        {
            String ranks = "";
            ranks += Utils.getModRankAsString(modRank);
            ranks += Utils.getSpecialRanksAsString(specialRanks);
            return ranks;
        }

        public Currency PlayerCurrency = new Currency();


    // Region: Punishments
        //All Player Punishments
        public ArrayList<Punishment> PunishmentLog = new ArrayList<>();
        //Current Punishments
        private boolean muted = false;
        public boolean isMuted()
        {
            if(!muted) return false;
            for(Punishment p : PunishmentLog){
                if(p.type == Enums.PunishmentType.MUTE && p.ends.isAfter(LocalDateTime.now()))
                {
                    return true;
                }
            }
            return false;
        }
        public void Mute(String moderator, String duration, String reason)
        {
            LocalDateTime end = Utils.ParseDuration(duration);
            Punishment p = new Punishment(Enums.PunishmentType.MUTE, PlayerUUID, moderator, LocalDateTime.now(), end, reason);
            PunishmentLog.add(p);
            Utils.UpdateAllPunishments(PlayerUUID, PunishmentLog);
            muted = true;
        }
        public void unMute()
        {
            muted = false;
            for(Punishment p : PunishmentLog)
            {
                if(p.type == Enums.PunishmentType.MUTE)
                {
                    p.ends = LocalDateTime.now();
                }
            }
            Utils.UpdateAllPunishments(PlayerUUID, PunishmentLog);
        }

        public boolean tempbanned = false;
        public boolean permbanned = false;
        public boolean isBanned()
        {
            if(permbanned) return true;
            if(!tempbanned) return false;
            for(Punishment p : PunishmentLog)
            {
                if(p.type == Enums.PunishmentType.TEMPBAN && p.ends.isAfter(LocalDateTime.now()))
                {
                    return true;
                }
            }
            return false;
        }
        public void tempBan(String moderator, String duration, String reason)
        {
            LocalDateTime end = Utils.ParseDuration(duration);
            Punishment p = new Punishment(Enums.PunishmentType.TEMPBAN, PlayerUUID, moderator, LocalDateTime.now(), end, reason);
            PunishmentLog.add(p);
            Utils.UpdateAllPunishments(PlayerUUID, PunishmentLog);
            tempbanned = true;
        }
        public void permBan(String moderator, String reason)
        {
            LocalDateTime end = LocalDateTime.of(99999999, 1, 1, 0, 0, 0);
            Punishment p = new Punishment(Enums.PunishmentType.PERMBAN, PlayerUUID, moderator, LocalDateTime.now(), end, reason);
            PunishmentLog.add(p);
            Utils.UpdateAllPunishments(PlayerUUID, PunishmentLog);
            permbanned = true;
        }
        public void pardonBan()
        {
            permbanned = false; tempbanned = false;
            for(Punishment p : PunishmentLog)
            {
                if(p.type == Enums.PunishmentType.TEMPBAN || p.type == Enums.PunishmentType.PERMBAN)
                {
                    p.ends = LocalDateTime.now();
                }
            }
            Utils.UpdateAllPunishments(PlayerUUID, PunishmentLog);
        }
        public void logKick(String moderator, String reason)
        {
            LocalDateTime end = LocalDateTime.now();
            Punishment p = new Punishment(Enums.PunishmentType.KICK, PlayerUUID, moderator, LocalDateTime.now(), end, reason);
            PunishmentLog.add(p);
            Utils.UpdateAllPunishments(PlayerUUID, PunishmentLog);
        }

        public class Punishment
        {
            public Punishment(Enums.PunishmentType itype, UUID iplayerID, String imoderator, LocalDateTime itimestamp, LocalDateTime iends, String ireason) {
                type = itype;
                playerID = iplayerID;
                moderator = imoderator;
                timestamp = itimestamp;
                ends = iends;
                reason = ireason;
            }

            public Enums.PunishmentType type;
            public UUID playerID;
            public String moderator;
            public LocalDateTime timestamp;
            public LocalDateTime ends;
            public String reason;
        }
}
