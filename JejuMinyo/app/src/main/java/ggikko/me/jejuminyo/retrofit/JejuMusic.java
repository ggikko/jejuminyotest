package ggikko.me.jejuminyo.retrofit;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ggikko on 16. 2. 29..
 */
public interface JejuMusic {

    final String BASE_URL = "http://www.jeju.go.kr/";

    @GET("rest/JejuFolksongService/getJejuFolksongList")
    Call<JejuMusicObject> getJejuMusic();

    class Factory{

        private static JejuMusic service;

        public static JejuMusic getInstance(){

            if(service ==null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                return service = retrofit.create(JejuMusic.class);
            }else{
                return service;
            }
        }
    }
}
