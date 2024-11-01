package projects.moyethon.dto;

import projects.moyethon.domain.Music;

public record MusicDTO(String title, String artist, String previewUrl, String imagePath) {

    public Music toMusic(){
        return Music.builder()
                .title(this.title)
                .artist(this.artist)
                .previewUrl(this.previewUrl)
                .albumImage(this.imagePath).build();
    }
}
