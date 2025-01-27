package EllieMinibot.relics;

import EllieMinibot.CharacterFile;
import EllieMinibot.actions.ChannelRandomEllieOrbAction;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class EllieAntennaRelic extends AbstractEasyRelic {
    public static final String ID = makeID("EllieAntennaRelic");

    public EllieAntennaRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.ELLIE_MINIBOT_COLOR);
    }

    @Override
    public void atBattleStartPreDraw() {
        atb(new ChannelRandomEllieOrbAction());
    }
}
