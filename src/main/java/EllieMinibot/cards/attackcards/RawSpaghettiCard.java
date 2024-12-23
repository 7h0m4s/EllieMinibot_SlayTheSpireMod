package EllieMinibot.cards.attackcards;

import EllieMinibot.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EllieMinibot.ModFile.makeID;

public class RawSpaghettiCard  extends AbstractEasyCard {

    public final static String ID = makeID("RawSpaghetti");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public RawSpaghettiCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = damage = 10;
        secondDamage = 2;
        isMultiDamage = true;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        allDmg(AbstractGameAction.AttackEffect.NONE);
        DmgSelf(p, AbstractGameAction.AttackEffect.BLUNT_LIGHT);

    }

    @Override
    public void upp() {

        upgradeDamage(3);
        secondDamage = secondDamage -  1;
    }
}
