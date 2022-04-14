
import com.raylib.Jaylib;

import static com.raylib.Jaylib.*;
public class Platform {

    private final Texture tx;
    private Jaylib.Vector2 pos;
    private final Jaylib.Rectangle txLeft = new Jaylib.Rectangle(272,16,16,5);
    private final Jaylib.Rectangle txMiddle = new Jaylib.Rectangle(288,16,16,5);
    private final Jaylib.Rectangle txRight = new Jaylib.Rectangle(304,16,16,5);
    private int platformWidth = 32;

    public Platform(Texture tx, Jaylib.Vector2 vector2,int size){
        this.tx = new Texture(tx);
        pos = vector2;
        platformWidth = size*32;
    }
    public void drawTexture(Player player){
        DrawTextureTiled(tx,txLeft,new Jaylib.Rectangle(-player.getLoc().x(),0,32,10),pos,0,2,WHITE);
        DrawTextureTiled(tx,txMiddle,new Jaylib.Rectangle(-player.getLoc().x(),0,platformWidth-64,10),new Jaylib.Vector2(pos.x()-32,pos.y()),0,2,WHITE);
        DrawTextureTiled(tx,txRight,new Jaylib.Rectangle(-player.getLoc().x(),0,32,10),new Jaylib.Vector2(pos.x()-platformWidth+32,pos.y()),0,2,WHITE);
    }

    public float getPosX() {
        return pos.x();
    }

    public int getPlatformWidth() {
        return platformWidth;
    }

    public int getPosY() {
        return 386+(int)(pos.y());
    }
}
