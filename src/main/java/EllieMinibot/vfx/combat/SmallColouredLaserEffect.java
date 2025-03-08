package EllieMinibot.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class SmallColouredLaserEffect extends SmallLaserEffect {
    private static final float DUR = 0.25F;
    private static TextureAtlas.AtlasRegion img;


    public SmallColouredLaserEffect(float sX, float sY, float dX, float dY, Color colour) {
        super( sX,  sY,  dX,  dY);
        this.color = colour;
    }

}