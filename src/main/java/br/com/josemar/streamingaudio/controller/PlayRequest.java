package br.com.josemar.streamingaudio.controller;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/play")
public class PlayRequest {

    Logger LOGGER = LoggerFactory.getLogger(PlayRequest.class);

    File diretorioTemp = new File(System.getProperty("java.io.tmpdir"));
    YoutubeDownloader downloader = new YoutubeDownloader();

    @RequestMapping("/{idMusica}")
    public ResponseEntity play(@PathVariable("idMusica") String idMusica) throws IOException,YoutubeException{

            LOGGER.error(">>> Inciando execucao do metodo play com o IdMusica:" + idMusica);

            LOGGER.error(">>> Inciando instancia da varivel video");
            YoutubeVideo video = downloader.getVideo(idMusica);
            LOGGER.error(">>> Encerrando instancia da varivel video");

            LOGGER.error(">>> Inciando instancia da varivel videobaixado");
            File videoBaixado = video.download(video.videoWithAudioFormats().get(0),diretorioTemp);
            LOGGER.error(">>> Encerrando instancia da varivel videoBaixado");

            return new ResponseEntity(new FileInputStream(videoBaixado).readAllBytes(),HttpStatus.OK);

    };


}
