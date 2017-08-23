package com.example.tkkil.slider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private int[] layouts = {R.layout.slide1, R.layout.slide2, R.layout.slide3, R.layout.slide4};
    private ViewPager viewPager;
    mPagerAdapter mPagerAdapter;
    LinearLayout dotslayout;
    ImageView[] imageViews;
    Button btnSkip, btnNext, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new PreferenceManager(this).checkPreference()) {
            loadHome();
        }
        if (Build.VERSION.SDK_INT > 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_welcome);
        init();
        mPagerAdapter = new mPagerAdapter(this, layouts);
        viewPager.setAdapter(mPagerAdapter);
        createDots(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if (position == 0) {
                    btnBack.setVisibility(View.INVISIBLE);
                } else if (position == layouts.length - 1) {
                    btnNext.setText("START");
                    btnSkip.setVisibility(View.INVISIBLE);
                    btnBack.setVisibility(View.VISIBLE);
                } else {
                    btnNext.setText("NEXT");
                    btnSkip.setVisibility(View.VISIBLE);
                    btnBack.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private void init() {
        viewPager = findViewById(R.id.viewPager);
        dotslayout = findViewById(R.id.dotsLayout);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        btnBack = findViewById(R.id.btnBack);
        btnNext.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }
    private void createDots(int current_position) {
        if (dotslayout != null) {
            dotslayout.removeAllViews();
        }
        imageViews = new ImageView[layouts.length];
        for (int i = 0; i < layouts.length; i++) {
            imageViews[i] = new ImageView(this);
            if (i == current_position) {
                imageViews[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));
            } else {
                imageViews[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            dotslayout.addView(imageViews[i], params);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                loadNextSlide();
                break;
            case R.id.btnSkip:
                loadHome();
                new PreferenceManager(this).writeSharedPreference();
                break;
            case R.id.btnBack:
                loadBackSlide();
                break;
        }
    }
    private void loadBackSlide() {
        int back_slide = viewPager.getCurrentItem() - 1;
        if(back_slide < layouts.length){
            viewPager.setCurrentItem(back_slide);
        }else{
        }
    }
    private void loadHome() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }
    private void loadNextSlide() {
        int next_slide = viewPager.getCurrentItem() + 1;
        if (next_slide < layouts.length) {
            viewPager.setCurrentItem(next_slide);
        } else {
            loadHome();
            new PreferenceManager(this).writeSharedPreference();
        }
    }
}
