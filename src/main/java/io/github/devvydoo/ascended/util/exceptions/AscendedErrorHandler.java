package io.github.devvydoo.ascended.util.exceptions;

import sh.niall.yui.cogs.ErrorHandler;
import sh.niall.yui.commands.Context;
import sh.niall.yui.exceptions.CommandException;

public class AscendedErrorHandler extends ErrorHandler {

    /**
     * Error handler that is ran every time our Bot runs into an exception when handling commands
     *
     * @param ctx The context of the command that caused the exception
     * @param error The exception that was thrown
     */
    @Override
    public void onError(Context ctx, Throwable error) {

        if (error instanceof ToonAlreadyClaimedException){
            ToonAlreadyClaimedException specificError = (ToonAlreadyClaimedException) error;
            String owner = specificError.getToon().getOwner().getAsMention();
            ctx.send(String.format("%s The toon `%s` is already claimed by %s!", ctx.getAuthor().getAsMention(), specificError.getToon().getName(), owner));
        }
        else if (error instanceof ToonNotClaimedException){
            ToonNotClaimedException specificError = (ToonNotClaimedException) error;
            ctx.send(String.format("%s You cannot unclaim the toon `%s` as you don't have it claimed!", ctx.getAuthor().getAsMention(), specificError.getToon().getName()));
        }
        else if (error instanceof InvalidToonException)
            ctx.send(String.format("%s That toon does not exist!", ctx.getAuthor().getAsMention()));
        else if (error instanceof InvalidGroupException)
            ctx.send(String.format("%s That group does not exist!", ctx.getAuthor().getAsMention()));
        else if (error instanceof CommandException)
            ctx.send(String.format("%s %s", ctx.getAuthor().getAsMention(), error.getMessage()));
        else
            error.printStackTrace();
    }
}
