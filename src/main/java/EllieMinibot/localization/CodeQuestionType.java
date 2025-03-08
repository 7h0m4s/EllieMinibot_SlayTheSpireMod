package EllieMinibot.localization;

import java.util.ArrayList;

public class CodeQuestionType {
    public final String Type;
    public final ArrayList<CodeQuestion> CodeQuestions;

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
