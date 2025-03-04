package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.*;
import static EllieMinibot.util.Wiz.applyToSelf;
import static EllieMinibot.util.Wiz.att;

public class SongCrazyRobotBodyCard extends AbstractEasyCard {
    public final static String ID = makeID("SongCrazyRobotBody");
    // intellij stuff power, self, uncommon

    public SongCrazyRobotBodyCard() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new SFXAction(SONG_CRAZYROBOTBODY_KEY));
        boolean powerExists = false;

        for(AbstractPower pow : p.powers) {
            if (pow.ID.equals("Barricade")) {
                powerExists = true;
                break;
            }
        }

        if (!powerExists) {
            this.addToBot(new ApplyPowerAction(p, p, new BarricadePower(p)));
        }
    }

    @Override
    public void upp() {
        this.upgradeBaseCost(2);;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "Xenogaeia")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("strength"), BaseMod.getKeywordDescription("strength")));
        return tooltips;
    }
}