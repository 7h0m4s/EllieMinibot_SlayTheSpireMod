package EllieMinibot.actions;

import EllieMinibot.ModFile;
import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.specialcards.CodeQuestionModalChoiceCard;
import EllieMinibot.localization.CodeQuestion;
import EllieMinibot.localization.CodeQuestionType;
import EllieMinibot.cards.EasyModalChoiceCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.function.Predicate;

import static EllieMinibot.util.Wiz.atb;

public class CodeQuestionAction extends AbstractGameAction {

    public CodeQuestionAction(Runnable onSuccess, Runnable onFailure) {


        Random random = new Random();
        int codeQuestionTypeIndex = random.nextInt(ModFile.CODE_QUESTION_TYPES.size());
        CodeQuestionType codeQuestionType = ModFile.CODE_QUESTION_TYPES.get(codeQuestionTypeIndex);

        int codeQuestionIndex = random.nextInt(codeQuestionType.CodeQuestions.size());
        CodeQuestion codeQuestion = codeQuestionType.CodeQuestions.get(codeQuestionIndex);
        Collections.shuffle(codeQuestion.options);

        ArrayList<AbstractCard> easyCardList = new ArrayList<>();

        int count = 0;
        for(String option: codeQuestion.options){
            String cardName;
            switch(count){
                case 0:
                    cardName = "A";
                    break;
                case 1:
                    cardName = "B";
                    break;
                case 2:
                    cardName = "C";
                    break;
                case 3:
                    cardName = "D";
                    break;
                default:
                    cardName = "Something Broke";
                    break;
            }
            String cardDescription = option;
            Runnable cardAction;
            if(option.equals(codeQuestion.answer)){
                cardAction = onSuccess;
            }
            else{
                cardAction = onFailure;
            }
            easyCardList.add(new CodeQuestionModalChoiceCard(cardName, cardDescription, cardAction, codeQuestion));

            count++;

        }

        // do the action
        atb(new SelectCardsCenteredAction(easyCardList,codeQuestionType.Type +": "+ codeQuestion.question, false,(c) -> true, (cards) -> {
            for (AbstractCard q : cards) {
                q.onChoseThisOption();
            }
        }));

    }

    @Override
    public void update() {
        this.isDone = true;
        return;
    }
}
