package com.zenfer.compiler;

import android.support.annotation.StringDef;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.zenfer.annotation.ZNetApi;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 生成Selector代理
 *
 * @author Zenfer
 * @date 2019/8/20 10:45
 */
public class ApiSelectorCreatorProxy {
    private RoundEnvironment mRoundEnvironment;
    private Elements mElementUtils;
    private ProcessingEnvironment processingEnv;
    private MessageUtil util;

    public ApiSelectorCreatorProxy(ProcessingEnvironment processingEnv, Elements elementUtils, RoundEnvironment roundEnvironment) {
        this.processingEnv = processingEnv;
        this.mRoundEnvironment = roundEnvironment;
        this.mElementUtils = elementUtils;
        util = new MessageUtil(processingEnv.getMessager());
    }

    public boolean createSelector() {
        util.logNote("processing...");
        Set<? extends Element> apiElements = mRoundEnvironment.getElementsAnnotatedWith(ZNetApi.class);
        if (apiElements == null || apiElements.isEmpty()) {
            return false;
        }

        for (Element element : apiElements) {
            if (element.getKind() != ElementKind.INTERFACE) {
                util.logError(element, "被ZNetApi注解的必须是接口。");
                return false;
            }
            if (!element.getModifiers().contains(Modifier.PUBLIC)) {
                util.logError(element, "被ZNetApi注解的接口必须是公开的。");
                return false;
            }
            TypeElement classElement = (TypeElement) element;
            PackageElement packageElement = mElementUtils.getPackageOf(classElement);
            String packageName = packageElement.getQualifiedName().toString();
            String className = classElement.getSimpleName().toString();

            try {
                //　生成枚举文件
                // 添加 Retention 和 StringDef 注解
                List<AnnotationSpec> annotations = new ArrayList<>();
                annotations.add(AnnotationSpec.builder(ClassName.get(Retention.class)).addMember("value", CodeBlock.of("java.lang.annotation.RetentionPolicy.SOURCE")).build());
                StringBuilder stringBuilder = new StringBuilder("{\n");
                for (Element chileElement : classElement.getEnclosedElements()) {
                    String name = chileElement.getSimpleName().toString().toUpperCase();
                    stringBuilder.append(className + "Enum." + name + ",\n");
                }
                stringBuilder.append("}");
                AnnotationSpec.Builder annotationBuild = AnnotationSpec.builder(ClassName.get(StringDef.class)).addMember("value", CodeBlock.of(stringBuilder.toString()));
                annotations.add(annotationBuild.build());
                // 添加 每个接口请求的枚举对应值
                List<FieldSpec> fields = new ArrayList<>();
                for (Element chileElement : classElement.getEnclosedElements()) {
                    String name = chileElement.getSimpleName().toString().toUpperCase();
                    FieldSpec.Builder fieldBuilder = FieldSpec
                            .builder(String.class, name, Modifier.STATIC, Modifier.FINAL, Modifier.PUBLIC)
                            .initializer(CodeBlock.of("\"" + name + "\""));
                    fields.add(fieldBuilder.build());
                }
                // 拼接枚举类
                TypeSpec typeSpecEnum = TypeSpec.annotationBuilder(className + "Enum")
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotations(annotations)
                        .addFields(fields)
                        .build();
                // 执行枚举类文件生成
                JavaFile javaEnumFile = JavaFile.builder(packageName, typeSpecEnum).build();
                javaEnumFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                List<MethodSpec> methodSpecs = new ArrayList<>();
                List<? extends Element> childElements = classElement.getEnclosedElements();
                if (childElements == null || apiElements.isEmpty()) {
                    util.logError(classElement, "被ZNetApi注解的接口必须有 Observable<NetWordResult> 返回值的方法");
                    return false;
                }
                for (Element childElement : childElements) {
                    if (!(childElement instanceof ExecutableElement)) {
                        continue;
                    }
                    ExecutableElement executableElement = (ExecutableElement) childElement;
                    String methodName = String.valueOf(childElement.getSimpleName());

                    MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName)
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            .addException(Exception.class)
                            .addParameter(Object.class, "params");
                    // 添加方法返回类型
                    methodBuilder.returns(TypeName.get(executableElement.getReturnType()));

                    //地址检查
                    POST post = executableElement.getAnnotation(POST.class);
                    GET get = executableElement.getAnnotation(GET.class);
//                    PUT put = executableElement.getAnnotation(PUT.class);
//                    DELETE delete = executableElement.getAnnotation(DELETE.class);
                    if (post == null && get == null
//                            && put == null && delete == null
                    ) {
                        util.logError(executableElement, "方法必须有Post、Get、Put、Delete注解");
                        return false;
                    }
                    String requestTypeValue = "";
                    if (post != null) {
                        requestTypeValue = "(com.zenfer.network.framwork.RequestBodyUtil.createMapRequestBody(params))";
                    } else if (get != null) {
                        requestTypeValue = "(com.zenfer.network.framwork.RequestBodyUtil.createMapParams(params))";
                    }
//                    else if (put != null) {
//                        urlValue = put.value();
//                    } else {
//                        urlValue = delete.value();
//                    }
                    if ("".equals(requestTypeValue)) {
                        util.logError(executableElement, "Url地址不允许为空。");
                        return false;
                    }
                    methodBuilder.addCode("return " + "com.zenfer.network.framwork.ZNetwork.getInstance().getApi(" + element.getSimpleName().toString() + ".class)." + methodName + requestTypeValue + ";\n");
                    methodSpecs.add(methodBuilder.build());
                }
                TypeSpec typeSpecSelector = TypeSpec.classBuilder(className + "Selector")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethods(methodSpecs)
                        .build();
                //　执行selector文件生成
                JavaFile javaSelectorFile = JavaFile.builder(packageName, typeSpecSelector).build();
                javaSelectorFile.writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                e.printStackTrace();
                util.logNote("process finish with error ...");
                return false;
            }
        }
        util.logNote("process finish ...");
        return true;
    }
}
