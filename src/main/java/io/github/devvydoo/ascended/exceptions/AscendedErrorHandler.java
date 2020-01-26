package io.github.devvydoo.ascended.exceptions;

import sh.niall.yui.cogs.ErrorHandler;
import sh.niall.yui.commands.Context;
import sh.niall.yui.exceptions.CommandException;

public class AscendedErrorHandler extends ErrorHandler {

    @Override
    public void onError(Context ctx, Throwable error) {

        if (error instanceof CommandException)
            ctx.send(error.getMessage());
        else {
            error.printStackTrace();
        }

    }
}
