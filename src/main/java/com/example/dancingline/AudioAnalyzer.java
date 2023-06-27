package com.example.dancingline;

//import org.apache.commons.math3.transform.*;
//import org.apache.commons.math3.complex.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioAnalyzer extends Thread{
    private AudioFormat format;
    private DataLine.Info info;
    private TargetDataLine line;
    private double lastVolume;
    private final int SAMPLE_RATE = 44100;
    private final int SAMPLE_SIZE_IN_BITS = 16;


    public AudioAnalyzer(){
        try {
            // Apri il microfono
            format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, 1, true, true);
            info = new DataLine.Info(TargetDataLine.class, format);
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void audioDetect() {
        // Definisci la dimensione del buffer
        int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
        byte[] buffer = new byte[bufferSize];

        int bytesRead = line.read(buffer, 0, buffer.length);

        // Calcola il volume
        long sum = 0;
        for (int i = 0; i < bytesRead; i += 2) {
            // Converti i byte in un valore intero
            int sample = (buffer[i + 1] << 8) | (buffer[i] & 0xFF);
            sum += (long) sample * sample;
        }
        double rms = Math.sqrt(sum / (bytesRead / 2));
        lastVolume = 20 * Math.log10(rms);
    }

    public double getVolume(){
        return lastVolume;
    }

/*
 private static double computeFrequency(byte[] buffer, int bytesRead, float sampleRate) {
        // Converte i byte del buffer in array di campioni
        double[] samples = new double[bytesRead / 2];
        for (int i = 0; i < bytesRead; i += 2) {
            int sample = (buffer[i + 1] << 8) | (buffer[i] & 0xFF);
            samples[i / 2] = sample;
        }

        // Applica la Trasformata di Fourier Veloce (FFT)
        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] frequencies = transformer.transform(samples, TransformType.FORWARD);

        // Trova l'indice del picco di ampiezza massima
        int peakIndex = -1;
        double peakAmplitude = 0.0;
        for (int i = 0; i < frequencies.length / 2; i++) {
            double amplitude = frequencies[i].abs();
            if (amplitude > peakAmplitude) {
                peakIndex = i;
                peakAmplitude = amplitude;
            }
        }

        // Calcola la frequenza corrispondente all'indice del picco
        double frequency = peakIndex * sampleRate / samples.length;

        return frequency;
    }
 */

    @Override
    public void run() {
        while (true){
            audioDetect();
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
