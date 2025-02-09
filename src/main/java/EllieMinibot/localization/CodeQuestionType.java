package EllieMinibot.localization;

import java.util.ArrayList;
import java.util.List;

public class CodeQuestionType {
    public String Type;
    public ArrayList<CodeQuestion> CodeQuestions;

    public CodeQuestionType(String type, ArrayList<CodeQuestion> codeQuestions) {

        // Filter out any questions where the Answer cannot be reliably matched to an option.
        // Filter any questions that have anything other than 4 options
        ArrayList<CodeQuestion> filteredList = new ArrayList<>();
        for (CodeQuestion question : codeQuestions) {
            if(question.options.stream().anyMatch(option -> option.equals(question.answer))
                    && question.options.size() == 4){
                filteredList.add(question);
            }
        }

        CodeQuestions = filteredList;
        Type = type;
    }
}
