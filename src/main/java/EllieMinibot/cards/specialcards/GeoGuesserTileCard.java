package EllieMinibot.cards.specialcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.EasyModalChoiceCard;
import EllieMinibot.localization.CodeQuestion;
import EllieMinibot.powers.UmActuallyPower;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;

@AutoAdd.Ignore
public class GeoGuesserTileCard extends AbstractEasyCard {
    public final static String ID = makeID("GeoGuesserTile");
    private TextureRegion mapTextureRegion;
    public int row,col;

    private int cardWidth = 600/2, cardHeight = 830/2;

    public GeoGuesserTileCard(TextureRegion mapRegion, int row, int col) {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        tags.add(CardTags.EMPTY);
        this.mapTextureRegion = mapRegion;
        this.row = row;
        this.col = col;
    }


    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.setColor(Color.WHITE);

        // x = column
        // y = row

        //height 830
        //width 600
        sb.draw(mapTextureRegion, (this.current_x - (cardWidth * this.drawScale)/2f), (this.current_y - (cardHeight * this.drawScale)/2f), cardWidth * this.drawScale, cardHeight * this.drawScale);
    }


}
