package io.github.devvydoo.ascended;

import io.github.devvydoo.ascended.cogs.ErrorHandler;
import io.github.devvydoo.ascended.cogs.Fun;
import io.github.devvydoo.ascended.cogs.ToonClaimer;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.niall.yui.Yui;
import sh.niall.yui.cogs.commands.prefix.PrefixManager;
import sh.niall.yui.exceptions.PrefixException;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ascended {

    private String getDiscordToken(){

        File file = new File("token.txt");
        try (Scanner scanner = new Scanner(file)){
            return scanner.nextLine();
        } catch (FileNotFoundException e){
            throw new RuntimeException("Could not find valid discord token in token.txt!");
        }
    }


    public static void main(String[] args)  throws PrefixException, LoginException {

        Ascended main = new Ascended();

        Logger logger = LoggerFactory.getLogger(Ascended.class);

        PrefixManager prefixManager = new PrefixManager("/", "~");
        JDABuilder jdaBuilder = JDABuilder.createDefault(main.getDiscordToken());

        Yui yui = new Yui(jdaBuilder, prefixManager);
        yui.addCog(
                new Fun(),
                new ToonClaimer(),
                new ErrorHandler()
                );

        jdaBuilder.build();

        logger.info("Ready to go!");
    }

}
