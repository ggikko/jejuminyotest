package ggikko.me.jejuminyo;

/**
 * Created by ggikko on 16. 2. 29..
 */
public class DataObject {

    private String song_name;
    private String soundUrl;
    private String song_minute;
    private String song_second;
    private String song_content;

    public DataObject(String song_name, String soundUrl, String song_minute, String song_second, String song_content) {
        this.song_name = song_name;
        this.soundUrl = soundUrl;
        this.song_minute = song_minute;
        this.song_second = song_second;
        this.song_content = song_content;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }

    public String getSong_minute() {
        return song_minute;
    }

    public void setSong_minute(String song_minute) {
        this.song_minute = song_minute;
    }

    public String getSong_second() {
        return song_second;
    }

    public void setSong_second(String song_second) {
        this.song_second = song_second;
    }

    public String getSong_content() {
        return song_content;
    }

    public void setSong_content(String song_content) {
        this.song_content = song_content;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "song_name='" + song_name + '\'' +
                ", soundUrl='" + soundUrl + '\'' +
                ", song_minute='" + song_minute + '\'' +
                ", song_second='" + song_second + '\'' +
                ", song_content='" + song_content + '\'' +
                '}';
    }
}
