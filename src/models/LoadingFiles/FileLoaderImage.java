package models.LoadingFiles;

import sun.tools.jar.Main;

import javax.imageio.ImageIO;

import controllers.GlobalResources;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

//Class for image loading
public class FileLoaderImage
{

    // Image store for boat crashed to be used once
    private static Image _Stored_Crash_Image = null;

    // Background image loader
    public static Image ImageForBackground()
    {
        return ReaderForImages(GlobalResources.MAINSELECTIONMENU_ImageBackground);
    }

    // Keyboard background loads image and returns the result
    public static Image ImageForKeyboard()
    {
        Image returnedKeyBoardImage;
        String file = GlobalResources.ATTRIBUTESFORHELP_PrefixSelctionImagePath + GlobalResources.ATTRIBUTESFORHELP_KeyBoardPrefix + Integer.toString(GlobalResources.In_Game_Player) + GlobalResources.ATTRIBUTESFORHELP_ImagesPrefixExt;
        returnedKeyBoardImage = ReaderForImages(file);
        return returnedKeyBoardImage;
    }

    // Map selection loads image and returns the result
    public static Image ImageForMapSelection(String MapTitle)
    {
        Image returnedMapSelectionImage;
        String file = GlobalResources.ATTRIBUTESFORMAP_PrefixMapFilePath + GlobalResources.ATTRIBUTESFORMAP_PrefixMapName + MapTitle + GlobalResources.ATTRIBUTESFORMAP_PrefixMapsExtension;
        returnedMapSelectionImage = ReaderForImages(file);
        return returnedMapSelectionImage;
    }

    // Small boat image loader and returns result
    public static Image ImageForLaunchDisplay(int IdentifierForBoat)
    {
        Image ImageForLaunchDisplay;
        String file = GlobalResources.ATTRIBUTESFORBOAT_DefaultFilePathPrefix + Integer.toString(IdentifierForBoat) + GlobalResources.ATTRIBUTESFORBOAT_DefaultFileNamePrefix ;
        ImageForLaunchDisplay = ReaderForImages(file);
        return ImageForLaunchDisplay;
    }

    // Arrow selector loader and returns the result
    public static Image ImageForArrow(boolean ArrowSelector)
    {
        Image returnedArrowImage;
        String file;
        if(ArrowSelector)
        {
            file = GlobalResources.PREPDISPLAYATTRIBUTES_ArrowLeft;
        }
        else
        {
            file = GlobalResources.PREPDISPLAYATTRIBUTES_ArrowRight;
        }
        returnedArrowImage = ReaderForImages(file);
        return returnedArrowImage;
    }

    // File reader and Error checker for file if return result is null
    public static Image ReaderForImages(String fileName)
    {
        Image returnedReadImage = null;
        try {
            InputStream inputStream = Main.class.getResourceAsStream(fileName);
            returnedReadImage = ImageIO.read(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnedReadImage;
    }

    // Loads boat Images and and stores in an array starting from 0
    public static Image[] ImageLoad(int ArrayOFBoatImages)
    {
        ArrayList<Image> returnedArrayListOfImages = new ArrayList<>();
        String streamStart = GlobalResources.BOATSINGAME_BoatInGameFilePath + Integer.toString(ArrayOFBoatImages) + "/" + GlobalResources.BOATSINGAME_BoatAnimationBoatPrefix;

        Stream.of(GlobalResources.BOATSINGAME_AnglePrefixes).forEach(s ->
        {
            String SI = streamStart + s + GlobalResources.BOATSINGAME_BoatImageExtension;
            returnedArrayListOfImages.add(ReaderForImages(SI));
        });

        return returnedArrayListOfImages.toArray(new Image[returnedArrayListOfImages.size()]);
    }

    // Loads and returns the cached crash boat images
    public static Image ReturnCrashImage()
    {
        if(_Stored_Crash_Image == null)
        {
            _Stored_Crash_Image = ReaderForImages(GlobalResources.BOATSINGAME_CrashImage);
        }

        return _Stored_Crash_Image;
    }
}
