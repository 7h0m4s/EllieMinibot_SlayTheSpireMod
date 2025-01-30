package EllieMinibot.actions;

import EllieMinibot.orbs.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChannelRandomEllieOrbAction extends AbstractGameAction {
    private AbstractCard funCard;

    public ChannelRandomEllieOrbAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
    }

    public void update() {
        try {
            // Step 1: List of classes that extend AbstractOrb
            List<Class<? extends AbstractOrb>> orbClasses = new ArrayList<>();
            orbClasses.add(CerberOrb.class);
            orbClasses.add(GomiOrb.class);
            orbClasses.add(MinikoMewOrb.class);
            orbClasses.add(ShoomimiOrb.class);
            orbClasses.add(VedalOrb.class);
            orbClasses.add(YourAverageBoOrb.class);
            orbClasses.add(EllieOrb.class);
            orbClasses.add(GomiOrb.class);
            orbClasses.add(MapleOrb.class);

            // Step 2: Select a random class
            Random random = new Random();
            Class<? extends AbstractOrb> selectedClass = orbClasses.get(random.nextInt(orbClasses.size()));

            // Step 3: Instantiate the selected class
            AbstractOrb randomOrb = selectedClass.getDeclaredConstructor().newInstance();

            // Step 4: Channel the orb to the player
            AbstractDungeon.player.channelOrb(randomOrb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.isDone = true;
    }

}