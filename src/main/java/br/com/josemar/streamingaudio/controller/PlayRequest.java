package br.com.josemar.streamingaudio.controller;

import br.com.josemar.streamingaudio.util.Converter;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ws.schild.jave.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/play")
public class PlayRequest {

    File diretorioTemp = new File(System.getProperty("java.io.tmpdir"));
    YoutubeDownloader downloader = new YoutubeDownloader();

    @RequestMapping("/{idMusica}")
    public ResponseEntity play(@PathVariable("idMusica") String idMusica){

        try {
            YoutubeVideo video = downloader.getVideo(idMusica);
            File videoBaixado = video.download(video.videoWithAudioFormats().get(0),diretorioTemp);
            videoBaixado.deleteOnExit();

            File musicaConvertida = Converter.converter(videoBaixado);

            InputStreamResource stream = new InputStreamResource(new FileInputStream(musicaConvertida));

            return new ResponseEntity(stream,HttpStatus.OK);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (YoutubeException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    };


}
