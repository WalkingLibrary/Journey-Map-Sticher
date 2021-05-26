package com.jumbodinosaurs.journeymapsticher;

import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        if(args.length < 4)
        {
            System.out.println("Usage: java -jar <jar name> <journey map image dir> <x> <z> <max width>");
            return;
        }
        
        int x, z, maxWidth;
        File journeyMapDir;
        try
        {
            journeyMapDir = new File(args[0]);
            x = Integer.parseInt(args[1]);
            z = Integer.parseInt(args[2]);
            maxWidth = Integer.parseInt([3]);
        }
        catch(Exception e)
        {
            
            System.out.println("Arguments Given were not Valid");
            return;
        }
        
        Point
    
        for(File journeyMapImage: journeyMapDir.listFiles())
        {
        
        }
        
        
    }
}
