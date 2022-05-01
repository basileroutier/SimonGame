/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 * Make a sound effect
 * @author basile
 */
public class SoundEffect {

    private Synthesizer synth;

    /**
     * Basic constructor that initialize the synthetizer and open it
     * @throws MidiUnavailableException 
     */
    public SoundEffect() throws MidiUnavailableException {
        synth = MidiSystem.getSynthesizer();
        synth.open();
    }

    /**
     * Make a sound with a specific notesound
     * Check if it is silent 
     * if not it will make the specific sound for a duration
     * @param noteSound int: note of the sound
     * @param isSilent boolean : if it is silent
     */
    public void makeSound(int noteSound, boolean isSilent) {
        if (isSilent) {
            var channel = synth.getChannels()[0];
            channel.noteOn(noteSound, 80);
            var pause = new PauseTransition(Duration.millis(500));
            pause.setOnFinished(event -> channel.noteOff(noteSound)
            );
            pause.play();
        }
    }

}
