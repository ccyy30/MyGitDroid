package com.feicui.mygitdroid.commons;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This is a standard {@link Interceptor} for {@link okhttp3.OkHttpClient}.
 * Show the log of a {@link okhttp3.Call}.
 *
 * <p/>
 * 这是一个标准的OkHttp拦截器，用来打印一个OkHttp Call的日志。
 */
public class LoggingInterceptor implements Interceptor {
    @Override public Response intercept(Chain chain) throws IOException {
        // 从请求链中获取一个新的请求
        Request request = chain.request();

        // 打印该请求的相关信息，包括url、连接信息和请求头信息。
        long t1 = System.nanoTime();
        LogUtils.i(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        // 获取该请求对应的响应
        Response response = chain.proceed(request);

        // 打印该响应的相关信息
        long t2 = System.nanoTime();
        LogUtils.i(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return response;
    }
}
