package EllieMinibot.cards.attackcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.applyToEnemy;
import static EllieMinibot.util.Wiz.applyToEnemyTop;

public class TeethModelCard extends AbstractEasyCard {

    public final static String ID = makeID("TeethModel");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public TeethModelCard() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 7;
        baseMagicNumber = magicNumber = 2;
        isMultiDamage = true;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        applyToEnemyTop(m, new VulnerablePower(m, magicNumber, false));
        applyToEnemyTop(m, new WeakPower(m, magicNumber, false));
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);

    }

    @Override
    public void upp() {

        upgradeDamage(3);
        upgradeMagicNumber(1);
    }
}
