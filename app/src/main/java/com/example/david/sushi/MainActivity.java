package com.example.david.sushi;

import android.animation.Animator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.david.sushi.Common.Constant;
import com.example.david.sushi.Database.Data.CallbackWrapper;
import com.example.david.sushi.Database.Data.Data;
import com.example.david.sushi.Fragment.MenuFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.victor.loading.book.BookLoading;
import com.victor.loading.rotate.RotateLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by David on 21/03/2017.
 */

public class MainActivity extends BaseActivity implements DialogInterface {

    @BindView(R.id.stl_menu)
    SmartTabLayout stlMenu;
    @BindView(R.id.vp_menu)
    ViewPager vpMenu;
    @BindView(R.id.ib_chart)
    ImageButton ibCart;
    @BindView(R.id.b_bill)
    Button bBill;
    @BindView(R.id.b_call)
    Button bCall;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_notif)
    TextView tvNotif;

    @BindView(R.id.rl_wrapper_loading)
    RelativeLayout rlWrapperLoading;
    @BindView(R.id.rotateloading)
    BookLoading rlLoading;

    Dialog dialog;
    MyCountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        if (Constant.mainActivity == null) {
            Constant.mainActivity = this;
        }
        ButterKnife.bind(this);
        customizeFonts(bBill, bCall);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setListener();
        setView();

        //getMenu();

        dialog = new Dialog(this, R.style.StyleDialog);
        dialog.setContentView(R.layout.layout_dialog_corousel);

        countDownTimer = new MyCountDownTimer(getSession().getTimer(), Constant.INTERVAL);
        countDownTimer.start();
    }

    private void setListener() {
        ibCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        bBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVerifyBack();
            }
        });

        bCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogWaiter();
                Call<CallbackWrapper> callWaiter = getService().callWaiter(getSession().getNoMeja());
                callWaiter.enqueue(new Callback<CallbackWrapper>() {
                    @Override
                    public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {

                    }

                    @Override
                    public void onFailure(Call<CallbackWrapper> call, Throwable throwable) {

                    }
                });
            }
        });
    }

    private void setView() {
        showLoading();
        Call<CallbackWrapper> call = getService().getAllCategory();
        call.enqueue(new retrofit2.Callback<CallbackWrapper>() {
            @Override
            public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {
                if (response.isSuccessful()) {
                    FragmentPagerItems.Creator fragmentPagerItems = FragmentPagerItems.with(MainActivity.this);
                    List<Data> dataList = response.body().getData();
                    for (int i = 0; i < dataList.size(); i++) {
                        fragmentPagerItems.add(dataList.get(i).getNama(), MenuFragment.class, new Bundler().putString("id", dataList.get(i).getId()).get());

                    }

                    FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                            getSupportFragmentManager(), fragmentPagerItems
                            .create());

                    vpMenu.setAdapter(adapter);
                    vpMenu.setOffscreenPageLimit(dataList.size());
                    stlMenu.setViewPager(vpMenu);
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<CallbackWrapper> call, Throwable t) {

            }
        });

    }

    public void refreshCart() {
        if (Constant.cart.size() > 0) {
            tvNotif.setText(String.valueOf(Constant.cart.size()));
            tvNotif.setVisibility(View.VISIBLE);
        } else {
            tvNotif.setVisibility(View.INVISIBLE);
        }
    }

    public void showDialogWaiter() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.layout_dialog_call_waiter);
        ImageView ivWaiter = (ImageView) dialog.findViewById(R.id.iv_waiter);

        YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(5).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                dialog.dismiss();
            }
        })
                .playOn(ivWaiter);

        dialog.show();
    }

    private void showCarousol() {

        final CarouselView carouselView = (CarouselView) dialog.findViewById(R.id.carouselView);
        ImageButton ibNext = (ImageButton) dialog.findViewById(R.id.ib_next);
        ImageButton ibBefore = (ImageButton) dialog.findViewById(R.id.ib_before);
        ImageButton ibClose = (ImageButton) dialog.findViewById(R.id.ib_close);

        final int[] sampleImages = {R.drawable.img_promo, R.drawable.img_promo, R.drawable.img_promo};
        carouselView.setPageCount(sampleImages.length);

        final ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(sampleImages[position]);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countDownTimer.start();
                        dialog.dismiss();
                    }
                });
            }
        };

//        ImageListener imageListener = new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                Glide.with(getApplicationContext())
//                        .load(mImages.get(position))
//                        .centerCrop()
//                        .placeholder(R.drawable.placeholder_image)
//                        .into(imageView);
//            }
//        };

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                countDownTimer.start();
                dialog.dismiss();
            }
        });

        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carouselView.getCurrentItem() < sampleImages.length) {
                    carouselView.setCurrentItem(carouselView.getCurrentItem() + 1);
                }
            }
        });

        ibBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carouselView.getCurrentItem() > 0) {
                    carouselView.setCurrentItem(carouselView.getCurrentItem() - 1);
                }
            }
        });

        carouselView.setImageListener(imageListener);

        if (Constant.SHOW_SCREENSAVER) {
            dialog.show();
        } else {
            countDownTimer.cancel();
            countDownTimer.start();
        }
    }

    private void showVerifyBack() {
        final Dialog dialog = new Dialog(this, R.style.StyleDialog);
        dialog.setContentView(R.layout.layout_dialog_password);
        RelativeLayout rlWrapper = (RelativeLayout) dialog.findViewById(R.id.rl_wrapper);
        final PinLockView pinLockView = (PinLockView) dialog.findViewById(R.id.pin_lock_view);
        IndicatorDots indicatorDots = (IndicatorDots) dialog.findViewById(R.id.indicator_dots);
        pinLockView.attachIndicatorDots(indicatorDots);
        pinLockView.setPinLockListener(new PinLockListener() {
            @Override
            public void onComplete(String s) {
                showLoading();
                Call<CallbackWrapper> callLogin = getService().login(s);
                callLogin.enqueue(new Callback<CallbackWrapper>() {
                    @Override
                    public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {
                        hideLoading();
                        if (response.isSuccessful()&&response.body().getCode().equals(Constant.API_SUCCESS)) {
                            Call<CallbackWrapper> call1 = getService().openTable("status","1",getSession().getNoMeja());
                            call1.enqueue(new Callback<CallbackWrapper>() {
                                @Override
                                public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {
                                    if(response.isSuccessful()){
                                        dialog.dismiss();
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CallbackWrapper> call, Throwable throwable) {

                                }
                            });
                        } else {
                            YoYo.with(Techniques.Shake)
                                    .duration(500)
                                    .repeat(1).onEnd(new YoYo.AnimatorCallback() {
                                @Override
                                public void call(Animator animator) {
                                    pinLockView.resetPinLockView();
                                }
                            })
                                    .playOn(pinLockView);
                        }
                    }

                    @Override
                    public void onFailure(Call<CallbackWrapper> call, Throwable throwable) {

                    }
                });
            }

            @Override
            public void onEmpty() {

            }

            @Override
            public void onPinChange(int i, String s) {

            }
        });

        rlWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onUserInteraction() {

        super.onUserInteraction();

        //Reset the timer on user interaction...
        countDownTimer.cancel();
        countDownTimer.start();
    }

    @Override
    protected void onPause() {
        countDownTimer.cancel();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCart();
        countDownTimer.start();
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            showCarousol();
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            System.out.println("TIMER: "+this+" "+millisUntilFinished/1000);
        }
    }

    public void showLoading() {
        rlWrapperLoading.setVisibility(View.VISIBLE);
        rlLoading.start();
    }

    public void hideLoading() {
        rlWrapperLoading.setVisibility(View.GONE);
        rlLoading.stop();

    }
}
