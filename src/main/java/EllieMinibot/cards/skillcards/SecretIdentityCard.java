package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.applyToSelf;

public class SecretIdentityCard extends AbstractEasyCard {
    public final static String ID = makeID("SecretIdentity");


    public SecretIdentityCard() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        tags.add(CardTags.EMPTY);
        exhaust = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf((new IntangiblePlayerPower(p,this.magicNumber)));
    }

    @Override
    public void upp() {
        this.upgradeMagicNumber(1);
    }
}