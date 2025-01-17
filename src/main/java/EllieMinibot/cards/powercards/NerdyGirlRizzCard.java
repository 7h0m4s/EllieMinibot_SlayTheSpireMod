package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static EllieMinibot.ModFile.makeID;

public class NerdyGirlRizzCard extends AbstractEasyCard {
    public final static String ID = makeID("NerdyGirlRizz");
    // intellij stuff power, self, uncommon

    public NerdyGirlRizzCard() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // Remove player debuffs
        List<AbstractPower> playerDebuffPowers = p.powers.stream().filter(a -> a.type == AbstractPower.PowerType.DEBUFF).collect(Collectors.toList());
        p.powers.removeAll(playerDebuffPowers);

        // For each monster, move buffs to player
        for( AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(a -> !a.isDeadOrEscaped()).collect(Collectors.toList())){
            List<AbstractPower> monsterBuffPowers = monster.powers.stream().filter(a -> a.type == AbstractPower.PowerType.BUFF).collect(Collectors.toList());
            monsterBuffPowers.forEach(a-> Wiz.transferPower(a, monster, p));
            monster.powers.removeAll(monsterBuffPowers);
            playerDebuffPowers.forEach(a-> Wiz.transferPower(a, p, monster));
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