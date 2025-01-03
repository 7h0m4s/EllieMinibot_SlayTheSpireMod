package EllieMinibot.cards.attackcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.applyToEnemyTop;

public class LeadedSolderCard extends AbstractEasyCard {

    public final static String ID = makeID("LeadedSolder");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public LeadedSolderCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 4;
        baseSecondMagic = secondMagic = 1;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        applyToEnemyTop(m, new PoisonPower(m,p, magicNumber));
        applyToEnemyTop(m, new WeakPower(m, secondMagic, false));
    }

    @Override
    public void upp() {

        upgradeMagicNumber(2);
        upgradeSecondMagic(1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("poison"), BaseMod.getKeywordDescription("poison")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("weak"), BaseMod.getKeywordDescription("weak")));
        return tooltips;
    }
}
