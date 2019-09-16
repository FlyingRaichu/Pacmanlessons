package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;

/**
 *
 * This class should contain all your game logic
 */

public class Game {
    //context is a reference to the activity
    private Context context;
    private int points = 0; //how points do we have

    //bitmap of the pacman
    private Bitmap pacBitmap;
    private Bitmap coinBitmap;
    Random rnd = new Random();
    int rintx = rnd.nextInt(1000);
    int rinty = rnd.nextInt(1000);
    //textview reference to points
    private TextView pointsView;
    private int pacx, pacy;
    //the list of goldcoins - initially empty
    public ArrayList<GoldCoin> coins = new ArrayList<>();
    //a reference to the gameview
    private GameView gameView;
    private int h,w; //height and width of screen

    public Game(Context context, TextView view)
    {
        this.context = context;
        this.pointsView = view;
        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacman);
        coinBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldcoin);
    }

    public void setGameView(GameView view)
    {
        this.gameView = view;
    }

    //TODO initialize goldcoins also here
    public void newGame()
    {
        pacx = 50;
        pacy = 400; //just some starting coordinates
        //reset the points
        points = 0;
        pointsView.setText(context.getResources().getString(R.string.points)+" "+points);

        coins.add(new GoldCoin(rintx, rinty, false));
        rintx = rnd.nextInt(1000);
        rinty = rnd.nextInt(1000);
        coins.add(new GoldCoin(rintx, rinty, false));
        rintx = rnd.nextInt(1000);
        rinty = rnd.nextInt(1000);
        coins.add(new GoldCoin(rintx, rinty, false));

        gameView.invalidate(); //redraw screen
    }

    public void setSize(int h, int w)
    {
        this.h = h;
        this.w = w;
    }

    public void movePacmanRight(int pixels)
    {
        //still within our boundaries?
        if (pacx+pixels+pacBitmap.getWidth()<w) {
            pacx = pacx + pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    public void movePacmanLeft(int pixels) {
        if (pacx + pixels > 0) {
            pacx = pacx - pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    public void movePacmanDown (int pixels) {
        if (pacy + pixels + pacBitmap.getWidth() <h) {
            pacy = pacy + pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    public void movePacmanUp (int pixels) {
        if (pacy + pixels >0) {
            pacy = pacy - pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    public void doCollisionCheck()
    {
        for (GoldCoin coin: coins) {
            int centerpacx = pacx + pacBitmap.getWidth()/2;
            int centerpacy = pacy + pacBitmap.getHeight()/2;

            int centercoinx = coin.getCoinx() + coinBitmap.getWidth()/2;
            int centercoiny = coin.getCoiny() + coinBitmap.getHeight()/2;

            double distance = Math.sqrt(Math.pow((centerpacy - centercoiny), 2) + Math.pow((centerpacx - centercoinx), 2));

            if (distance < 80 && !coin.IsPickedUp()) {
                coin.setIsPickedUp(true);
                points++;
                pointsView.setText(context.getResources().getString(R.string.points)+" "+points);
            }

            if (doWinCheck()) {
                Toast.makeText(context, "You win!", Toast.LENGTH_LONG).show();
            }
        }
    }


    public boolean doWinCheck() {
        for (GoldCoin coin : coins) {
            if (!coin.IsPickedUp()) {
                return false;
            }
        }
        return true;
    }


    public int getPacx()
    {
        return pacx;
    }

    public int getPacy()
    {
        return pacy;
    }

    public int getPoints()
    {
        return points;
    }

    public ArrayList<GoldCoin> getCoins()
    {
        return coins;
    }

    public Bitmap getPacBitmap()
    {
        return pacBitmap;
    }

    public Bitmap getcoinBitmap()
    {
        return coinBitmap;
    }


}
