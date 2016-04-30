package ggikko.me.jejuminyo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    RecyclerView my_recycler_view;
    VideoView videoView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static String TAG = "MainActivity";

    boolean doubleBackToExitPressedOnce = false;

    private List<DataObject> data = new ArrayList<>();

    public void setData(ArrayList<DataObject> mDataset) {
        this.data = mDataset;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView) findViewById(R.id.videoView);
        my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        my_recycler_view.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(this, videoView);
        my_recycler_view.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        my_recycler_view.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    videoView.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "게임을 종료시키기 위해 뒤로가기버튼을 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
