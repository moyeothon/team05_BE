package projects.moyethon.dto;

import projects.moyethon.domain.Music;

public record MusicDTO(String title, String artist, String previewUrl, String imagePath) {

    public Music toMusic(){
        return Music.builder()
                .title(title)
                .artist(artist)
                .previewUrl(previewUrl)
                .albumImage(imagePath).build();
    }
}
