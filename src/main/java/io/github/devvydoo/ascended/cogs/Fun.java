package io.github.devvydoo.ascended.cogs;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import sh.niall.yui.cogs.Cog;
import sh.niall.yui.commands.Context;
import sh.niall.yui.commands.interfaces.Command;
import sh.niall.yui.exceptions.CommandException;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A Yui cog that is used for various utility or fun commands/behavior
 *
 * TODO: Commands that need to be added still: RegionVote, Aetball, Msg
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
     * Simple ping command
     *
     * @param ctx The context of the command
     */
    @Command(name = "ping")
    public void pingCommand(Context ctx) {
        ctx.send("Pong!");
    }

    /**
     * A simple coin flip command used for making decisions
     *
     * @param ctx The context of the command
     */
    @Command(name = "flip")
    public void flipCoinCommand(Context ctx){
        ctx.send(String.format("%s I got %s!", ctx.getAuthor().getAsMention(), Math.random() < .5 ? "heads" : "tails"));
    }

    /**
     * A command that lets the user play rock paper scissors with the bot
     *
     * @param ctx The context of the command
     * @throws CommandException The command  will throw an error if invalid arguments are provided
     */
    @Command(name = "rps", aliases = {"rockpaperscissors"})
    public void rockPaperScissorsCommand(Context ctx) throws CommandException {

        // Attempt to get their choice
        String userChoice;
        try {
            userChoice = ctx.getArgs().get(1);  // 2nd argument is their input, 1st is the actual /rps command
        } catch (IndexOutOfBoundsException ignored){
            throw new CommandException("You must pass in an argument! < `rock` | `paper` | `scissors` >");
        }

        // Make sure the user gave a valid choice
        if (!(userChoice.equalsIgnoreCase("rock") || userChoice.equalsIgnoreCase("paper") || userChoice.equalsIgnoreCase("scissors")))
            throw new CommandException("You must say either `rock` `paper` or `scissors`!");

        // Now that we have an argument let's calculate the bot's choice
        String botChoice;
        double randomNumber = Math.random();
        if (randomNumber < .33)
            botChoice = "rock";
        else if (randomNumber < .66)
            botChoice = "paper";
        else
            botChoice = "scissors";

        // Check the case where it is a tie
        if (botChoice.equalsIgnoreCase(userChoice))
            ctx.send(String.format("It's a draw! We both picked `%s`!", userChoice));
        // Check the case where the user won
        else if (botChoice.equalsIgnoreCase("paper") && userChoice.equalsIgnoreCase("scissors")
        || botChoice.equalsIgnoreCase("scissors") && userChoice.equalsIgnoreCase("rock")
        || botChoice.equalsIgnoreCase("rock") && userChoice.equalsIgnoreCase("paper"))
            ctx.send(String.format("You win! You picked `%s` and I picked `%s`!", userChoice, botChoice));
        // Case where bot won
        else
            ctx.send(String.format("I win! I picked `%s` and you picked `%s`!", botChoice, userChoice));
    }

    /**
     * A command used to pick a random item from a list of args supplied by the user
     *
     * @param ctx The context of the command
     * @throws CommandException Throws an error when we are given invalid arguments
     */
    @Command(name = "pick", aliases = {"pickrandom", "randompicker", "random"})
    public void randomPickCommand(Context ctx) throws CommandException {

        // Sanity check, were we given some arguments to work with? i.e. we need at least 2 since /pick will be one
        if (ctx.getArgs().size() < 2)
            throw new CommandException("You must provide comma-separated arguments!");

        ctx.getArgs().remove(0);  // Removes the arg that contains the actual command

        // Join the entire message into one string by replacing different list entries with spaces
        String joinedArguments = String.join(" ", ctx.getArgs());

        // First make sure that we have at least two choices to pick from
        if (!joinedArguments.contains(","))
            throw new CommandException("You must separate the choices with commas!");

        // Yea no don't let people be edgy
        if (joinedArguments.contains(",,"))
            throw new CommandException("You cannot have nothing as a choice!");

        // Now we can make a new list of things by separating the string by commas
        List<String> choices = Arrays.asList(joinedArguments.split(","));

        if (choices.isEmpty())
            throw new CommandException("Please provide some choices!");

        // Pick a random element
        String element = choices.get(new Random().nextInt(choices.size()));
        element = element.trim();  // Removes any weird spacing that can occur

        // Let em know
        ctx.send(String.format("%s I picked `%s`!", ctx.getAuthor().getAsMention(), element));
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
            throw new CommandException("%s You must be in a voice channel to use that command!");

        // Retrieve the IDs needed in the message
        String guildId = ctx.getGuild().getId();
        String vcChannelId = vc.getChannel().getId();

        // Send the message
        ctx.send(String.format("%s Here ya go!\nhttps://discordapp.com/channels/%s/%s", ctx.getAuthor().getAsMention(), guildId, vcChannelId));
    }

}
