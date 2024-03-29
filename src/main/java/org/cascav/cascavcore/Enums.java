package org.cascav.cascavcore;

public class Enums {
    public enum GamemodeEnum {
        SPLEEF,
        BRIDGE,
        SUMO,
        ROD_PVP,
        UHC
    }

    public enum GamemodeVariationEnum {
        NORMAL,
        RANKED,
        PRIVATE
    }

    public enum PunishmentType { PERMBAN, TEMPBAN, KICK, MUTE }
    public enum ModRanks { OWNER, ADMIN, MODERATOR, OBSERVER, NONE }
    public enum SpecialRanks { EARLYSUPPORTER, PARTNER }
}
