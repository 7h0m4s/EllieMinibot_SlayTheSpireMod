package EllieMinibot.cards.specialcards;

import EllieMinibot.cards.EasyModalChoiceCard;
import EllieMinibot.localization.CodeQuestion;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;

@AutoAdd.Ignore
public class CodeQuestionModalChoiceCard extends EasyModalChoiceCard {


    public CodeQuestionModalChoiceCard(String name, String description, Runnable onUseOrChosen, CodeQuestion codeQuestion) {
        super(name, description, onUseOrChosen);
        loadCardImage("ellieminibotResources/images/cards/mcq/"+name.toLowerCase()+".png");
    }


    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        switch(name.toLowerCase()){
            case "a":
                tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@Fefifox")));
                break;
            case "b":
                tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@SprayCactus")));
                break;
            case "c":
                tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@rurulalan")));
                break;
            case "d":
                tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@Pumpurumpum2")));
                break;
        }

        return tooltips;
    }
}
