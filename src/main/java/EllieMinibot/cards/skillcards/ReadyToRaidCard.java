package EllieMinibot.cards.skillcards;
import EllieMinibot.actions.EasyXCostAction;
import EllieMinibot.cards.AbstractEasyCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.*;

public class ReadyToRaidCard  extends AbstractEasyCard {
    public final static String ID = makeID("ReadyToRaid");
    // intellij stuff power, self, uncommon

    public ReadyToRaidCard() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            applyToEnemyTop(m, new StrengthPower(m, -(effect + params[0])));
            return true;
        }, magicNumber));

    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("exhaust"), BaseMod.getKeywordDescription("exhaust")));
        return tooltips;
    }
}