
import com.raylib.Jaylib;

import java.util.ArrayList;

import static com.raylib.Jaylib.*;

public class Player {
    private final Texture idle = LoadTexture("resources/Assets/Main Characters/Mask Dude/Idle (32x32).png");
    private final Texture run = LoadTexture("resources/Assets/Main Characters/Mask Dude/Run (32x32).png");
    private final Texture jump = LoadTexture("resources/Assets/Main Characters/Mask Dude/Jump (32x32).png");
    private final Texture doubleJump = LoadTexture("resources/Assets/Main Characters/Mask Dude/Double Jump (32x32).png");
    private final Texture fall = LoadTexture("resources/Assets/Main Characters/Mask Dude/Fall (32x32).png");
    private final Texture slide = LoadTexture("resources/Assets/Main Characters/Mask Dude/Slide (32x32).png");
    private Jaylib.Vector2 loc = new Jaylib.Vector2();
    private int minX = 0;
    private int maxY = 322;
    private Jaylib.Vector2 speed = new Jaylib.Vector2();
    private double multiply = 1;
    private double health = 100;
    private boolean sliding = false;
    private boolean down = false;
    private  int jumpCount = 0;


    public Player() {
        loc.x(100);
        loc.y(322);
    }

    public void setLoc(Jaylib.Vector2 loc) {
        this.loc = loc;
    }

    public Jaylib.Vector2 getLoc() {
        return loc;
    }

    public void gravity() {
        speed.y(speed.y() + 1);
    }

    public void jump() {
        if (jumpCount==2) return;
        if (speed.y()!=0) jumpCount=1;
        jumpCount++;
        speed.y(-15);
    }

    public int update(ArrayList<Block> blocks, ArrayList<Platform> platforms, ArrayList<Spike> spikes, ArrayList<Fruit> fruits) {
        if (!sliding) multiply = 1+loc.x()/10000;
        gravity();
        int a = (int)(speed.x() - 0.5f * speed.x() / Math.abs(speed.x()));
        speed.x(a);
        loc.y(loc.y() + speed.y());
        if (loc.y() >= maxY && speed.y()>0){
            loc.y(maxY);
            speed.y(0);
            jumpCount=0;
        }
        if (Math.abs(speed.x()) > 0.6f) {
            loc.x(loc.x() + (int)(speed.x() * multiply));
        }
        boolean suc = false;
        for (int i = 0; i < blocks.size(); i++) {
            boolean in = loc.x()+100>-blocks.get(i).getPosX()-52 && loc.x()+100<-blocks.get(i).getPosX()-12+blocks.get(i).getBoxWidth();
            if (in){
                suc = true;
                if (loc.y()<322-blocks.get(i).getBoxHeight()) maxY = 322-blocks.get(i).getBoxHeight();
            }
            if (speed.x()>0) {
                if (in && loc.y()>322-blocks.get(i).getBoxHeight()) loc.x(-blocks.get(i).getPosX()-152);
            } else {
                if (in && loc.y()>322-blocks.get(i).getBoxHeight()) loc.x(-blocks.get(i).getPosX()-112+blocks.get(i).getBoxWidth());
            }
        }
        for (int i = 0; i < platforms.size(); i++) {
            boolean in = loc.x()+100>-platforms.get(i).getPosX()-52 && loc.x()+100<-platforms.get(i).getPosX()-12+platforms.get(i).getPlatformWidth();
            if (in && !down){
                if (loc.y()<=322-platforms.get(i).getPosY() && 322-platforms.get(i).getPosY()<=maxY) {
                    maxY = 322-platforms.get(i).getPosY();
                    suc = true;
                }
            }
        }
        if (!suc){
            maxY = 322;
        }
        for (int i = 0; i < spikes.size(); i++) {
            boolean in = loc.x()+100>-spikes.get(i).getPosX()-52 && loc.x()+100<-spikes.get(i).getPosX()-12+spikes.get(i).getWidth();
            if (in && loc.y()>306){
                health-=3;
                break;
            }
        }
        for (int i = 0; i < fruits.size(); i++) {
            boolean in = loc.x()+100>-fruits.get(i).getPosX()-52 && loc.x()+100<-fruits.get(i).getPosX()+20;
            if (in && loc.y()+fruits.get(i).getPosY()>=-32 && loc.y()+fruits.get(i).getPosY()<=32 && health!=100){
                health+=fruits.get(i).getHp();
                if (health>100) health = 100;
                return i;
            }
        }
        return -1;
    }

    public void setSpeedY(float speedY) {
        this.speed.y(speedY);
    }

    public void moveX(boolean forward) {
        if (forward) {
            speed.x(5);
            if (minX<(int)loc.x()-200) minX = (int)loc.x()-200;
        } else {
            if (minX < loc.x()) speed.x(-5);
        }
    }

    public int getMinX() {
        return minX;
    }

    public void setMultiply(int multiply) {
        this.multiply = multiply;
    }

    public void slide() {
        down = true;
        if(loc.y()!=maxY) sliding = false; else sliding = true;
        if (sliding) multiply=multiply*0.98*Math.abs(speed.x())/5;
        if (multiply<0.25) multiply = 0.25;
    }
    public void standUp(){
        sliding = down = false;
        multiply=1;
    }
    public void drawTexture(double idlePos){
        DrawRectangle(10,10,(int)(health*2),20,RED);
        DrawRectangleLines(10,10,200,20,BLACK);
        if(sliding){
            DrawTextureEx(slide,new Jaylib.Vector2(100,loc.y()),0,2,WHITE);
            return;
        }
        if (speed.y()<0 && jumpCount == 2 &&  idlePos <=6) {
            DrawTextureTiled(doubleJump,new Jaylib.Rectangle((int)idlePos%6*32,0,32+(int)idlePos%6*32,32),new Jaylib.Rectangle(0,0,64,64),new Jaylib.Vector2(-100,-loc.y()),0,2,WHITE);
            return;
        }
        if (speed.y()<0) {
            DrawTextureEx(jump,new Jaylib.Vector2(100,loc.y()),0,2,WHITE);
            return;
        }
        if (speed.y()>0) {
            DrawTextureEx(fall,new Jaylib.Vector2(100,loc.y()),0,2,WHITE);
            return;
        }
        if (Math.abs(speed.x())>0){
            DrawTextureTiled(run,new Jaylib.Rectangle((int)idlePos%12*32,0,32+(int)idlePos%12*32,32),new Jaylib.Rectangle(0,0,64,64),new Jaylib.Vector2(-100,-loc.y()),0,2,WHITE);
            return;
        }
        DrawTextureTiled(idle,new Jaylib.Rectangle((int)idlePos%11*32,0,32+(int)idlePos%11*32,32),new Jaylib.Rectangle(0,0,64,64),new Jaylib.Vector2(-100,-loc.y()),0,2,WHITE);
    }
    public boolean isAlive() {
        return health >= 0;
    }
    public void unloadTextures(){
        UnloadTexture(idle);
        UnloadTexture(run);
        UnloadTexture(jump);
        UnloadTexture(doubleJump);
        UnloadTexture(fall);
        UnloadTexture(slide);
    }

    public Texture getRun() {
        return run;
    }
}