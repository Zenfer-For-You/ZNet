package com.zenfer.znet.api.upload;

import android.support.annotation.StringDef;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@StringDef({
        UploadApiEnum.UPLOAD_HEAD_PIC,
        UploadApiEnum.UPLOAD_PIC,
})
@Retention(RetentionPolicy.RUNTIME)
public @interface UploadApiEnum {
    String UPLOAD_HEAD_PIC = "UPLOAD_HEAD_PIC";
    String UPLOAD_PIC = "UPLOAD_PIC";
}
