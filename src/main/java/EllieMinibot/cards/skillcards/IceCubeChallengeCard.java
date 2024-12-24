package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;

import static EllieMinibot.ModFile.makeID;

public class IceCubeChallengeCard  extends AbstractEasyCard {
    public final static String ID = makeID("IceCubeChallenge");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public IceCubeChallengeCard() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        for (int i = 0; i < magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Frost()));
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}