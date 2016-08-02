package com.feicui.mygitdroid.github.network;

import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.github.login.UserRepo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Token拦截器
 * Created by Administrator on 2016/7/29 0029.
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //Chain对象相当于是请求和响应链，里面存放和请求和响应的数据
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        //如果发送请求时用户缓存中有token，就在头消息中添加上去
        if(UserRepo.hasToken()){
            LogUtils.i("token加入请求头--"+UserRepo.getToken());
            //这里是以键值对的形式添加的，注意不要写成Authorization：，还有token后面的空格别忘记
            builder.addHeader("Authorization","token " + UserRepo.getToken());
        }
        //注意必须用request重新赋值或者chain.proceed(builder.build())
        request = builder.build();
        //发送请求
        Response response = chain.proceed(request);
        //如果成功获取到响应
        if(response.isSuccessful()){
            return response;
        }
        //如果有异常
        if(response.code() == 401 || response.code() == 403){
            throw new IOException("未经授权！");
        }else{
            throw new IOException("网络连接失败！");
        }
    }
}
