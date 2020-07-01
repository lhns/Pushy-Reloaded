import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import javax.swing.*;

/**
 * Write a description of class SaveButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SaveButton extends Tool
{
    public void edited() {
        JFileChooser filechooser = new JFileChooser();
        if (filechooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
            File selected = filechooser.getSelectedFile();
            String ext = "";
            if (selected.getAbsolutePath().lastIndexOf(".")>-1) ext = selected.getAbsolutePath().substring(selected.getAbsolutePath().lastIndexOf("."));
            if (!ext.equals(".pe") && !ext.equals(".lev")) {
                selected = new File(selected.getAbsolutePath()+".pe");
            }
            LevelFile lvlFile = new LevelFile(selected, getWorld());
            lvlFile.save();
        }
    }
}
