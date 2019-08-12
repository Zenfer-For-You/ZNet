package com.yushibao.zenfernetwork.api.upload;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.yushibao.zenfernetwork.api.common.CommonApiEnum;
import com.zenfer.network.framwork.Network;
import com.zenfer.network.upload.UploadFormDataParams;
import com.zenfer.network.upload.UploadProgressListener;
import com.zenfer.network.upload.UploadProgressRequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 上传工具类
 *
 * @author Zenfer
 * @date 2019/6/14 9:39
 */
public class UploadUtil {

    /**
     * 组装 图片路径上传对象
     *
     * @param imagePaths 图片路径集合
     */
    public static List<UploadFormDataParams> requestImagePost(final List<String> imagePaths) {
        List<UploadFormDataParams> dataParamsList = new ArrayList<>();
        for (String imagePath : imagePaths) {
            File file = new File(imagePath);
            if (file.exists()) {
                dataParamsList.add(new UploadFormDataParams("headImg", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file)));
            } else {
                return null;
            }
        }
        return dataParamsList;
    }

    /**
     * 组装 图片路径上传对象
     *
     * @param imagePath 图片路径
     */
    public static UploadFormDataParams requestImagePost(final String imagePath) {
        File file = new File(imagePath);
        if (file.exists()) {
            return new UploadFormDataParams("file", file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
        } else {
            return null;
        }
    }

    /**
     * 执行上传
     *
     * @param dataParams file数据
     * @param callBack   回调
     */
    public static void upload(@CommonApiEnum String tag, @Nullable List<UploadFormDataParams> dataParams, UploadCallBack callBack) {
        if (dataParams == null || dataParams.size() > 0) {
            return;
        }
        switch (tag) {
            case CommonApiEnum.UPLOAD_HEAD_PIC:
                //上传头像
                Network.addObservable(Network.getInstance().getApi(UploadService.class)
                        .upLoadHeadPic(filesToMultipartBody(dataParams, callBack.getListener())), callBack);
                break;
            default:
        }
    }

    /**
     * 上传图片，单张上传
     */
    public static void uploadPicSingle(String tag, int order, UploadFormDataParams dataParams, UploadCallBack callBack) {
        if (dataParams == null) {
            return;
        }
        callBack.setTag(tag);
        switch (tag) {
            case CommonApiEnum.UPLOAD_HEAD_PIC:
                //上传头像
                Network.addObservable(Network.getInstance().getApi(UploadService.class)
                        .upLoadHeadPic(filesToMultipartBody(order, dataParams, callBack.getListener())), callBack);
                break;
            case CommonApiEnum.UPLOAD_PIC:
                //上传工作环境图片
                Network.addObservable(Network.getInstance().getApi(UploadService.class)
                        .uploadSingle(filesToMultipartBody(order, dataParams, callBack.getListener())), callBack);
                break;
            default:
        }

    }


    /**
     * 生成 RequestBody
     *
     * @param params   file数据
     * @param listener 上传监听 主要是 监听 progress
     * @param order    排序数字 1，2，3，4，5
     * @return MultipartBody
     */
    private static MultipartBody filesToMultipartBody(int order, UploadFormDataParams params, UploadProgressListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart(params.getName(), params.getValue(), new UploadProgressRequestBody(params.getBody(), listener));
        //上传多张图片
        if (order > 0) {
            builder.addFormDataPart("order", String.valueOf(order));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    /**
     * 生成 RequestBody
     *
     * @param mDataParamsList file数据
     * @param listener        上传监听 主要是 监听 progress
     * @return MultipartBody
     */
    private static MultipartBody filesToMultipartBody(List<UploadFormDataParams> mDataParamsList, UploadProgressListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (UploadFormDataParams params : mDataParamsList) {
            if (TextUtils.isEmpty(params.getName())) {
                continue;
            }
            if (params.getBody() == null) {
                builder.addFormDataPart(params.getName(), params.getValue());
                continue;
            }
            builder.addFormDataPart(params.getName(), params.getValue(), new UploadProgressRequestBody(params.getBody(), listener));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

}
