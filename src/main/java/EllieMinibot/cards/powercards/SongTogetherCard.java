package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.SONG_TOGETHER_KEY;
import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.applyToSelf;
import static EllieMinibot.util.Wiz.att;

public class SongTogetherCard extends AbstractEasyCard {
    public final static String ID = makeID("SongTogether");
    // intellij stuff power, self, uncommon

    public SongTogetherCard() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new SFXAction(SONG_TOGETHER_KEY));
        applyToSelf(new FocusPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@Kuinmelen")));
        tooltips.add(new TooltipInfo(GameDictionary.FOCUS.NAMES[0], GameDictionary.FOCUS.DESCRIPTION));
        return tooltips;
    }
}