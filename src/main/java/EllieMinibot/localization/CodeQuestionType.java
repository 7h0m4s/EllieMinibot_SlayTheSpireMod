package EllieMinibot.localization;

import java.util.ArrayList;
import java.util.List;

public class CodeQuestionType {
    public String Type;
    public ArrayList<CodeQuestion> CodeQuestions;

    public CodeQuestionType(String type, ArrayList<CodeQuestion> codeQuestions) {
        CodeQuestions = codeQuestions;
        Type = type;
    }
}
