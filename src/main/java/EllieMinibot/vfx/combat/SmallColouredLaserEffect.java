package EllieMinibot.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class SmallColouredLaserEffect extends SmallLaserEffect {
    private static final float DUR = 0.25F;
    private static TextureAtlas.AtlasRegion img;


    public SmallColouredLaserEffect(float sX, float sY, float dX, float dY, Color colour) {
        super( sX,  sY,  dX,  dY);
        this.color = colour;
    }

}