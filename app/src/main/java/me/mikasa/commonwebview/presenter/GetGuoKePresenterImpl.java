package me.mikasa.commonwebview.presenter;

import android.content.Context;

import java.util.List;

import me.mikasa.commonwebview.bean.GuoKe;
import me.mikasa.commonwebview.contract.GetGuoKeContract;
import me.mikasa.commonwebview.model.GetGuoKeModelImpl;


/**
 * Created by mikasacos on 2018/9/27.
 */

public class GetGuoKePresenterImpl implements GetGuoKeContract.Presenter {
    private GetGuoKeContract.View mView;
    private GetGuoKeContract.Model mModel;
    public GetGuoKePresenterImpl(GetGuoKeContract.View view){
        this.mView=view;
        mModel=new GetGuoKeModelImpl(this);
    }

    @Override
    public void getGuoKe() {
        mModel.getGuoke();
    }

    @Override
    public void getGuoKeSuccess(List<GuoKe> list) {
        mView.getGuokeSuccess(list);
    }

    @Override
    public void getError(String msg) {
        mView.getError(msg);
    }
}
