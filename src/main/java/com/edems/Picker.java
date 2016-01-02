package com.edems;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by edems on 2016.01.02..
 */
public class Picker extends Thread {

    private Player mPicker;
    private Player mPicked;

    private float mDistance;
    private boolean mFly;

    public Picker(Player picker, Player picked) {
        mPicker = picker;
        mPicked = picked;

        mDistance = 6;
        mFly = true;
    }

    @Override
    public void run() {
        if( mPicker == null || mPicked == null )
            return;

        mPicked.setVelocity(new Vector(0, 0, 0)); // make a tmp?
        while( mFly ) {
           // mPicked.getLocation().setX(mPicker.getLocation().getX() + mPicker.getLocation().getDirection().getX() * mDistance);

            mPicked.teleport(mPicker.getLocation().clone().add(mPicker.getLocation().getDirection().multiply(mDistance)));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void Drop() {
        mFly = false;
    }

    public void setDistance(float distance) {
        mDistance = distance;
    }
}
