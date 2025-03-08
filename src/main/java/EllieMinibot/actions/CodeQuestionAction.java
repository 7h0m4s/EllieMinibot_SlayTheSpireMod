package EllieMinibot.actions;

import EllieMinibot.ModFile;
import EllieMinibot.cards.specialcards.CodeQuestionModalChoiceCard;
import EllieMinibot.localization.CodeQuestion;
import EllieMinibot.localization.CodeQuestionType;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static EllieMinibot.config.ConfigPanel.Disable_QUIZ_Card_Questions;
import static EllieMinibot.util.Wiz.atb;

public class CodeQuestionAction extends AbstractGameAction {

    public CodeQuestionAction(Runnable onSuccess, Runnable onFailure) {
        Random random = new Random();

        if(Disable_QUIZ_Card_Questions || ModFile.CODE_QUESTION_TYPES.isEmpty()){
            onSuccess.run();
            return;
        }

        int codeQuestionTypeIndex = random.nextInt(ModFile.CODE_QUESTION_TYPES.size());
        CodeQuestionType codeQuestionType = ModFile.CODE_QUESTION_TYPES.get(codeQuestionTypeIndex);

        int codeQuestionIndex = random.nextInt(codeQuestionType.CodeQuestions.size());

        if(Disable_QUIZ_Card_Questions || codeQuestionType.CodeQuestions.isEmpty()){
            onSuccess.run();
            return;
        }

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
            Runnable cardAction;
            if(option.equals(codeQuestion.answer)){
                cardAction = onSuccess;
            }
            else{
                cardAction = onFailure;
            }
            easyCardList.add(new CodeQuestionModalChoiceCard(cardName, option, cardAction, codeQuestion));

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
    }
}
