package io.github.devvydoo.ascended.cogs;

import io.github.devvydoo.ascended.util.exceptions.InvalidGroupException;
import io.github.devvydoo.ascended.util.exceptions.InvalidToonException;
import io.github.devvydoo.ascended.util.exceptions.ToonAlreadyClaimedException;
import io.github.devvydoo.ascended.util.exceptions.ToonNotClaimedException;
import sh.niall.yui.cogs.cog.Cog;
import sh.niall.yui.cogs.commands.context.Context;
import sh.niall.yui.exceptions.CommandException;

public class ErrorHandler extends Cog {

    @Override
    public void onError(Context ctx, Exception error, boolean thisCog) {
        if (error instanceof ToonAlreadyClaimedException){
            ToonAlreadyClaimedException specificError = (ToonAlreadyClaimedException) error;
            String owner = specificError.getToon().getOwner().getAsMention();
            ctx.sendq(String.format("%s The toon `%s` is already claimed by %s!", ctx.getAuthor().getAsMention(), specificError.getToon().getName(), owner), null);
        }
        else if (error instanceof ToonNotClaimedException){
            ToonNotClaimedException specificError = (ToonNotClaimedException) error;
            ctx.sendq(String.format("%s You cannot unclaim the toon `%s` as you don't have it claimed!", ctx.getAuthor().getAsMention(), specificError.getToon().getName()), null);
        }
        else if (error instanceof InvalidToonException)
            ctx.sendq(String.format("%s That toon does not exist!", ctx.getAuthor().getAsMention()), null);
        else if (error instanceof InvalidGroupException)
            ctx.sendq(String.format("%s That group does not exist!", ctx.getAuthor().getAsMention()), null);
        else if (error instanceof CommandException)
            ctx.sendq(String.format("%s %s", ctx.getAuthor().getAsMention(), error.getMessage()), null);
        else
            error.printStackTrace();
    }


}
