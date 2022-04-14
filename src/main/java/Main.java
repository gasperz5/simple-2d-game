import com.raylib.Jaylib;

import java.util.ArrayList;

import static com.raylib.Jaylib.*;

public class Main {
    public static void main(String[] args) {
        int screenWidth = 800;
        int screenHeight = 450;

        int score = 0;
        InitWindow(screenWidth, screenHeight, "Seminarska Naloga");
        Texture bg = LoadTexture("resources/Assets/Background/Brown.png");
        Texture sp = LoadTexture("resources/Assets/Traps/Spikes/Idle.png");
        Texture gd = LoadTexture("resources/Assets/Terrain/Terrain(16x16).png");
        Texture bl = new Texture(gd);
        Player pl = new Player();
        int highScore = 0;
        ArrayList<Block> blocks = new ArrayList<>();
        ArrayList<Platform> platforms = new ArrayList<>();
        ArrayList<Spike> spikes = new ArrayList<>();
        ArrayList<Fruit> fruits = new ArrayList<>();


        for(int i = 0, addB = 0, addS = 0, addP = 0, spR = 1, plR = 2; i < 10; i++){
            addB+=GetRandomValue(160,384);
            blocks.add(new Block(bl,new Jaylib.Vector2(-500-addB,96-screenHeight)));
            spR = GetRandomValue(1,3);
            addS+=GetRandomValue(5,10)*32+spR*32;
            spikes.add(new Spike(sp,new Jaylib.Vector2(-300-addS,96-screenHeight),spR));
            plR = GetRandomValue(2,5);
            addP+=GetRandomValue(5,20)*32+plR*32;
            platforms.add(new Platform(bl,new Jaylib.Vector2(-500-addP,96+32*GetRandomValue(1,2)-screenHeight),plR));
        }
        fruits.add(new Fruit(new Jaylib.Vector2(blocks.get(1).getPosX()+16-(blocks.get(1).getBoxWidth()-32)/2,-322+blocks.get(1).getBoxHeight())));
        fruits.add(new Fruit(new Jaylib.Vector2(blocks.get(5).getPosX()+16-(blocks.get(5).getBoxWidth()-32)/2,-322+blocks.get(5).getBoxHeight())));
        SetTargetFPS(60);
        double frame = 0;
        while (!WindowShouldClose()){
            BeginDrawing();
            ClearBackground(RAYWHITE);
            DrawTextureTiled(pl.getRun(),new Jaylib.Rectangle((int)frame%21*32,0,32+(int)frame%12*32,32),new Jaylib.Rectangle(0,0,64,64),new Jaylib.Vector2(-screenWidth/2+32,-322),0,2,WHITE);
            DrawTextureTiled(gd,new Jaylib.Rectangle(112,0,16,32),new Jaylib.Rectangle(-5*(int)frame%32,0,screenWidth+5*(int)frame%32,64),new Jaylib.Vector2(0,64-screenHeight),0,2,WHITE);
            DrawText("PRESS ENTER TO START", screenWidth/2-5*26, 270, 20, BLACK);
            EndDrawing();
            if (IsKeyPressed(KEY_ENTER)) break;
            frame+=0.2;
        }
        frame = 0;
        while (!WindowShouldClose()) {
            if (IsKeyPressed(KEY_R) && !pl.isAlive()) {

                blocks.removeAll(blocks);
                spikes.removeAll(spikes);
                platforms.removeAll(platforms);
                fruits.removeAll(fruits);

                for(int i = 0, addB = 0, addS = 0, addP = 0, spR = 1, plR = 2; i < 10; i++){
                    addB+=GetRandomValue(160,384);
                    blocks.add(new Block(bl,new Jaylib.Vector2(-500-addB,96-screenHeight)));
                    spR = GetRandomValue(1,3);
                    addS+=GetRandomValue(5,10)*32+spR*32;
                    spikes.add(new Spike(sp,new Jaylib.Vector2(-300-addS,96-screenHeight),spR));
                    plR = GetRandomValue(2,5);
                    addP+=GetRandomValue(5,20)*32+plR*32;
                    platforms.add(new Platform(bl,new Jaylib.Vector2(-500-addP,96+32*GetRandomValue(1,2)-screenHeight),plR));
                }
                fruits.add(new Fruit(new Jaylib.Vector2(blocks.get(1).getPosX()+16-(blocks.get(1).getBoxWidth()-32)/2,-322+blocks.get(1).getBoxHeight())));
                fruits.add(new Fruit(new Jaylib.Vector2(blocks.get(5).getPosX()+16-(blocks.get(5).getBoxWidth()-32)/2,-322+blocks.get(5).getBoxHeight())));

                score = 0;
                pl.unloadTextures();
                pl = new Player();

            }
            if (pl.isAlive()) {
                
                if (pl.getLoc().x()>score/2) score = (int)(pl.getLoc().x())*2;
                if(IsKeyDown(KEY_D)) pl.moveX(true);
                if(IsKeyDown(KEY_A) && pl.getLoc().x()>100) pl.moveX(false);
                if(IsKeyDown(KEY_S)) pl.slide();
                if(IsKeyReleased(KEY_S)) pl.standUp();
                if(IsKeyPressed(KEY_SPACE)||IsKeyPressed(KEY_W)) pl.jump();
                if(IsKeyPressed(KEY_SPACE)||IsKeyPressed(KEY_D)||IsKeyPressed(KEY_A)||IsKeyPressed(KEY_W)) frame = 0;
                if(IsKeyDown(KEY_LEFT_SHIFT) && IsKeyPressed(KEY_S)) TakeScreenshot("image"+ System.currentTimeMillis() +".png");
                pl.update(blocks,platforms,spikes,fruits);
                if (-blocks.get(0).getPosX()<pl.getMinX()){
                    blocks.remove(0);
                    blocks.add(new Block(bl,new Jaylib.Vector2(blocks.get(blocks.size()-1).getPosX()-GetRandomValue(150,400),96-screenHeight)));
                }
                if (-spikes.get(0).getPosX()<pl.getMinX()){
                    spikes.remove(0);
                    spikes.add(new Spike(sp,new Jaylib.Vector2(spikes.get(spikes.size()-1).getPosX()-GetRandomValue(150,400),96-screenHeight),GetRandomValue(1,3)));
                }
                if (-platforms.get(0).getPosX()<pl.getMinX()){
                    platforms.remove(0);
                    platforms.add(new Platform(bl,new Jaylib.Vector2(platforms.get(platforms.size()-1).getPosX()-GetRandomValue(150,400),96+32*GetRandomValue(0,2)-screenHeight),GetRandomValue(2,5)));
                }
            if (-fruits.get(0).getPosX()<pl.getMinX()){
                fruits.get(0).unloadTexture();
                fruits.remove(0);
                fruits.add(new Fruit(new Jaylib.Vector2(blocks.get(blocks.size()-1).getPosX()+16-(blocks.get(blocks.size()-1).getBoxWidth()-32)/2,-322+blocks.get(blocks.size()-1).getBoxHeight())));
            }
            frame+=0.2;
            BeginDrawing();
            ClearBackground(RAYWHITE);
            DrawTextureTiled(bg,new Jaylib.Rectangle(0,0,64,64),new Jaylib.Rectangle(-pl.getLoc().x()%128/2,0,screenWidth+pl.getLoc().x()%128/2,screenHeight),new Jaylib.Vector2(0,0),0,1,WHITE);//background
                DrawTextureTiled(gd,new Jaylib.Rectangle(112,0,16,32),new Jaylib.Rectangle(-pl.getLoc().x()%32,0,screenWidth+pl.getLoc().x()%32,64),new Jaylib.Vector2(0,64-screenHeight),0,2,WHITE);//floor
                for (Block block : blocks) {
                    block.drawTexture(pl);
                }
                for (Platform platform : platforms){
                    platform.drawTexture(pl);
                }
                for (Spike spike:spikes){
                spike.drawTexture(pl);
            }
            for (Fruit fruit : fruits){
                fruit.drawTexture(pl,GetTime());
            }
            pl.drawTexture(frame);
            //DrawFPS(700, 20);
            DrawText(""+score,20,40,20,BLACK);
            EndDrawing();
        }else if(highScore<score){
                highScore = score;
        }else{
            BeginDrawing();
            ClearBackground(RAYWHITE);
            DrawText("HIGH SCORE", screenWidth/2-5*12, screenHeight/2-60, 20, BLACK);
            DrawText(highScore+"",screenWidth/2-5-5*(int)Math.log10(highScore),screenHeight/2-40,20,BLACK);
            DrawText("SCORE", screenWidth/2-5*6, screenHeight/2-20, 20, BLACK);
            DrawText(score+"",screenWidth/2-5-5*(int)Math.log10(score),screenHeight/2,20,BLACK);
            DrawText("PRESS R TO RESTART", screenWidth/2-5*13, screenHeight/2+35, 12, GRAY);
            EndDrawing();
        }
        }
        UnloadTexture(bg);
        UnloadTexture(gd);
        CloseWindow();
    }
}