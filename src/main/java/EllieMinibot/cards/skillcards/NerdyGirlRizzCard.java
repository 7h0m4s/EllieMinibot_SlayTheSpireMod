package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.*;
import EllieMinibot.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.*;

public class NerdyGirlRizzCard extends AbstractEasyCard {
    public final static String ID = makeID("NerdyGirlRizz");
    // intellij stuff power, self, uncommon

    public NerdyGirlRizzCard() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new BookieCollabCard());
        easyCardList.add(new CerberCollabCard());
        easyCardList.add(new EllieCollabCard());
        easyCardList.add(new GomiCollabCard());
        easyCardList.add(new MapleCollabCard());
        easyCardList.add(new MinikoMewCollabCard());
        easyCardList.add(new ShoomimiCollabCard());
        easyCardList.add(new VedalCollabCard());
        easyCardList.add(new YourAverageBoCollabCard());

        atb(new SelectCardsCenteredAction(easyCardList,"Choose which orb to channel", this::channelForEachOrbSlot));

    }

    public void channelForEachOrbSlot(List<AbstractCard> cards) {

        AbstractCard card = cards.get(0);
        Class<?> orbClass;
        if(card.getClass() == BookieCollabCard.class) orbClass = BookieOrb.class;
        else if(card.getClass() == CerberCollabCard.class) orbClass = CerberOrb.class;
        else if(card.getClass() == EllieCollabCard.class) orbClass = EllieOrb.class;
        else if(card.getClass() == GomiCollabCard.class) orbClass = GomiOrb.class;
        else if(card.getClass() == MapleCollabCard.class) orbClass = MapleOrb.class;
        else if(card.getClass() == MinikoMewCollabCard.class) orbClass = MinikoMewOrb.class;
        else if(card.getClass() == ShoomimiCollabCard.class) orbClass = ShoomimiOrb.class;
        else if(card.getClass() == VedalCollabCard.class) orbClass = VedalOrb.class;
        else if(card.getClass() == YourAverageBoCollabCard.class) orbClass = YourAverageBoOrb.class;
        else return;


        for(int i = 0; i < Wiz.p().maxOrbs; ++i) {
            try {
                AbstractDungeon.player.channelOrb((AbstractOrb) orbClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void upp() {
        upgradeBaseCost(2);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@HoneyX9Y")));
        return tooltips;
    }
}