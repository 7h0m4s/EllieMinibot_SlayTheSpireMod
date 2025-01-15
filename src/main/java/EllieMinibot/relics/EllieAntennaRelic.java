package EllieMinibot.relics;

import EllieMinibot.CharacterFile;
import EllieMinibot.orbs.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static EllieMinibot.ModFile.makeID;

public class EllieAntennaRelic extends AbstractEasyRelic {
    public static final String ID = makeID("EllieAntennaRelic");

    public EllieAntennaRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.ELLIE_MINIBOT_COLOR);
    }

    @Override
    public void atBattleStartPreDraw() {
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
    }
}
