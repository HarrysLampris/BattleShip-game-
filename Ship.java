package practice;

import javafx.scene.Parent;

/**
 * Κάνει extend από την Parent κλάση, ώστε τα δεδομένα στην Ship
 * να είναι προσβάσιμα απευθείας από το Application file.
 **/
public class Ship extends Parent {
    
    public int type;    //Μήκος πλοίου.
    public boolean vertical = true; //Προσανατολισμός πλοίου (κάθετος/οριζόντιος).
    private int health;    //Η ζωή του πλοίου. (Αναλόγως το μήκος).

    /**
     * Στην μέθοδο Ship κατασκευάζονται τα πλοία.
     * Ορίζονται μήκος, κατεύθυνση και ζωή, 
     * αναλόγως ποιό πλοίο θα πατήσει ο χρήστης.
     **/
    public Ship(int type, boolean vertical) {
        this.type = type;           //Ορίζουμε το μήκος του πλοίου,
        this.vertical = vertical;   //και την κατεύθυνσή του.
        health = type;              //Η ζωή του πλοίου να είναι ίση με το μήκος του πλοίου.
    }
    
    /**
     * Μέθοδοι που καλούνται κάθε φορά που χτυπιέται πλοίο.
     * Αφαιρείται η ζωή κατα 1 κάθε φορά, (hit())
     * ελέγχεται επίσης εάν το πλοίο καταρρίφθηκε ολόκληρο. (isAlive())
     */
    public void hit() {
        health--;             //μειώνει την ζωή του πλοίου κατά 1.
    }
    
    public boolean isAlive() {
        return health > 0;    //Ελέγχει εάν το πλοίο είναι ακόμα ζωντανό με βάση την ζωή του.
    }
}