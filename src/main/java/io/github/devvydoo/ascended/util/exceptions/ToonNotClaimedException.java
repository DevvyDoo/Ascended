package io.github.devvydoo.ascended.util.exceptions;

import io.github.devvydoo.ascended.util.claiming.Toon;

public class ToonNotClaimedException extends Exception {

    private Toon toon;  // The Toon involved in this exception

    /**
     * Constructs the Exception with a reference to the Toon involved with the error
     *
     * @param toon The Toon responsible for throwing this exception
     */
    public ToonNotClaimedException(Toon toon) {
        this.toon = toon;
    }

    /**
     * Get the Toon responsible for causing this exception
     *
     * @return The Toon object that caused this exception
     */
    public Toon getToon(){
        return toon;
    }

}
