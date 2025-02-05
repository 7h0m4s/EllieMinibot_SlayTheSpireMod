package EllieMinibot.cards.attackcards;

import EllieMinibot.cards.AbstractEasyCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.makeInHand;

public class HireathonCard extends AbstractEasyCard {

    public final static String ID = makeID("Hireathon");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public HireathonCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 3;
        this.misc = 1;
        baseMagicNumber = magicNumber = 1;
        rawDescription = String.format(cardStrings.DESCRIPTION,this.misc);
        this.initializeDescription();
        this.cardsToPreview = new ThatCompanyCard();
    }

    @Override


    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        Random random = new Random();
        // Generate a random number between 0 and 99
        int chance = random.nextInt(100);

        // 5% chance (0-4 out of 0-99)
        if (chance < misc) {
            AbstractCard thatCompany = new ThatCompanyCard();
            if(this.upgraded) thatCompany.upgrade();

            this.exhaust = true;
            this.triggerOnExhaust();

            ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

            for(int i = masterDeck.size() - 1; i >= 0; --i) {
                AbstractCard masterCard = (AbstractCard)masterDeck.get(i);
                if (masterCard.uuid == this.uuid) {
                    AbstractDungeon.player.masterDeck.removeCard(masterCard);
                    break;
                }
            }
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(thatCompany, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            UnlockTracker.markCardAsSeen(thatCompany.cardID);

            AbstractCard tempThatCompany = new ThatCompanyCard();
            if(this.upgraded) tempThatCompany.upgrade();
            makeInHand(tempThatCompany);
        }

        if(this.misc >= 100 || (this.misc + this.magicNumber >= 100)) {
            this.addToBot(new IncreaseMiscAction(this.uuid, this.misc, 100-this.misc));
        }
        else{
            this.addToBot(new IncreaseMiscAction(this.uuid, this.misc, this.magicNumber));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        rawDescription = String.format(cardStrings.DESCRIPTION,this.misc);
        this.initializeDescription();
    }


    @Override
    public void upp() {

        upgradeMagicNumber(1);
        AbstractCard thatCompany = new ThatCompanyCard();
        thatCompany.upgrade();
        this.cardsToPreview = thatCompany;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("exhaust"), BaseMod.getKeywordDescription("exhaust")));
        return tooltips;
    }
}
