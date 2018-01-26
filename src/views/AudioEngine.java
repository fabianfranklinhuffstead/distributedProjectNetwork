package views;

import controllers.GlobalResources;
import models.LoadingFiles.FileLoaderSounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// The sound engine for the boats application
public class AudioEngine
{
    // Background music sound
    private Clip _MusicSegment;
    
    // boats on impact sound
    private Clip _BoatImpactSegment;

    // boats on crash sound
    private Clip _BoatCrashSegment;
    
    // boats on acceleration sound
    private Clip _BoatAccelerationSegment;

    // The sound engine for the boats application
    public AudioEngine()
    {
        //Simple try catch for error catching init sound engine
    		try
        {
            _MusicSegment = AudioSystem.getClip();
            _BoatImpactSegment = AudioSystem.getClip();
            _BoatCrashSegment = AudioSystem.getClip();
            _BoatAccelerationSegment = AudioSystem.getClip();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //Open the sound files as audio streams
        AudioInputStream _MusicAudioStream = FileLoaderSounds.GetMusic();
        AudioInputStream _BoatAudioImpact = FileLoaderSounds.GetAudio();
        AudioInputStream _BoatAudioCrash = FileLoaderSounds.GetCrashAudio();
        AudioInputStream _BoatsAccelerationAudio = FileLoaderSounds.GetAccelerationAudio();

        try
        {
            //Assign audio streams to clips
            _MusicSegment.open(_MusicAudioStream);
            _BoatImpactSegment.open(_BoatAudioImpact);
            _BoatCrashSegment.open(_BoatAudioCrash);
            _BoatAccelerationSegment.open(_BoatsAccelerationAudio);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //Background music set to true by default
        if (GlobalResources.ATTRIBUTESFORDEFAULT_MusicState)
        {
            SetdMusicAudio(true);
        }
    }


    // Boolean property for background music playing
    public void SetdMusicAudio(Boolean play)
    {
        if (play)
        {
            // if true
            StartMusicAudio();
        }
        else
        {
            // else false
            StopMusicAudio();
        }
    }

    // This is the sound method when boat collides with non boat object
    public void AudioPlayBoatImpact()
    {
        _BoatImpactSegment.setFramePosition(0);
        _BoatImpactSegment.start();
    }

    // This is the sound method when boat collides with boat object
    public void AudioBoatCrash()
    {
        _BoatCrashSegment.setFramePosition(0);
        _BoatCrashSegment.start();
    }

    // This is the sound which is played when the boat is accelerating
    public void AudioBoatAccelerator(Boolean play)
    {
        if (play)
        {
            if (!_BoatAccelerationSegment.isActive())
            {
                _BoatAccelerationSegment.setFramePosition(0);
                _BoatAccelerationSegment.start();
            }
        }
        else
        {
            _BoatAccelerationSegment.stop();
        }
    }

    // This is the method of stopping background music
    private void StopMusicAudio()
    {
        _MusicSegment.stop();
    }

    // This is the method of starting the background music
    private void StartMusicAudio()
    {
        if (!_MusicSegment.isRunning())
        {
                _MusicSegment.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

}
