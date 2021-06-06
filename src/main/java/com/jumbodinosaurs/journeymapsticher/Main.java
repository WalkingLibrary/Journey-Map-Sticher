package com.jumbodinosaurs.journeymapsticher;

import com.jumbodinosaurs.devlib.log.LogManager;
import com.jumbodinosaurs.devlib.util.GeneralUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        int journeyMapRegionImageSize = 512;
        if(args.length < 3)
        {
            System.out.println("Usage: java -jar <jar name> <x> <z> <max width>");
            return;
        }
    
        int x, z, maxWidth;
        try
        {
            x = Integer.parseInt(args[0]);
            z = Integer.parseInt(args[1]);
        
        
            maxWidth = Integer.parseInt(args[2]);
        
            if(maxWidth <= 16 * 32)
            {
                System.out.println("width Not Big Enough");
                return;
            }
        }
        catch(Exception e)
        {
    
            System.out.println("Arguments Given were not Valid");
            return;
        }
    
        int xOffset, zOffset;
    
    
        xOffset = x - (maxWidth / 2);
        zOffset = z - (maxWidth / 2);
    
    
        int regionRange = (maxWidth / 16 / 32) + 2;
    
        int regionX, regionZ;
    
    
        regionX = x / 16 / 32;
        regionZ = z / 16 / 32;
    
        BufferedImage largerImage = new BufferedImage(maxWidth, maxWidth, BufferedImage.TYPE_3BYTE_BGR);
    
        System.out.println("Making Image at X: " + x + " Z: " + z + " and " + maxWidth + " Blocks Wide");
        File journeyMapDir = GeneralUtil.checkFor(GeneralUtil.userDir, "Input", true);
        for(File journeyMapImage : journeyMapDir.listFiles())
        {
            try
            {
    
    
                int currentFilesRegionX, currentFilesRegionZ;
    
                //PArsing of the File name
                String fileName = journeyMapImage.getName();
                fileName = fileName.split("\\.")[0];
                fileName = fileName.split(",")[0] + " " + fileName.split(",")[1];
                Scanner scanner = new Scanner(fileName);
                //Gets the current files region
                currentFilesRegionX = scanner.nextInt();
                currentFilesRegionZ = scanner.nextInt();
    
    
                //Culling before reading in the image
    
                //if the difference between the specified area x and the current file is greater than the range
                if(!(Math.abs(regionX - currentFilesRegionX) < regionRange))
                {
                    continue;
                }
    
                if(!(Math.abs(regionZ - currentFilesRegionZ) < regionRange))
                {
                    continue;
                }
    
                //Add Image to Bigger Image
                byte[] imageBytes = readPhoto(journeyMapImage);
    
    
                BufferedImage currentImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
    
                for(int rows = 0; rows < journeyMapRegionImageSize; rows++)
                {
                    for(int columns = 0; columns < journeyMapRegionImageSize; columns++)
                    {
                        int colorAT = currentImage.getRGB(rows, columns);
            
                        int offsetX, offsetZ;
            
                        offsetX = ((currentFilesRegionX * 32 * 16) + rows) - xOffset;
                        offsetZ = ((currentFilesRegionZ * 32 * 16) + columns) - zOffset;
            
                        if(offsetX >= 0 && offsetX <= largerImage.getWidth() - 1)
                        {
                            if(offsetZ >= 0 && offsetZ <= largerImage.getHeight() - 1)
                            {
                                largerImage.setRGB(offsetX, offsetZ, colorAT);
                            }
                        }
            
                    }
                }
            
            
            }
            catch(Exception e)
            {
                e.printStackTrace();
                continue;
            }
        
        
        }
        File outputFile = GeneralUtil.checkFor(GeneralUtil.userDir, "output.png");
    
        try
        {
            System.out.println("Image Saved at " + outputFile.getAbsolutePath());
            ImageIO.write(largerImage, "png", outputFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        
        }
    
    
    }
    
    public static byte[] readPhoto(File file)
    {
        try
        {
            byte[] imageBytes = new byte[(int) file.length()];
            InputStream imageStream = new BufferedInputStream(new FileInputStream(file));
            imageStream.read(imageBytes, 0, imageBytes.length);
            imageStream.close();
            return imageBytes;
        }
        catch(Exception e)
        {
            LogManager.consoleLogger.error("Error Reading Photo", e);
            
        }
        return null;
    }
    
}
