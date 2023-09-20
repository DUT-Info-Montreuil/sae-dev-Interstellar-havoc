
package com.application.S2_dev.modele.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;

public class Sound extends Thread {

    private URL fileURL; // The URL of the file to play
    private boolean loop = false;

    public Sound(URL fileURL) {
        this.fileURL = fileURL;
    }

    public void setLoop(boolean value) {
        this.loop = value;
    }

    /**
     * Plays the sound
     */
    public void run() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(fileURL);
            AudioFormat format = stream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);

            if (loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();
            Thread.sleep(100);

            while (clip.isRunning() || loop) {
                Thread.sleep(100);
            }

            clip.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
