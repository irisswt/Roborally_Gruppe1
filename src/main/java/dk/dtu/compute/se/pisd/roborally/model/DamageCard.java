package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import org.jetbrains.annotations.NotNull;

public class DamageCard{

    final public Damage damage;

    /**
     * Constructor for DamageCard. Also known in-game as a programming card.
     * @param damage The command itself for the card.
     */
    public DamageCard(@NotNull Damage damage) {
        this.damage = damage;
    }

    /**
     * Constructor for CommandCard. Also known in-game as a programming card.
     *
     * @param command The command itself for the card.
     */


    /**
     * Get method for the name of the Damage card.
     * @return the card name.
     */
    public String getName() {
        return damage.displayName;
    }


}