package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.powers.WaterproofingPower;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.applyToSelf;

public class WaterproofingCard extends AbstractEasyCard {
    public final static String ID = makeID("Waterproofing");
    // intellij stuff power, self, uncommon

    public WaterproofingCard() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new WaterproofingPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@Kuinmelen")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Waterproofing")), BaseMod.getKeywordDescription(makeID("Waterproofing"))));
        return tooltips;
    }
}