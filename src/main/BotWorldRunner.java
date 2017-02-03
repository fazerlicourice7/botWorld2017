package main;

import brain.AaribWall;
import brain.JannBot;
import brain.RandomRat;
import brain.VamshiBot;
import brain.dijkstra;
import brain.testBot;
import util.PAGuiUtil;
import util.RatBotManager;
import world.BotWorld;

public class BotWorldRunner {

    public static void main(String[] args) {
        PAGuiUtil.setLookAndFeelToOperatingSystemLookAndFeel();
        BotWorld world = new BotWorld();
        world.show();

        // Load Bots from the 'brain' package
        RatBotManager.loadRatBotsFromClasspath("brain", world);

        /*
         * This is another place where you can add Bots to the match. 
         * Loading them here will save the time of selecting from the menu.  
         */
        world.add(new JannBot());
        world.add(new RandomRat());
        world.add(new RandomRat());
        world.add(new RandomRat());
        world.add(new RandomRat());
        world.add(new RandomRat());
        world.add(new RandomRat());
        world.add(new VamshiBot());

        world.show();
    }
}
