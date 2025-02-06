package EllieMinibot.cards.attackcards;

import EllieMinibot.cards.AbstractEasyCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;

public class RawSpaghettiCard  extends AbstractEasyCard {

    public final static String ID = makeID("RawSpaghetti");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public RawSpaghettiCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = damage = 10;
        baseSecondDamage = secondDamage = 1;
        isMultiDamage = true;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        DmgSelf(p, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        allDmg(AbstractGameAction.AttackEffect.NONE);


    }

    @Override
    public void upp() {

        upgradeDamage(3);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@UkichanUki")));
        return tooltips;
    }
}
