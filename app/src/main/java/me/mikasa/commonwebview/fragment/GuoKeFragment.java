package me.mikasa.commonwebview.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.mikasa.commonwebview.R;
import me.mikasa.commonwebview.activity.WebViewActivity;
import me.mikasa.commonwebview.adapter.GuokeAdapter;
import me.mikasa.commonwebview.base.BaseFragment;
import me.mikasa.commonwebview.bean.GuoKe;
import me.mikasa.commonwebview.contract.GetGuoKeContract;
import me.mikasa.commonwebview.listener.OnRecyclerViewItemClickListener;
import me.mikasa.commonwebview.presenter.GetGuoKePresenterImpl;

/**
 * Created by mikasacos on 2018/9/27.
 */

public class GuoKeFragment extends BaseFragment implements GetGuoKeContract.View{
    @BindView(R.id.rv_guoke)
    RecyclerView recyclerView;

    private GetGuoKePresenterImpl mPresenter;
    private Context mContext;
    private GuokeAdapter mAdapter;
    private List<GuoKe>mGuokeList=new ArrayList<>();
    public static GuoKeFragment newInstance(){
        return new GuoKeFragment();
    }
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_guoke;
    }

    @Override
    protected void initData(Bundle bundle) {
        mContext=this.getContext();
        mPresenter=new GetGuoKePresenterImpl(this);
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter=new GuokeAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
        mPresenter.getGuoKe();//请求网络数据
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent=new Intent(mContext, WebViewActivity.class);
                intent.putExtra("link",mGuokeList.get(pos).getLink());//有header
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(int pos) {
                return false;
            }
        });
    }

    @Override
    public void getGuokeSuccess(List<GuoKe> list) {
        mGuokeList=list;
        mAdapter.updateData(list);
    }

    @Override
    public void getError(String msg) {
    }
}
