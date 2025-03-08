package EllieMinibot.cards;

public abstract class AbstractQuizCard extends AbstractEasyCard {


    public AbstractQuizCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }

    public AbstractQuizCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
    }

    public abstract void UpdateStreak(int streakValue);
}
