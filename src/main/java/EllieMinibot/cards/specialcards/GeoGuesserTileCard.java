package EllieMinibot.cards.specialcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.EasyModalChoiceCard;
import EllieMinibot.localization.CodeQuestion;
import EllieMinibot.powers.UmActuallyPower;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;

@AutoAdd.Ignore
public class GeoGuesserTileCard extends AbstractEasyCard {
    public final static String ID = makeID("GeoGuesserTile");



    public GeoGuesserTileCard() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        tags.add(CardTags.EMPTY);


    }


    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
