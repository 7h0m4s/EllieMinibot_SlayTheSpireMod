package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.YourAverageBoOrb;
import EllieMinibot.powers.AboveAverageBoPower;
import EllieMinibot.powers.WaterproofingPower;
import EllieMinibot.powers.WelcomeRaidersPower;
import EllieMinibot.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.WELCOME_RAIDERS_KEY;
import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.*;

public class AboveAverageBoCard extends AbstractEasyCard {
    public final static String ID = makeID("AboveAverageBo");
    // intellij stuff power, self, uncommon

    public AboveAverageBoCard() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        applyToSelf(new AboveAverageBoPower(p, 1));
        for(int i = 0; i < magicNumber; ++i) {
            atb(new ChannelAction(new YourAverageBoOrb()));
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@WigsInk")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("YourAverageBo")), BaseMod.getKeywordDescription(makeID("YourAverageBo"))));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("channel"), BaseMod.getKeywordDescription("channel")));
        return tooltips;
    }
}