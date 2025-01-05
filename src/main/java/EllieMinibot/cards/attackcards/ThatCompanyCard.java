package EllieMinibot.cards.attackcards;

import EllieMinibot.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Random;

import static EllieMinibot.ModFile.makeID;

public class ThatCompanyCard extends AbstractEasyCard {

    public final static String ID = makeID("ThatCompany");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public ThatCompanyCard() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = damage = 32;
        tags.add(CardTags.STRIKE);
        exhaust = true;

        this.costForTurn = 0; // Free to play when first created
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        dmg(m, AbstractGameAction.AttackEffect.SMASH);
    }

    @Override
    public void upp() {
        upgradeDamage(10);
    }
}
