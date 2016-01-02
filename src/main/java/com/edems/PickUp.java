package com.edems;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by edems on 2016.01.02..
 */
public class PickUp extends JavaPlugin {

    private Picker mPicker;

    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled!");

        mPicker = null;
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( command.getName().equalsIgnoreCase("pickup") ) {
            doPickUp(sender); return true;
        } else if( command.getName().equalsIgnoreCase("drop") ) {
            doDrop(sender); return true;
        } else if( command.getName().equalsIgnoreCase("dist") ) {
            doDistance(sender, args); return true;
        }

        return false;
    }

    private void doPickUp(CommandSender sender) {
        if( !(sender instanceof Player) ) {
            getLogger().info("You must be a Player to use this command!"); return;
        }

        for(Player player : ((Player) sender).getWorld().getPlayers()) {
            getLogger().info("p: "+ sender.getName());

            if( !player.isOnline() || player == sender || !(((Player) sender).canSee(player)) )
                continue;

            if( mPicker == null ) {
                mPicker = new Picker((Player) sender, player);
                mPicker.start();
            } else {
                mPicker.Drop();
                mPicker = new Picker((Player) sender, player);
                mPicker.start();
            }

            break;
        }
    }

    private void doDrop(CommandSender sender) {
        if( !(sender instanceof Player) ) {
            getLogger().info("You must be a Player to use this command!"); return;
        }

        if( mPicker != null ) {
            mPicker.Drop();
            mPicker = null;
        }
    }

    private void doDistance(CommandSender sender, String args[]) {
        if( !(sender instanceof Player) ) {
            getLogger().info("You must be a Player to use this command!"); return;
        }

        if( args.length != 1 ) {
            getLogger().info("Too few or too much arguments!"); return;
        }

        if( mPicker != null ) {
            mPicker.setDistance(Float.parseFloat(args[0]));
        }
    }

}
