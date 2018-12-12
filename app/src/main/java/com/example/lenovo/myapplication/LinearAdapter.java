package com.example.lenovo.myapplication;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * created by Qin yiyi 16301087@bjtu.edu.cn
 */

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.LinearViewHolder> {

    private Context mcontext;
    private String[] classes = {
            "Body dance",
            "Aerobics",
            "Lama drilling",
            "Body shape",
            "Dancing practice",
            "Three Balls",
            "Sports dance",
            "Physical dance",
            "Taekwondo",
            "Yonga",
    };
    private String[] teachers = {
            "MR.ZHAO",
            "MR.WANG",
            "MRS.QIAN",
            "MR.SUN",
            "MR.LI",
            "MR.ZHOU",
            "MRS.WU",
            "MRS.ZHAO",
            "MR.LI",
            "MR.QIN",
    };
    private String[] phones = {
            "13207716967",
            "15858747219",
            "18753128762",
            "01024819274",
            "15510472142",
            "13283120312",
            "15184924122",
            "16327914429",
            "15289132111",
            "18141024124",
    };

    /**
     * 构造函数，需要的值可在这儿传过来
     */
    public LinearAdapter(Context context){
        mcontext = context;
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     */
    @NonNull
    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = View.inflate(mcontext, R.layout.recycler_linear_item, null);
        return new LinearViewHolder(itemView);
        //return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.recycler_linear_item,viewGroup,false));
    }

    /**
     * 绑定数据，数据与view绑定
     */
    @Override
    public void onBindViewHolder(@NonNull LinearAdapter.LinearViewHolder viewHolder, int position) {
        viewHolder.coach.setText(teachers[position]);
        viewHolder.phone.setText(phones[position]);
        position++;
        String s = "";
        s += String.valueOf(position);
        s += " . ";
        s += classes[position];
        viewHolder.textView.setText(s);
    }

    /**
     * 得到总条数
     */
    @Override
    public int getItemCount() {
        return 10;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{
        TextView textView,coach,phone;
        Button button;
        ImageButton imageButton;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_);
            coach = itemView.findViewById(R.id.textView_name);
            phone = itemView.findViewById(R.id.textView_phone);
            imageButton = itemView.findViewById(R.id.imageButton_class);
            button = itemView.findViewById(R.id.send);
            // 网络视频
            // String videoUrl = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8" ;
            // 设置视频控制器
            // videoView.setMediaController(new MediaController(mcontext));
            // 设置视频路径
            // videoView.setVideoPath(videoUrl);
            // videoView.requestFocus();
            // videoView.seekTo(1);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = textView.getText().toString();
                    s += " is not open at present!";
                    Toast.makeText(mcontext, s, Toast.LENGTH_LONG).show();
                    /*此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, goodsEntityList.get(getLayoutPosition()));
                    }*/
                }
            });
            /*videoView.setOnTouchListener(new View.OnTouchListener() {
                GestureDetector mGesture;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mGesture == null) {
                        mGesture = new GestureDetector(mcontext, new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDown(MotionEvent e) {
                                //返回false的话只能响应长摁事件
                                return true;
                            }
                        });
                        mGesture.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                            @Override
                            public boolean onSingleTapConfirmed(MotionEvent e) {
                                videoView.setBackground(null);
                                if (! videoView.isPlaying())
                                    videoView.start();
                                else
                                    videoView.stopPlayback();
                                return true;
                            }

                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                return true;
                            }

                            @Override
                            public boolean onDoubleTapEvent(MotionEvent e) {
                                return false;
                            }
                        });
                    }
                    return mGesture.onTouchEvent(event);
                }
            });*/

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                        ContentUtil.sendSmsWithBody(mcontext, phone.getText().toString(), "");
                }
            });
        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         */
        public void OnItemClick(View view);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
