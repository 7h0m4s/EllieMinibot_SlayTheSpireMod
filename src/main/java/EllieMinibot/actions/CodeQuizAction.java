package EllieMinibot.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class CodeQuizAction extends SelectCardsCenteredAction {

    public CodeQuizAction(ArrayList<AbstractCard> list, int amount, String textforSelect) {
        super(list, amount, textforSelect, (cards) -> {
            for (AbstractCard q : cards) {
                q.onChoseThisOption();
            }
        });
    }

    public CodeQuizAction(ArrayList<AbstractCard> list, int amount) {
        this(list, amount, "Choose.");
    }

    public CodeQuizAction(ArrayList<AbstractCard> list) {
        this(list, 1, "Choose.");
    }
}