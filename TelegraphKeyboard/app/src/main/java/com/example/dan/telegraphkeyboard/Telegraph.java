package com.example.dan.telegraphkeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;

public class Telegraph extends InputMethodService implements KeyboardView.OnKeyboardActionListener
{
    private MorseListener state = new MorseListener();
    private Timer scheduler = new Timer();
    private AudioTrack player;
    private Keyboard.Key key;
    private boolean isBackspace = false;

    public View onCreateInputView() {
        KeyboardView kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        Keyboard keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        kv.setPreviewEnabled(false);
        key = keyboard.getKeys().get(0);
        return kv;
    }

    public void onPress(int primaryCode) {
        TimerTask backspace = new TimerTask() {
            public void run() {
                isBackspace = true;
                getCurrentInputConnection().deleteSurroundingText(1, 0);
            }
        };

        if (player != null) player.release();
        player = TonePlayer.generate();
        player.play();

        isBackspace = false;
        state.press(System.currentTimeMillis());
        key.label = state.getCurrentState();

        scheduler.cancel();
        scheduler = new Timer();
        scheduler.scheduleAtFixedRate(backspace, 7 * MorseListener.DOT, 2 * MorseListener.DOT);
    }

    public void onRelease(int primaryCode) {
        TimerTask space = new TimerTask() {
            public void run() {
                if (!isBackspace)
                    getCurrentInputConnection().commitText(" ", 1);
            }
        };

        TimerTask print = new TimerTask() {
            public void run() {
                String dotsAndDashes = state.getCurrentState();

                if (dotsAndDashes != null && !isBackspace) {
                    String character = Translator.translate(dotsAndDashes);
                    getCurrentInputConnection().commitText(character, 1);
                    scheduler.schedule(space, 4 * MorseListener.DOT);
                }
            }
        };

        player.release();
        state.release(System.currentTimeMillis());
        key.label = state.getCurrentState();

        scheduler.cancel();
        scheduler = new Timer();
        scheduler.schedule(print, 3 * MorseListener.DOT);
    }

    // random interface crap
    public void onKey(int primaryCode, int[] keyCodes) { }
    public void onText(CharSequence text) { }
    public void swipeLeft() { }
    public void swipeRight() { }
    public void swipeDown() { }
    public void swipeUp() { }
}
