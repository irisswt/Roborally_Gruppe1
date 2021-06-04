package dk.dtu.compute.se.pisd.roborally.model;

public enum Damage{
    SPAM("Play the top card of your programming deck this register", 1),
    WORM("Immediately reboot your robot", 2),
    TROJANHORSE("Immediately take 2 SPAM. Play the top card of your programming deck this register", 3),
    VIRUS("Robots within a 6 space radius of your robot immediately take 1 SPAM. Play the top card of your programming deck this register", 4);

    final public String displayName;
    final public int value;

    Damage(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }
}
// Isak was here
