package org.cascav.cascavcore.Player;

import java.util.List;

public class Ranks
{
    //TBA-paid ranks? Titles?
    public enum ModRanks { OWNER, ADMIN, MODERATOR, OBSERVER, NONE }
    public enum SpecialRanks { EARLYSUPPORTER, PARTNER }
    public static String getModRankAsString(ModRanks modRank)
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
    public static String getSpecialRanksAsString(List<SpecialRanks> specialRanks)
    {
        String ranks = "";

        if(specialRanks.contains(SpecialRanks.EARLYSUPPORTER)) { ranks += "&6&l&o| &e&l&oEARLY SUPPORTER &6&l&o|"; }
        if(specialRanks.contains(SpecialRanks.PARTNER)) { ranks += "&2&l&o| &a&l&oPARTNER &2&l&o|"; }

        return ranks;
    }

}
