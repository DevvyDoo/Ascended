package io.github.devvydoo.ascended.cogs;

import io.github.devvydoo.ascended.util.claiming.Toon;
import io.github.devvydoo.ascended.util.claiming.ToonManager;
import io.github.devvydoo.ascended.util.exceptions.InvalidToonException;
import io.github.devvydoo.ascended.util.exceptions.ToonAlreadyClaimedException;
import io.github.devvydoo.ascended.util.exceptions.ToonNotClaimedException;
import sh.niall.yui.cogs.Cog;
import sh.niall.yui.commands.Context;
import sh.niall.yui.commands.interfaces.Command;
import sh.niall.yui.exceptions.CommandException;

/**
 * A class responsible for the entire Toon Claiming system put in place so that account sharers can communicate easily
 * to each other by literally seeing who's on what account
 */
public class ToonClaimer extends Cog {

    private ToonManager toonManager;  // A separate Manager class that handles a lot of the hard work for us

    /**
     * Upon cog creation, simply just instantiate a new ToonManager instance
     */
    public ToonClaimer(){
        toonManager = new ToonManager();
    }

    /**
     * Command used to claim a toon to let their teammates know that the user is logged on this toon
     *
     * @param ctx The context of the command
     * @throws CommandException Thrown when we are given an incorrect number of arguments
     * @throws ToonAlreadyClaimedException Thrown when the toon is already claimed and we cannot do anything for the user
     * @throws InvalidToonException Thrown when the Toon doesn't exist
     */
    @Command(name = "claim", aliases = {"c"})
    public void claimCommand(Context ctx) throws CommandException, ToonAlreadyClaimedException, InvalidToonException {

        // We need a toon name arg
        if (ctx.getArgs().size() < 2)
            throw new CommandException("Please provide a Toon name!");

        // Grab the toon object and attempt to claim it. The classes and error handler will handle all checking for us.
        Toon toon = toonManager.getToon(ctx.getArgs().get(1));
        toon.claim(ctx.getAuthor());

        // Success!
        ctx.send(String.format("%s Successfully claimed the toon `%s`!", ctx.getAuthor().getAsMention(), toon.getName()));

    }

    /**
     * A command used to unclaim one/all toons that a member may have claimed, again the classes handle all checking
     * and error handling for us so the command will look pretty simple
     *
     * @param ctx The context of the command
     * @throws CommandException Thrown when we aren't provided an arg
     * @throws ToonNotClaimedException Thrown when the Toon provided isn't claimed by the member
     * @throws InvalidToonException Thrown when the toon doesn't exist
     */
    @Command(name = "unclaim", aliases = {"u", "uc"})
    public void unclaimCommand(Context ctx) throws CommandException, ToonNotClaimedException, InvalidToonException {

        // We need a toon name arg
        if (ctx.getArgs().size() < 2)
            throw new CommandException("Please provide a Toon name or `all`!");

        // See if they want to unclaim all toons
        if (ctx.getArgs().get(1).equalsIgnoreCase("all")) {
            toonManager.unclaimAllToons(ctx.getAuthor());
            ctx.send(String.format("%s Unclaimed all of your toons!", ctx.getAuthor().getAsMention()));
        }
        else {
            Toon toon = toonManager.getToon(ctx.getArgs().get(1));  // Attempt to get the toon
            toonManager.unclaimToon(ctx.getAuthor(), toon);  // Attempt to unclaim it
            ctx.send(String.format("%s Unclaimed the toon `%s` for you!", ctx.getAuthor().getAsMention(), toon.getName()));
        }

    }

    // Temporary command to see toons claimed until the status message/updater thing is done
    @Command(name = "debug")
    public void debugCommand(Context ctx) {
        StringBuilder message = new StringBuilder();
        int numToonsClaimed = 0;
        for (Toon toon: toonManager.getRegisteredToons()){
            if (toon.isClaimed()) {
                message.append(String.format("%s - %s\n", toon.getName(), toon.getOwner().getAsMention()));
                numToonsClaimed++;
            }
        }
        if (numToonsClaimed > 0)
            ctx.send(message.toString());
        else
            ctx.send("No toons claimed!");
    }

}
