package EllieMinibot.relics;

import EllieMinibot.CharacterFile;
import EllieMinibot.util.Wiz;

import static EllieMinibot.ModFile.makeID;

public class NerfTurretRelic extends AbstractEasyRelic {
    public static final String ID = makeID("NerfTurretRelic");

    public NerfTurretRelic() {
        super(ID, RelicTier.SHOP, LandingSound.FLAT, CharacterFile.Enums.ELLIE_MINIBOT_COLOR);
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        if(Wiz.p().hasRelic(NeuroDogRelic.ID)){
            NeuroDogRelic neuroDogRelic = (NeuroDogRelic) Wiz.p().getRelic(NeuroDogRelic.ID);
            if(neuroDogRelic.counter >= neuroDogRelic.maxNeuroDogCounter){
                this.flash();
            }
        }

    }
}
