package es.ucm.fdi.tp.practica5.view.sound;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class MakeSound implements LineListener  {
	
	/**
     * this flag indicates whether the playback completes or not.
     */
	boolean playCompleted;

	/**
	 * Funcion statica que inicializa los efectos musicales
	 * @param fileName String del nombre del archivo musical
	 */
	public static void PlaySound(final String fileName) {
		File sound = new File(fileName);

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();

			Thread.sleep(clip.getMicrosecondLength() / 1000);

		} catch (Exception e) {
			System.out.println("play sound error: " + e.getMessage() + " for " + fileName);
		}
	}
	/**
	 * Funcion statica que sincroniza los efectos musicales con los eventos que los desembocan
	 * @param fileName String del nombre del archivo musical
	 */
	public static synchronized void RunPlaySound(final String fileName) {
		File sound = new File(fileName);

		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(sound));
					clip.start();
				} catch (Exception e) {
					System.out.println("play sound error: " + e.getMessage() + " for " + fileName);
				}
			}
		}).start();
	}
	
	/**
	 * Funcion statica que hace que la musica comience y no pare hasta que finaliza la aplicación
	 * @param fileName String del nombre del archivo musical
	 * @param e es un ActionEvent para el catch
	 */
	public static synchronized void RunLoopPlaySound(final String fileName, ActionEvent e) {
		File sound = new File(fileName);
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(sound));
						clip.loop(-1);
					
				} catch (Exception e) {
					System.out.println("play sound error: " + e.getMessage() + " for " + fileName);
				}
			}
		}).start();
	}
	
	/**
     * Listens to the START and STOP events of the audio line.
     */
	@Override
	public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
        
        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");
             
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }
    }

		
}
    