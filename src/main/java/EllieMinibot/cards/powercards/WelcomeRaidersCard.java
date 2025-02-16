package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.powers.WelcomeRaidersPower;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.*;
import static EllieMinibot.util.Wiz.applyToSelf;
import static EllieMinibot.util.Wiz.att;

public class WelcomeRaidersCard extends AbstractEasyCard {
    public final static String ID = makeID("WelcomeRaiders");
    // intellij stuff power, self, uncommon

    public WelcomeRaidersCard() {
        super(ID, 3, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new SFXAction(WELCOME_RAIDERS_KEY));
        applyToSelf(new WelcomeRaidersPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@HoneyX9Y")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("channel"), BaseMod.getKeywordDescription("channel")));
        return tooltips;
    }
}