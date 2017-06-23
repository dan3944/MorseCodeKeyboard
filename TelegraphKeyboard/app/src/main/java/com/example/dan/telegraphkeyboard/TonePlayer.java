package com.example.dan.telegraphkeyboard;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;

public class TonePlayer
{
    private static int duration = 3;
    private static int sampleRate = 8000;
    private static int numSamples = duration * sampleRate;
    private static double[] sample = new double[numSamples];
    private static double freqOfTone = 880;
    private static byte[] generatedSnd = new byte[2 * numSamples];
    private static Handler handler = new Handler();

    static {
        for (int i = 0; i < numSamples; i++)
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));

        int idx = 0;
        for (final double dVal : sample) {
            final short val = (short) (dVal * 32767);
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }

    public static AudioTrack generate() {
        AudioTrack player = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                                    AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length, AudioTrack.MODE_STATIC);
        player.write(generatedSnd, 0, generatedSnd.length);
        return player;
    }
}
