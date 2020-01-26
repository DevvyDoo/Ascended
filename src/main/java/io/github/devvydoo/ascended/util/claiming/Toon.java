package io.github.devvydoo.ascended.util.claiming;

import io.github.devvydoo.ascended.util.exceptions.ToonAlreadyClaimedException;
import io.github.devvydoo.ascended.util.exceptions.ToonNotClaimedException;
import net.dv8tion.jda.api.entities.Member;

/**
 * A helper class used for the toon claimer tracker
 */
public class Toon {

    private String name;  // The name of the toon to display on the tracker
    private Group group;  // The group the toon belongs to
    private Member owner;  // The discord member that owns this toon
    private boolean claimed;  // Whether or not the toon is claimed
    private long timeClaimed;  // The System timestamp the toon was claimed at

    /**
     * Instantiates a claimable toon object
     *
     * @param name The name of the toon
     * @param group The group the toon belongs to
     */
    public Toon(String name, Group group) {
        this.name = name;
        this.group = group;
        this.owner = null;
        this.claimed = false;
        this.timeClaimed = 0;
    }

    /**
     * Get the name of the toon
     *
     * @return The name of the toon as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Get the Group object that this toon belongs to
     *
     * @return The group that the toon belongs to
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Retrieve the Member object that claimed this toon
     *
     * @return The Member object that owns this toon
     */
    public Member getOwner() {
        return owner;
    }

    /**
     * Set a new owner of this toon when claiming
     *
     * @param owner The Member object that now owns this toon
     */
    public void setOwner(Member owner) {
        this.owner = owner;
    }

    /**
     * Check whether or not this toon is currently claimed
     *
     * @return a boolean representing the claim status of the toon
     */
    public boolean isClaimed() {
        return claimed;
    }

    /**
     * Marks this toon as claimed
     *
     * @param claimed Whether or not to mark this toon as claimed
     */
    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    /**
     * Returns the System time stamp that this toon was claimed
     *
     * @return The System timestamp that this toon was claimed at
     */
    public long getTimeClaimed() {
        return timeClaimed;
    }

    /**
     * Set the time that the toon was claimed
     *
     * @param timeClaimed The System timestamp the toon was claimed at
     */
    public void setTimeClaimed(long timeClaimed) {
        this.timeClaimed = timeClaimed;
    }

    /**
     * Claims this toon, setting a Discord Member as its owner
     *
     * @param owner The Discord Member that now owns this toon
     * @throws ToonAlreadyClaimedException Thrown when the toon is already claimed
     */
    public void claim(Member owner) throws ToonAlreadyClaimedException {
        if (isClaimed())
            throw new ToonAlreadyClaimedException(this);
        setOwner(owner);
        setTimeClaimed(System.currentTimeMillis());
        setClaimed(true);
    }

    /**
     * Unclaims this Toon
     *
     * @throws ToonNotClaimedException Thrown when the toon isn't claimed
     */
    public void unclaim() throws ToonNotClaimedException {
        if (!isClaimed())
            throw new ToonNotClaimedException(this);
        forceUnclaim();
    }

    /**
     * Forces the toon to be unclaimed whether or not they already are, simply bypasses a check needed for command usage
     */
    public void forceUnclaim(){
        setOwner(null);
        setTimeClaimed(0);
        setClaimed(false);
    }

    @Override
    public String toString() {
        return "Toon{" +
                "name='" + name + '\'' +
                '}';
    }
}
