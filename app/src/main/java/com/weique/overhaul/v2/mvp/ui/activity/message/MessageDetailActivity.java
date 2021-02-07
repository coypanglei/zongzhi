package com.weique.overhaul.v2.mvp.ui.activity.message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.FileUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerMessageDetailComponent;
import com.weique.overhaul.v2.mvp.contract.MessageDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.FileBean;
import com.weique.overhaul.v2.mvp.model.entity.MessageListBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.model.entity.event.ProgressChangeBean;
import com.weique.overhaul.v2.mvp.presenter.MessageDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MessageDetailAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.EditTextDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_MESSAGEDETAILACTIVITY)
public class MessageDetailActivity extends BaseActivity<MessageDetailPresenter> implements MessageDetailContract.View {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.submiter)
    TextView submiter;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.accessory_layoutl)
    LinearLayout accessoryLayoutl;

    @Inject
    Gson gson;
    public static final String LISTBEAN = "ListBean";
    @Autowired(name = LISTBEAN)
    MessageListBean.ListBean listBean;

    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    private MessageDetailAdapter messageDetailAdapter;
    private EditTextDialog editTextDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            setTitle(getString(R.string.mes_detail));
            rightBtnText.setText("回复");
            title.setText(listBean.getMessage());
            content.setText(listBean.getMemo());
            submiter.setText(listBean.getSenderUserName());
            time.setText(listBean.getCreateTime());
            if (!listBean.isIsRead()) {
                mPresenter.setMessageIsReading(listBean.getId());
            }
            if (StringUtil.isNotNullString(listBean.getURL())) {
                List<String> list = gson.fromJson(listBean.getURL(), List.class);
                if (list == null || list.size() <= 0) {
                    accessoryLayoutl.setVisibility(View.GONE);
                    return;
                }
                accessoryLayoutl.setVisibility(View.VISIBLE);
                List<FileBean> fileBeans = new ArrayList<>();
                FileBean fileBean;
                for (String s : list) {
                    fileBean = new FileBean();
                    fileBean.setUrl(s);
                    fileBeans.add(fileBean);
                }
                fileBean = null;
                if (messageDetailAdapter == null) {
                    messageDetailAdapter = new MessageDetailAdapter();
                    messageDetailAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                    ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                            .colorResId(R.color.gray_eee)
                            .sizeResId(R.dimen.dp_1)
                            .build());
                    recyclerView.setAdapter(messageDetailAdapter);
                    messageDetailAdapter.setOnItemClickListener((adapter, view, position) -> {
                        try {
                            if (AppUtils.isFastClick()) {
                                return;
                            }
                            FileBean item = (FileBean) adapter.getItem(position);
                            String url = item.getUrl();
                            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
                            File file = Constant.getDownloadFile(fileName);
                            if (file.exists()) {
                                new CommonDialog.Builder(this)
                                        .setContent("文件已经下载完成，确认查看文件")
                                        .setPositiveButton("确定", (v, commonDialog) -> {
                                            //todo 打开文件
                                            FileUtil.openFile(this, file);
                                        }).create().show();
                                return;
                            }
                            new CommonDialog.Builder(MessageDetailActivity.this)
                                    .setContent("确定下载<" + fileName + ">")
                                    .setPositiveButton((view1, commonDialog) -> {
                                        mPresenter.getPermission(url, fileName, position);
                                    }).create().show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                messageDetailAdapter.setNewData(fileBeans);
            } else {
                accessoryLayoutl.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    /**
     * 设置reading
     */
    @Override
    public void setIsReadingSuccess() {
        EventBus.getDefault().post(new EventBusBean(), source);
        EventBus.getDefault().post(new EventBusBean(EventBusConstant.REQUEST_AGAIN), RouterHub.APP_MAINACTIVITY);
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @OnClick(R.id.right_btn)
    public void onClick() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if (editTextDialog == null) {
                editTextDialog = new EditTextDialog(this);
                editTextDialog.setOnConfirmClickListener(contents -> {
                    mPresenter.replyMessage(listBean.getId(), contents);
                });
            }
            if (!editTextDialog.isShowing()) {
                editTextDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收服务上传签到点位
     *
     * @param eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_MESSAGEDETAILACTIVITY)
    private void eventBusCallback(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.DOWNLOAD_PREGRESS:
                case EventBusConstant.DOWNLOAD_PREGRESS_OK:
                    ProgressChangeBean data = (ProgressChangeBean) eventBusBean.getData();
                    messageDetailAdapter.setUpdateProgress(data);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
