
import com.raylib.Jaylib;

import static com.raylib.Jaylib.*;


public class Spike {

    private Texture tx;
    private Jaylib.Vector2 pos;
    private int width = 32;

    public Spike(Texture texture, Jaylib.Vector2 pos,int size){
        tx = texture;
        this.pos = pos;
        width = size*width;
    }
    public void drawTexture(Player player){
        DrawTextureTiled(tx,new Jaylib.Rectangle(0,0,16,16),new Jaylib.Rectangle(-player.getLoc().x(),0,width,32),pos,0,2,WHITE);
    }

    public int getWidth() {
        return width;
    }

    public int getPosX() {
        return (int)(pos.x());
    }
}
