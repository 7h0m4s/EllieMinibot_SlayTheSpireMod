package EllieMinibot.cards.specialcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.EasyModalChoiceCard;
import EllieMinibot.localization.CodeQuestion;
import EllieMinibot.powers.UmActuallyPower;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;

@AutoAdd.Ignore
public class GeoGuesserTileCard extends AbstractEasyCard {
    public final static String ID = makeID("GeoGuesserTile");
    private TextureRegion mapTextureRegion;
    private Texture outlineTexture;
    private Texture chosenOutlineTexture;
    public int row,col;
    private boolean hasOutline = false;
    private boolean isChosenCard = false;
    private final static float manualChosenOutlineScale = 1.2f;
    private final static float manualOutlineScale = 0.9f;
    private final static float manualMapScale = 0.8f;

    private int cardWidth = 680/2, cardHeight = 960/2;

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
    public AbstractCard makeCopy() {
        return new GeoGuesserTileCard(mapTextureRegion, row, col);
    }

    public void setOutline(Color requestedColour, boolean isChosenCard) {
        this.isChosenCard = isChosenCard;
        if(requestedColour == null){
            hasOutline = false;
            return;
        }
        hasOutline = true;
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(requestedColour);
        pixmap.fill();
        outlineTexture = new Texture(pixmap);
        pixmap.dispose();


        Pixmap pixmap2 = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.LIGHT_GRAY);
        pixmap2.fill();
        chosenOutlineTexture = new Texture(pixmap2);
        pixmap2.dispose();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.setColor(Color.WHITE);

        if(isChosenCard) {
            sb.draw(chosenOutlineTexture,
                    (this.current_x - ((cardWidth / 2f) * manualChosenOutlineScale * this.drawScale * Settings.scale)),
                    (this.current_y - ((cardHeight / 2f) * manualChosenOutlineScale * this.drawScale * Settings.scale)),
                    cardWidth * manualChosenOutlineScale * this.drawScale * Settings.scale,
                    cardHeight * manualChosenOutlineScale * this.drawScale * Settings.scale);
        }

        if(hasOutline) {
            sb.draw(outlineTexture,
                    (this.current_x - ((cardWidth / 2f) * manualOutlineScale * this.drawScale * Settings.scale)),
                    (this.current_y - ((cardHeight / 2f) * manualOutlineScale * this.drawScale * Settings.scale)),
                    cardWidth * manualOutlineScale * this.drawScale * Settings.scale,
                    cardHeight * manualOutlineScale * this.drawScale * Settings.scale);
        }

        sb.draw(mapTextureRegion,
                (this.current_x - ((cardWidth/2f) * manualMapScale * this.drawScale * Settings.scale)) ,
                (this.current_y - ((cardHeight/2f) * manualMapScale * this.drawScale * Settings.scale)),
                cardWidth * manualMapScale * this.drawScale * Settings.scale,
                cardHeight * manualMapScale * this.drawScale * Settings.scale);
    }


}
