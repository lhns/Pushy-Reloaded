import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import javax.swing.*;

/**
 * Write a description of class LoadButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LoadButton extends SaveButton
{
    public void edited() {
        JFileChooser filechooser = new JFileChooser();
        if (filechooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
            LevelFile lvlFile = new LevelFile(filechooser.getSelectedFile(), getWorld());
            lvlFile.load();
        }
    }
}
