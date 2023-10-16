package org.cascav.cascavcore.Player;

public class PartyProperties
{
    //Party Properties
    private String partyID = null;
    public String GetParty() {return this.partyID;}
    public void JoinedParty(String ID) { this.partyID = ID; }
    public void LeftParty() { this.partyID = null; }
    public boolean IsPartyLeader = false;
    public boolean IsInParty() { return partyID != null; }
}
