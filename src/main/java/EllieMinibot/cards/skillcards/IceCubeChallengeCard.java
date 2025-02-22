package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;

public class IceCubeChallengeCard  extends AbstractEasyCard {
    public final static String ID = makeID("IceCubeChallenge");


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

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("channel"), BaseMod.getKeywordDescription("channel")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("frost"), BaseMod.getKeywordDescription("frost")));
        return tooltips;
    }
}