package io.github.devvydoo.ascended.util.config;

/**
 * Basically just a static class with some global settings
 *
 * TODO: Make all of this in a database or json or something
 */
public final class ClaimConfig {

    // Don't let this class be instantiated
    private ClaimConfig() { throw new UnsupportedOperationException(); }

    public final static long STATUS_TEXT_CHANNEL_ID = 557395184577544222L;
    public final static long COMMANDS_TEXT_CHANNEL_ID = 557395184577544222L;

}
