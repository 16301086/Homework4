package com.example.lenovo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoList extends AppCompatActivity {
    private ListView mListView;
    private TextView introductionText;
    private List<VideoInfo> videoList=new ArrayList<VideoInfo>();
    private VideoInfo video;
    private myAdapter adapter;
    private int currentIndex=0;
    private VideoView mVideoView;
    MediaController mMediaCtrl;
    private int playPosition=-1;
    private boolean isPlaying=false;
    private int preLast = -1;
    private int ifFirst = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list);
        video=new VideoInfo("http://ht-mobile.cdn.turner.com/nba/big/teams/kings/2014/12/12/HollinsGlassmov-3462827_8382664.mp4",R.drawable.video2);
        videoList.add(video);
        video=new VideoInfo("http://ht-mobile.cdn.turner.com/nba/big/teams/kings/2014/12/12/VivekSilverIndiamov-3462702_8380205.mp4",R.drawable.video1);
        videoList.add(video);
        mListView=findViewById(R.id.video_listview);
        adapter = new myAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                System.out.println(firstVisibleItem + "--" + visibleItemCount);
                if((currentIndex<firstVisibleItem || currentIndex>mListView.getLastVisiblePosition())&&isPlaying){

                    playPosition=mVideoView.getCurrentPosition();
                    mVideoView.pause();
                    mVideoView.setMediaController(null);
                    isPlaying=false;


                }else
                {
                    if(preLast==firstVisibleItem)
                    {
                        if(visibleItemCount>2 && currentIndex==0 && ifFirst!=3)
                        {
                            ifFirst =0;
                            currentIndex =firstVisibleItem + 1;
                            adapter.notifyDataSetChanged();
                        }else if(visibleItemCount== 2 && firstVisibleItem==0 && ifFirst==0)
                        {
                            ifFirst = 1;
                            currentIndex =firstVisibleItem ;
                            adapter.notifyDataSetChanged();
                        }

                    }else
                    {
                        preLast = firstVisibleItem;
                        ifFirst = 0;
                        if(visibleItemCount>=2)
                        {

                            currentIndex =firstVisibleItem + 1;
                            adapter.notifyDataSetChanged();

                        }else if(visibleItemCount==1)
                        {
                            currentIndex=firstVisibleItem;

                            adapter.notifyDataSetChanged();
                        }

                    }


                }
            }
        });
    }

    static class ViewHolder{
        ImageView videoImage;
        ImageButton videoPlayBtn;
        ProgressBar mProgressBar;
    }
    static class VideoInfo {
        String url;
        String videoName = "Traning Video";
        int videoImage;
        public VideoInfo(String url,int path) {
            this.videoImage=path;
            this.url=url;
        }
    }


    class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return videoList.size();
        }

        @Override
        public Object getItem(int position) {
            return videoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final int mPosition=position;
            if(convertView==null){
                convertView=LayoutInflater.from(VideoList.this).inflate(R.layout.video_item, null);
                holder=new ViewHolder();
                holder.videoImage=convertView.findViewById(R.id.video_image);
                holder.videoPlayBtn=convertView.findViewById(R.id.video_play_btn);
                holder.mProgressBar=convertView.findViewById(R.id.progressbar);
                convertView.setTag(holder);
            }else{
                holder=(ViewHolder) convertView.getTag();
            }
            holder.videoImage.setImageDrawable(getResources().getDrawable(videoList.get(position).videoImage));
            holder.videoPlayBtn.setVisibility(View.VISIBLE);
            holder.videoImage.setVisibility(View.VISIBLE);
            mMediaCtrl = new MediaController(VideoList.this,false);
            if(currentIndex == position){
                holder.videoPlayBtn.setVisibility(View.INVISIBLE);
                holder.videoImage.setVisibility(View.INVISIBLE);

                if(isPlaying || playPosition==-1){
                    if(mVideoView!=null){
                        mVideoView.setVisibility(View.GONE);
                        mVideoView.stopPlayback();
                        holder.mProgressBar.setVisibility(View.GONE);
                    }
                }
                mVideoView= convertView.findViewById(R.id.videoview);
                introductionText=convertView.findViewById(R.id.video_introduction);
                mVideoView.setVisibility(View.VISIBLE);
                introductionText.setVisibility(View.VISIBLE);
                mMediaCtrl.setAnchorView(mVideoView);
                mMediaCtrl.setMediaPlayer(mVideoView);
                mVideoView.setMediaController(mMediaCtrl);
                mVideoView.requestFocus();
                holder.mProgressBar.setVisibility(View.VISIBLE);
                mVideoView.setVideoPath(videoList.get(mPosition).url);
                isPlaying=true;
                mVideoView.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(mVideoView!=null){
                            mVideoView.seekTo(0);
                            mVideoView.stopPlayback();
                            mVideoView.start();
                            isPlaying=false;
                            holder.mProgressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

                mVideoView.setOnErrorListener(new OnErrorListener() {

                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        mp.reset();
                        return false;
                    }
                });


                mVideoView.setOnPreparedListener(new OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        holder.mProgressBar.setVisibility(View.GONE);
                        mVideoView.start();
                    }
                });

            }else{
                holder.videoPlayBtn.setVisibility(View.VISIBLE);
                holder.videoImage.setVisibility(View.VISIBLE);
                holder.mProgressBar.setVisibility(View.GONE);
            }

            holder.videoPlayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playPosition=mVideoView.getCurrentPosition();
                    mVideoView.pause();
                    mVideoView.setMediaController(null);
                    isPlaying=false;
                    if(mPosition==0)
                    {
                        ifFirst = 3;
                    }
                    currentIndex=mPosition;
                    playPosition=-1;
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        };
    }
}