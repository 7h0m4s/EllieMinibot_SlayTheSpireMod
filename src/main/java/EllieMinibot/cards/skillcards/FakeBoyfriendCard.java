package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EllieMinibot.ModFile.makeID;

public class FakeBoyfriendCard extends AbstractEasyCard {
    public final static String ID = makeID("FakeBoyfriend");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public FakeBoyfriendCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 9;
        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        this.upgradeBlock(3);
    }
}