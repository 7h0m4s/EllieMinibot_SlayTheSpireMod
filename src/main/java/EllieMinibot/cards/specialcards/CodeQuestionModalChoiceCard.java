package EllieMinibot.cards.specialcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.EasyModalChoiceCard;
import EllieMinibot.localization.CodeQuestion;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EllieMinibot.ModFile.makeID;

@AutoAdd.Ignore
public class CodeQuestionModalChoiceCard extends EasyModalChoiceCard {


    public CodeQuestionModalChoiceCard(String name, String description, Runnable onUseOrChosen, CodeQuestion codeQuestion) {
        super(name, description, onUseOrChosen);
    }
}
