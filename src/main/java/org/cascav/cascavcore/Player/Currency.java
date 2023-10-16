package org.cascav.cascavcore.Player;

import java.util.ArrayList;

public class Currency
{
    public ArrayList<Purchase> PurchaseLog = new ArrayList<>();

    int cascoins = 0;

    public boolean LogPurchase(String i, String time, int c)
    {
        int before = cascoins;
        cascoins -= c;
        if(cascoins < 0) return false;
        int after = cascoins;
        PurchaseLog.add(new Purchase(i, time, c, before, after));
        return true;
    }

    public class Purchase
    {
        public Purchase(String i, String time, int c, int b, int a)
        {
            item = i; timestamp = time; cost = c; before = b; after = a;
        }
        public final String item;
        public final String timestamp;
        public final int cost;
        public final int before;
        public final int after;
    }
}
