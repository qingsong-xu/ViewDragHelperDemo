package com.xuqingsong.viewdraghelperdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_drag_horizontal)
    Button mBtnDragHorizontal;

    @Bind(R.id.btn_drag_vertical)
    Button mBtnDragVertical;

    @Bind(R.id.btn_youtube)
    Button mBtnYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setListener();
    }

    private void setListener() {
        mBtnDragHorizontal.setOnClickListener(mOnClickListener);
        mBtnDragVertical.setOnClickListener(mOnClickListener);
        mBtnYoutube.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        Intent intent = new Intent();

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_drag_horizontal:
                    intent.setClass(MainActivity.this, DrageLayoutActivity.class);

                    break;

                case R.id.btn_drag_vertical:

                    break;

                case R.id.btn_youtube:

                    break;
            }
            startActivity(intent);
        }
    };
}
