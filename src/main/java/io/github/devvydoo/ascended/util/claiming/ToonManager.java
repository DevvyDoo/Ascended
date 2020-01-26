package io.github.devvydoo.ascended.util.claiming;

import io.github.devvydoo.ascended.util.exceptions.InvalidGroupException;
import io.github.devvydoo.ascended.util.exceptions.InvalidToonException;
import io.github.devvydoo.ascended.util.exceptions.ToonNotClaimedException;
import net.dv8tion.jda.api.entities.Member;

import java.util.*;

/**
 * A central class that manages all the Groups and Toons that are registered
 */
public class ToonManager {

    // These maps will map Name/ID to the actual instance of the Toon/Group **NAMES/IDS MUST BE UNIQUE!!!!!!!
    private HashMap<String, Toon> toons;  // A map of all Toon objects that are currently registered
    private HashMap<String, Group> groups;  // A map of Group objects that are currently registered

    public ToonManager(){

        toons = new HashMap<>();
        groups = new HashMap<>();

        //TODO: Make it so that toons register based on an external and modifiable source (database) instead of this hard-coded mess
        Group _3x = new Group("3x");
        Group _3y = new Group("3y");
        Group _3z = new Group("3z");

        _3x.addToons(new Toon("Xalayna", _3x), new Toon("Xeno", _3x), new Toon("Xavier", _3x), new Toon("Stella", _3x));
        _3y.addToons(new Toon("Xeric", _3y), new Toon("Noxious", _3y), new Toon("Liament", _3y), new Toon("Xanthus", _3y));
        _3z.addToons(new Toon("Eria", _3z), new Toon("Glade", _3z), new Toon("Halcyon", _3z), new Toon("Furrtastic Kitteneer", _3z));

        // Put the groups in the hashmap
        groups.put(_3x.getGroupID().toLowerCase(), _3x);
        groups.put(_3y.getGroupID().toLowerCase(), _3y);
        groups.put(_3z.getGroupID().toLowerCase(), _3z);

        // Put all the toons in hashmap
        for (Group group: groups.values()){
            for (Toon toon: group.getToons()){
                toons.put(toon.getName().toLowerCase(), toon);
            }
        }

    }

    /**
     * Get a list of all registered toons
     *
     * @return A Collection of Toon objects that are currently registered
     */
    public Collection<Toon> getRegisteredToons() {
        return toons.values();
    }

    /**
     * Get a list of all registered Groups
     *
     * @return A Collection of Group objects that are currently registered
     */
    public Collection<Group> getRegisteredGroups() {
        return groups.values();
    }

    /**
     * Attempts to retrieve a Group object given its unique ID
     *
     * @param ID The ID to query for
     * @return a Group object
     * @throws InvalidGroupException Thrown when the group does not exist or is not registered
     */
    public Group getGroup(String ID) throws InvalidGroupException {
        if (groups.containsKey(ID.toLowerCase()))
            return groups.get(ID.toLowerCase());
        throw new InvalidGroupException();
    }

    /**
     * Attempts to retrieve a Toon object given its unique name
     *
     * @param name The name to query for
     * @return a Toon object
     * @throws InvalidToonException Thrown when the toon does not exist or is not registered
     */
    public Toon getToon(String name) throws InvalidToonException {
        if (toons.containsKey(name.toLowerCase()))
            return toons.get(name.toLowerCase());
        throw new InvalidToonException();
    }

    /**
     * Given a member and toon, attempt to unclaim it for them
     *
     * @param member The Member that is trying to unclaim a toon
     * @param toon The Toon that is being unclaimed
     * @throws ToonNotClaimedException Thrown when the toon is not claimed by the member
     */
    public void unclaimToon(Member member, Toon toon) throws ToonNotClaimedException {

        // Is the toon claimed by the member first of all
        if (toon.getOwner() == null || !toon.getOwner().equals(member))
            throw new ToonNotClaimedException(toon);

        toon.unclaim();
    }

    /**
     * Loops through all registered toons and unclaims them if the member parameter is the owner
     *
     * @param member The Member attempting to unclaim all toons
     */
    public void unclaimAllToons(Member member){
        for (Toon toon: toons.values()){
            if (toon.getOwner() != null && toon.getOwner().equals(member)){
                toon.forceUnclaim();
            }
        }
    }
}
