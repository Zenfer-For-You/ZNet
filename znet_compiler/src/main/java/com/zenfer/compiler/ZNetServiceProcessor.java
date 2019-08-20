package com.zenfer.compiler;

import com.google.auto.service.AutoService;
import com.zenfer.annotation.ZNetApi;
import com.zenfer.annotation.ZNetCallBack;
import com.zenfer.annotation.ZNetConvert;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class ZNetServiceProcessor extends AbstractProcessor {

    private Elements mElementUtils;



    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        ApiSelectorCreatorProxy apiSelectorCreatorProxy = new ApiSelectorCreatorProxy(processingEnv, mElementUtils, roundEnvironment);
        ApiRequestCreatorProxy apiRequestCreatorProxy = new ApiRequestCreatorProxy(processingEnv, mElementUtils, roundEnvironment);

        return apiSelectorCreatorProxy.createSelector() && apiRequestCreatorProxy.createRequest();
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(ZNetApi.class.getCanonicalName());
        supportTypes.add(ZNetConvert.class.getCanonicalName());
        supportTypes.add(ZNetCallBack.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
