package br.com.josemar.streamingaudio.util;

import ws.schild.jave.*;

import java.io.File;
import java.io.IOException;

public class Converter {

    public static File converter(File videoBaixado) throws EncoderException, IOException {

        File musicaConvertida = File.createTempFile("temp",null);
        musicaConvertida.deleteOnExit();

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(new MultimediaObject(videoBaixado), musicaConvertida, attrs);

        return musicaConvertida;
    }
}
