/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weique.overhaul.v2.app;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.ui.activity.LoginActivity;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * <p>
 * ================================================
 *
 * @author GK
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 TOKEN 过期后
     * 重新请求 TOKEN, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link okhttp3.Interceptor.Chain}
     * @param response   {@link Response}
     * @return {@link Response}
     */
    @NonNull
    @Override
    public Response onHttpResultResponse(@Nullable String httpResult, @NonNull Interceptor.Chain chain, @NonNull Response response) {
        /*if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {
            try {
                BaseBean baseBean = ArmsUtils.obtainAppComponentFromContext(context).gson().fromJson(httpResult, new TypeToken<BaseBean>() {
                }.getType());
                baseBean.getCode();
            } catch (Exception e) {
                e.printStackTrace();
                return response;
            }
        }*/

        /* 这里如果发现 TOKEN 过期, 可以先请求最新的 TOKEN, 然后在拿新的 TOKEN 放入 Request 里去重新请求
        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
        create a new request and modify it accordingly using the new TOKEN
        Request newRequest = chain.request().newBuilder().header("TOKEN", newToken)
                             .build();

        retry the request

        response.body().close();
        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
        如果不需要返回新的结果, 则直接把参数 response 返回出去即可*/
        return response;
    }

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 TOKEN 或者 header 以及参数加密等操作
     * <p>
     * 添加公共参数
     *
     * @param chain   {@link okhttp3.Interceptor.Chain}
     * @param request {@link Request}
     * @return {@link Request}
     */
    @NonNull
    @Override
    public Request onHttpRequestBefore(@NonNull Interceptor.Chain chain, @NonNull Request request) {
        /* 如果需要在请求服务器之前做一些操作, 则重新构建一个做过操作的 Request 并 return, 如增加 Header、Params 等请求信息, 不做操作则直接返回参数 request
        chain.request().newBuilder().header("TOKEN", tokenId)
                              .build(); */
        try {
            if (!request.url().toString().contains("app/login/login")) {
                /*if (UserInfoUtil.getUserInfo() == null || StringUtil.isNullString(UserInfoUtil.getUserInfo().getToken())) {
                    ArmsUtils.killAll(LoginActivity.class);
                    ARouterUtils.navigation(RouterHub.APP_LOGINACTIVITY);
                    ArmsUtils.makeText("登录已过期,请重新登录");
                    return null;
                }*/
                if (request.url().toString().endsWith(Constant.APK)
                        || UserInfoUtil.getUserInfo() == null) {
                    return request;
                }
                request = request.newBuilder()
                        .addHeader(Constant.TOKEN, StringUtil.setText(UserInfoUtil.getUserInfo().getToken()))
                        .addHeader(Constant.VERSION, AppUtils.getAppVersionName())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
