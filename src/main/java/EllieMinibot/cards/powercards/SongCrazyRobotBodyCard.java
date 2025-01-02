package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static EllieMinibot.ModFile.*;
import static EllieMinibot.util.Wiz.applyToSelf;
import static EllieMinibot.util.Wiz.att;

public class SongCrazyRobotBodyCard extends AbstractEasyCard {
    public final static String ID = makeID("SongCrazyRobotBody");
    // intellij stuff power, self, uncommon

    public SongCrazyRobotBodyCard() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new SFXAction(SONG_CRAZYROBOTBODY_KEY));
        applyToSelf(new StrengthPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }


}