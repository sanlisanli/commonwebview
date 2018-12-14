package me.mikasa.commonwebview.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import me.mikasa.commonwebview.R;
import me.mikasa.commonwebview.base.BaseToolbarActivity;
import me.mikasa.commonwebview.listener.DownloadListener;
import me.mikasa.commonwebview.utils.GlideUtil;

import static me.mikasa.commonwebview.utils.GlideUtil.FILEPATH;

public class PhotoViewActivity extends BaseToolbarActivity implements DownloadListener{
    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.iv_share)
    ImageView iv_share;
    @BindView(R.id.iv_download)
    ImageView iv_download;

    private String imgUrl;
    private Snackbar snackbar;
    private Context mContext;
    private boolean isDownload=false;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_picture_view;
    }

    @Override
    protected void initData() {
        mContext=this;
        Intent intent=getIntent();
        imgUrl=intent.getStringExtra("imgurl");
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(imgUrl)){
            Glide.with(this).load(imgUrl).into(photoView);
        }
    }

    @Override
    protected void initListener() {
        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_download.setPressed(true);
                if (!TextUtils.isEmpty(imgUrl)&&!isDownload){
                    downloadBitmap(imgUrl);
                }
            }
        });
        iv_share.setOnClickListener(new View.OnClickListener() {//简单测试一下分享文本信息
            @Override
            public void onClick(View v) {
                iv_share.setPressed(true);
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"分享"+imgUrl);
                startActivity(Intent.createChooser(intent,"分享"));
            }
        });
    }
    private void downloadBitmap(String url){
        GlideUtil glideUtil=new GlideUtil(mContext,this);
        glideUtil.downloadBitmap(url);
    }
    @Override
    public void downloadSuccess() {
        isDownload=true;
        snackbar= Snackbar.make(photoView,"图片已保存到"+FILEPATH,Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundResource(R.color.snackbar_success);
        snackbar.show();
    }

    @Override
    public void downloadFailed() {
    }

    @Override
    public void onBackPressed() {
        finish();//
    }
}
