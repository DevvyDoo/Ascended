package io.github.devvydoo.ascended.util.claiming;

import java.util.HashSet;
import java.util.Set;

/**
 * A helper class that aggregates Toon objects. Makes grouping sets of quads / mini clique's toons easier
 */
public class Group {

    private String groupID;  // The ID of the group of toons ex. 3x or 3y or 3z
    private HashSet<Toon> toons;  // A non duplicate list of Toon objects that are members of this group

    /**
     * Constructs a Group object with an empty roster of Toons
     *
     * @param groupID The ID to instantiate this group with. Think of this as a unique identifier
     */
    public Group(String groupID){
        this.groupID = groupID;
        toons = new HashSet<>();
    }

    /**
     * Constructs a Group object with an existing roster of Toon objects
     *
     * @param groupID The ID to instantiate this group with. Think of this as a unique identifier
     * @param toons The Toon objects to add to the roster of this group
     */
    public Group(String groupID, Toon... toons){
        this(groupID);
        addToons(toons);
    }

    /**
     * Gets this group's unique ID
     *
     * @return A String ID
     */
    public String getGroupID() {
        return groupID;
    }

    /**
     * Gets a list of this Group's Toon roster
     *
     * @return A Set of Toon objects
     */
    public Set<Toon> getToons() {
        return toons;
    }

    /**
     * Add a Toon object to this Group's roster
     *
     * @param toon The Toon object to add. Duplicate Toons will throw an exception.
     */
    public void addToon(Toon toon){
        toons.add(toon);
    }

    /**
     * Add multiple toons to this Group's roster
     *
     * @param toons The Toon objects to add
     */
    public void addToons(Toon... toons){
        // Loop through the Toon arguments and attempt to add them
        for (Toon t: toons)
            addToon(t);
    }

    /**
     * Attempts to remove a toon from this Group's roster
     *
     * @param toon The Toon to remove
     * @return whether or not the Toon was removed successfully or not
     */
    public boolean removeToon(Toon toon){
        return toons.remove(toon);
    }

}
