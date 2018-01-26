package models.LoadingFiles;

import sun.tools.jar.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import controllers.GlobalResources;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

//Audio file loader class
public class FileLoaderSounds
{
    // Gets background music audio stream and returns
    public static AudioInputStream GetMusic()
    {
        return GetGlobalSounds(GlobalResources.ATTRIBUTESFORAUDIO_MusicFilePath);
    }

    // Gets boat impact audio stream and returns
    public static AudioInputStream GetAudio()
    {
        return GetGlobalSounds(GlobalResources.ATTRIBUTESFORAUDIO_SoundFilePath);
    }

    // Gets boat crash audio stream and returns
    public static AudioInputStream GetCrashAudio()
    {
        return GetGlobalSounds(GlobalResources.ATTRIBUTESFORAUDIO_CrashFilePath);
    }

    // Gets boat rev/acceleration audio stream and returns
    public static AudioInputStream GetAccelerationAudio()
    {
        return GetGlobalSounds(GlobalResources.ATTRIBUTESFORAUDIO_BoatRevFilePath);
    }

    // Gets audio stream and returns if not returns null
    private static AudioInputStream GetGlobalSounds(String globalAudio)
    {
        AudioInputStream AudioInputStreamResult = null;
        try
        {
            InputStream is = Main.class.getResourceAsStream(globalAudio);
            BufferedInputStream ba = new BufferedInputStream(is);
            AudioInputStreamResult = AudioSystem.getAudioInputStream(ba);
        } catch (UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
        }

        return AudioInputStreamResult;
    }


}
