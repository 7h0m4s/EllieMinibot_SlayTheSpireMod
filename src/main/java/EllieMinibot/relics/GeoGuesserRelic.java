package EllieMinibot.relics;

import EllieMinibot.CharacterFile;
import EllieMinibot.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static EllieMinibot.ModFile.makeID;

public class GeoGuesserRelic extends AbstractEasyRelic {
    public static final String ID = makeID("GeoGuesserRelic");
    private final Random random = new Random();

    public GeoGuesserRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL, CharacterFile.Enums.ELLIE_MINIBOT_COLOR);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        List<AbstractCard> rareCards = Wiz.drawPile().group.stream()
                .filter(card -> card.rarity == AbstractCard.CardRarity.RARE)
                .collect(Collectors.toList());
        if(rareCards.size() > 0){
            int randomIndex = random.nextInt(rareCards.size());
            AbstractCard selectedCard = rareCards.get(randomIndex);
            Wiz.drawPile().removeCard(selectedCard);
            Wiz.drawPile().addToTop(selectedCard);
            Wiz.att(new DrawCardAction(1, false));
            this.flash();
        }

    }
}
