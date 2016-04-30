package ggikko.me.jejuminyo;

/**
 * Created by ggikko on 16. 2. 29..
 */

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    public static ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;
    private final static String basicURL = "http://www.jeju.go.kr/rest/JejuFolksongService/getJejuFolksongList";
    private static RecyclerHandler mHandler = null;

    private static Context mContext;
    private static VideoView mVideoView;

    static AsyncTask asyncTask;

    public static void go(){
        asyncTask = new AsyncTask() {
            @Override
            protected List<DataObject> doInBackground(Object[] params) {

                List<DataObject> dataList = new ArrayList<DataObject>();
                dataList.clear();

                try {
                    Document document = Jsoup.parse(new URL(basicURL).openStream(), "UTF8", basicURL);
                    Elements elements = document.select("jejunetApi").select("items").select("item");
                    for (Element element : elements) {
                        String name = element.select("name").text();
                        String sound = element.select("sound").text();
                        Log.e("ggikko", "sound : " + sound);
                        String soundUrl = "http://www.jeju.go.kr/files/folkSong/" + sound;
                        String minute = element.select("minute").text();
                        String second = element.select("second").text();

                        if (sound.trim().equals("")) {
                            String movie = element.select("movie1").text();
                            soundUrl = "mhttp://www.jeju.go.kr/files/folkSong/" + movie;
                            Log.e("ggikko", movie);
                        }
                        String contents = element.select("contents").text()
                                .replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                                .replaceAll("[&#39;]", "")
                                .replaceAll("[lsquo;]", "")
                                .replaceAll("[lt]", "")
                                .replaceAll("[gt]", "")
                                .replaceAll("[rsquo;]", "")
                                .replaceAll("[quot;]", "");

                        DataObject dataObject = new DataObject(name, soundUrl, minute, second, contents);
                        dataList.add(dataObject);

                        Log.e("ggikko", dataObject.toString());

                    }

                    return dataList;

                } catch (IOException e) {
                    Log.e("ggikko", "baba");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                mDataset = (ArrayList<DataObject>) o;
                ((MainActivity)mContext).setData(mDataset);

                Message message = mHandler.obtainMessage();
                message.what = 0;
                mHandler.sendMessage(message);
                Log.e("ggikko", "complete");
            }
        };

        asyncTask.execute();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView song_name;
        TextView song_minute;
        TextView song_second;
        TextView song_content;
        ImageButton playmusic;


        public DataObjectHolder(View itemView) {
            super(itemView);

            song_name = (TextView) itemView.findViewById(R.id.song_name);
            song_minute = (TextView) itemView.findViewById(R.id.song_minute);
            song_second = (TextView) itemView.findViewById(R.id.song_second);
            song_content = (TextView) itemView.findViewById(R.id.song_content);
            playmusic = (ImageButton) itemView.findViewById(R.id.playmusic);

            playmusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = 0;
                    String soundUrl = mDataset.get(getPosition()).getSoundUrl();
                    if(mDataset!=null && mDataset.get(getPosition()).getSoundUrl().startsWith("m")){
//                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.parse(soundUrl.substring(1,soundUrl.length()-1)), "video/mp4");
//                        mContext.startActivity(intent);
//                        mVideoView.setVisibility(View.VISIBLE);
//                        mVideoView.setVideoURI(Uri.parse(soundUrl.substring(1,soundUrl.length()-1)));
//                        final MediaController mediaController = new MediaController(mContext);
//                        mVideoView.setMediaController(mediaController);

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(soundUrl.substring(1,soundUrl.length()-1)));
                        mContext.startActivity(intent);

                    }else{
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.parse(soundUrl), "audio/*");
                        mVideoView.setVisibility(View.VISIBLE);
                        mVideoView.setVideoURI(Uri.parse(soundUrl));
                        final MediaController mediaController = new MediaController(mContext);
                        mVideoView.setMediaController(mediaController);
                        mVideoView.requestFocus();
                        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mVideoView.seekTo(position);
                                if (position==0){
                                    mVideoView.start();
                                    mVideoView.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mediaController.show(0);
                                        }
                                    }, 100);
                                }else{
                                    mVideoView.pause();
                                }
                            }
                        });



//                        mContext.startActivity(intent);
                    }
                }
            });


            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(Context context, VideoView videoView) {
        mVideoView = videoView;
        mContext = context;
        mHandler = new RecyclerHandler();
        go();
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.song_name.setText(mDataset.get(position).getSong_name());
        holder.song_minute.setText(mDataset.get(position).getSong_minute());
        holder.song_second.setText(mDataset.get(position).getSong_second());
        holder.song_content.setText(mDataset.get(position).getSong_content());
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) {
            return 0;
        }
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    class RecyclerHandler extends android.os.Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 0: {
                    notifyDataSetChanged();
                    break;
                }
            }
        }
    }
}