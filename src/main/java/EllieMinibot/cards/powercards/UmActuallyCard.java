package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.powers.UmActuallyPower;
import EllieMinibot.powers.WaterproofingPower;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AccuracyPower;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.applyToSelf;

public class UmActuallyCard extends AbstractEasyCard {
    public final static String ID = makeID("UmActually");
    // intellij stuff power, self, uncommon

    public UmActuallyCard() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new UmActuallyPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("BugFact")), BaseMod.getKeywordDescription(makeID("BugFact"))));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("UmActuallyPower")), BaseMod.getKeywordDescription(makeID("UmActuallyPower"))));
        return tooltips;
    }
}