
import com.raylib.Jaylib;

import static com.raylib.Jaylib.*;
public class Fruit {
    private final int[] hp = {10,5,15,10,5,10,50,20};
    private Texture tx;
    private int f;
    private Jaylib.Vector2 pos;
    private boolean doneT = false;
    private boolean stopDrawing = false;
    private int timeS = 0;
    public Fruit(Jaylib.Vector2 pos) {
        this.pos = pos;
        f = GetRandomValue(0,hp.length-1);
        String[] names = {"Apple", "Cherries", "Kiwi", "Orange", "Strawberry", "Bananas", "Melon", "Pineapple"};
        tx = LoadTexture("resources/Assets/Items/Fruits/"+ names[f]+".png");
    }
    public void drawTexture(Player player, double time){
        if(stopDrawing) return;
        if(timeS == -1){
            timeS = 6-(int)(time*5)%6;
            System.out.println(timeS);
        }
        if (doneT){
            DrawTextureTiled(tx,new Jaylib.Rectangle((int)(time*5+timeS)%6*32,0,32+(int)(time*5+timeS)%6*32,32),new Jaylib.Rectangle(-player.getLoc().x(),0,64,64),pos,0,2,WHITE);
            if ((int)(time*2+timeS)%6*32 == 160) stopDrawing = true;
            return;
        }
        DrawTextureTiled(tx,new Jaylib.Rectangle((int)(time*10)%17*32,0,32+(int)(time*10)%17*32,32),new Jaylib.Rectangle(-player.getLoc().x(),0,64,64),pos,0,2,WHITE);
    }
    public void unloadTexture(){
        UnloadTexture(tx);
    }

    public int getHp() {
        if (doneT) return 0;
        doneT = true;
        unloadTexture();
        tx = LoadTexture("resources/Assets/Items/Fruits/Collected.png");
        timeS = -1;
        return hp[f];
    }

    public int getPosX() {
        return (int)(pos.x());
    }
    public int getPosY(){
        return (int)(pos.y());
    }
}
