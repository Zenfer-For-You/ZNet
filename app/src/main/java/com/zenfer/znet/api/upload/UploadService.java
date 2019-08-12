package com.zenfer.znet.api.upload;


import com.zenfer.znet.api.HostEnum;
import com.zenfer.znet.bean.NetWordResult;
import com.zenfer.network.host.Host;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 上传服务
 *
 * @author Zenfer
 * @date 2019/6/14 9:39
 */
@Host(host =  HostEnum.HOST_COMMON)
public interface UploadService {

    /**
     * 上传图片 单张
     */
    @POST("/v1/upload/single")
    Observable<NetWordResult> uploadSingle(@Body MultipartBody multipartBody);

    /**
     * 上传头像
     */
    @POST("/v1/user/profile")
    Observable<NetWordResult> upLoadHeadPic(@Body MultipartBody multipartBody);

}
