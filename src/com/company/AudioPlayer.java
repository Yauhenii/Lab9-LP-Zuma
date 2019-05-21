package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    AudioInputStream audioInputStream;
    Clip clip;

    public AudioPlayer(String filePath)
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        // create clip reference
        clip = AudioSystem.getClip();
        // open audioInputStream to the clip
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void play() {
        clip.start();
    }
}
