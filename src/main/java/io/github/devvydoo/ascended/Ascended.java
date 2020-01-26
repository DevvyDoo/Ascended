package io.github.devvydoo.ascended;

import io.github.devvydoo.ascended.cogs.Fun;
import io.github.devvydoo.ascended.exceptions.AscendedErrorHandler;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import sh.niall.yui.Yui;
import sh.niall.yui.exceptions.PrefixException;
import sh.niall.yui.prefix.PrefixManager;

import javax.security.auth.login.LoginException;

public class Ascended {


    public static void main(String[] args)  throws PrefixException, LoginException {

        Logger logger = LoggerFactory.getLogger(Ascended.class);

        PrefixManager prefixManager = new PrefixManager("/");
        JDABuilder jdaBuilder = new JDABuilder("NTgyNzA5MDA5NjkwMTMyNDgy.XOx2wA.fTImAEVlTSYBOj66waXPh3LJufM");

        Yui yui = new Yui(jdaBuilder, prefixManager, new AscendedErrorHandler());
        yui.addCogs(new Fun());


        jdaBuilder.build();
    }

}
