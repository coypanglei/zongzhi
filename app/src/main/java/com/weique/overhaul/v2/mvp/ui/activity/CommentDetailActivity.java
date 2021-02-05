package com.weique.overhaul.v2.mvp.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.di.component.DaggerCommentDetailComponent;
import com.weique.overhaul.v2.mvp.contract.CommentDetailContract;
import com.weique.overhaul.v2.mvp.presenter.CommentDetailPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * todo 评论相关暂时不开放 待处理
 * 评论详情页
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
public class CommentDetailActivity extends BaseActivity<CommentDetailPresenter> implements CommentDetailContract.View {

    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_photo_text)
    ImageView userPhoto;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.comment_content)
    TextView commentContent;
    @BindView(R.id.comment_time)
    TextView commentTime;
    @BindView(R.id.praise)
    ImageView praise;
    @BindView(R.id.praise_num)
    TextView praiseNum;
    @BindView(R.id.all_reply)
    RecyclerView allReply;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommentDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_comment_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            allReply.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_1)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
    }

    @Override
    public void launchActivityByRouter(@NonNull String path) {
        ARouterUtils.navigation(this, path);
    }

    @Override
    public void killMyself() {
        finish();
    }

}
