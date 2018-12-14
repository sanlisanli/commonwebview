package me.mikasa.commonwebview.listener;


import okhttp3.Call;

public interface  OkHttpResultCallback {

    void onError(Call call, Exception e);

    void onResponse(byte[] bytes);
}
