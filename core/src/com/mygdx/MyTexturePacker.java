package com.mygdx;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MyTexturePacker {
    public static void main(String[] args) {
        // Input directory containing the images to be packed
        String inputDir = "C:\\Users\\onie2\\Documents\\GDX_Project\\assets\\single";

        // Output directory where the packed texture atlas will be saved
        String outputDir = "C:\\Users\\onie2\\Documents\\GDX_Project\\assets\\pack";

        // Name of the packed texture atlas file
        String packFileName = "textures_single";

        // Configure settings for the TexturePacker
        TexturePacker.Settings settings = new TexturePacker.Settings();

        // Set texture format (optional)
//        settings.format = TexturePacker.Format.RGBA8888;

        // Set edge padding (optional)
        settings.edgePadding = true;
        settings.paddingX = 2;
        settings.paddingY = 2;

//        // Set extrude (optional)
//        settings.duplicatePadding = true;
//        settings.edgePadding = true;
//
//        // Set trim mode (optional)
//        settings.trim = true;
//        settings.stripWhitespaceX = true;
//        settings.stripWhitespaceY = true;

        // Create a new TexturePacker instance with the specified settings
        TexturePacker.process(settings, inputDir, outputDir, packFileName);

        inputDir = "C:\\Users\\onie2\\Documents\\GDX_Project\\assets\\ui";
        packFileName = "ui";
        TexturePacker.process(settings, inputDir, outputDir, packFileName);

        // Print a message indicating successful packing
        System.out.println("Texture packing completed successfully!");
    }
}
