package io.github.devvydoo.ascended.cogs;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import sh.niall.yui.cogs.Cog;
import sh.niall.yui.commands.Context;
import sh.niall.yui.commands.interfaces.Command;
import sh.niall.yui.exceptions.CommandException;

import javax.annotation.Nonnull;

/**
 * A Yui cog that is used for various utility or fun commands/behavior
 */
public class Fun extends Cog {

    /**
     * Various memes and phrases the bot responds with if a member sends a specific message or phrase
     *
     * @param event The GuildMessageReceivedEvent we are listening for
     */
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentStripped();

        if (msg.contains("hi bot"))
            event.getChannel().sendMessage("hi how are you").queue();
        else if (msg.contains("gone"))
            event.getChannel().sendMessage(":crab:").queue();
        else if (msg.contains("entering") && event.getAuthor().getId().equals("334282153204514816"))
            event.getChannel().sendMessage("sweat mode").queue();
    }

    /**
     * A utility command used to grab a screenshare link
     *
     * @param ctx The context of the command
     * @throws CommandException A general command exception that is thrown manually by our code, in this case when the user isn't in a voice channel
     */
    @Command(name = "screenshare", aliases = {"ss", "share"})
    public void screenshareCommand(Context ctx) throws CommandException {

        GuildVoiceState vc = ctx.getAuthor().getVoiceState();  // Get the user's voice state

        // Check to see if the user is in a vc, if not let them know they must be in one
        if (vc == null || vc.getChannel() == null)
            throw new CommandException(String.format("%s You must be in a voice channel to use that command!", ctx.getAuthor().getAsMention()));

        // Retrieve the IDs needed in the message
        String guildId = ctx.getGuild().getId();
        String vcChannelId = vc.getChannel().getId();

        // Send the message
        ctx.send(String.format("%s Here ya go!\nhttps://discordapp.com/channels/%s/%s", ctx.getAuthor().getAsMention(), guildId, vcChannelId));
    }

}
