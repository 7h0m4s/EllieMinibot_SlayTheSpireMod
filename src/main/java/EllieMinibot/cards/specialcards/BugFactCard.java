package EllieMinibot.cards.specialcards;

import EllieMinibot.cards.AbstractEasyCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static EllieMinibot.ModFile.makeID;

public class BugFactCard  extends AbstractEasyCard {
    public final static String ID = makeID("BugFact");
    public static int totalBugFacts;
    public int bugFactIndex = 0;
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public BugFactCard() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        tags.add(CardTags.EMPTY);

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower("Accuracy")) {
            this.baseDamage = 4 + AbstractDungeon.player.getPower("Accuracy").amount;
        } else {
            this.baseDamage = 4;
        }
        this.exhaust = true;
        totalBugFacts = cardStrings.EXTENDED_DESCRIPTION.length;
        applyBugFact();

    }

    private void applyBugFact() {
        Random random = new Random();
        bugFactIndex = random.nextInt(totalBugFacts);
        this.rawDescription = String.format("%s NL %s", this.rawDescription, cardStrings.EXTENDED_DESCRIPTION[bugFactIndex]);

        loadCardImage(String.format("ellieminibotResources/images/cards/bugfacts/BugFact_%03d.png",bugFactIndex));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("exhaust"), BaseMod.getKeywordDescription("exhaust")));
        return tooltips;
    }
}