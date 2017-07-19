package com.gg.habittrain.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gg.habittrain.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class ItemDetailActivity extends AppCompatActivity {
    @BindView(R.id.txt_time_item)
    TextView txtTimeItem;
    @BindView(R.id.txt_title_item)
    TextView txtTitleItem;
    @BindView(R.id.txt_content_item)
    TextView txtContentItem;
    private String timeItem="";
    private String tileItem="";
    private String content="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_layout);
        ButterKnife.bind(this);
        if(getIntent()!=null){
            timeItem=getIntent().getStringExtra("time");
            tileItem=getIntent().getStringExtra("title");
            content=getIntent().getStringExtra("cuecontent");
        }
        initView();
    }

    private void initView() {
        txtTimeItem.setText(timeItem);
        txtTitleItem.setText(tileItem);
        txtContentItem.setText(content);
    }
}
