
import com.raylib.Jaylib;

import static com.raylib.Jaylib.*;
public class Block {

    private final Texture tx;
    private Jaylib.Vector2 pos;
    private Jaylib.Rectangle txRec = new Jaylib.Rectangle(192,16,16,16);
    private int boxHeight = 32;
    private int boxWidth = 32;

    public Block(Texture tx, Jaylib.Vector2 vector2){
        this.tx = new Texture(tx);
        pos = vector2;
        int i = GetRandomValue(0,200);
        if (i>150) {
            txRec = new Jaylib.Rectangle(240,0,16,48);
            pos = new Jaylib.Vector2(vector2.x(),vector2.y()+64);
            boxHeight = 96;
        } else if (i>100) {
            txRec = new Jaylib.Rectangle(192,0,48,16);
            boxWidth = 96;
        }
    }
    public void drawTexture(Player player){
        DrawTextureTiled(tx,txRec,new Jaylib.Rectangle(-player.getLoc().x(),0,boxWidth,boxHeight),pos,0,2,WHITE);
    }

    public float getPosX() {
        return pos.x();
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public int getBoxWidth() {
        return boxWidth;
    }
}
