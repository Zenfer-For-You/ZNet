package com.zenfer.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.zenfer.annotation.ZNetApi;
import com.zenfer.annotation.ZNetCallBack;
import com.zenfer.annotation.ZNetConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * 生成Request代理
 *
 * @author Zenfer
 * @date 2019/8/20 10:45
 */
public class ApiRequestCreatorProxy {
    private RoundEnvironment mRoundEnvironment;
    private Elements mElementUtils;
    private ProcessingEnvironment processingEnv;
    private MessageUtil util;

    private Types mTypes;
    private Filer mFiler;

    public ApiRequestCreatorProxy(ProcessingEnvironment processingEnv, Elements elementUtils, RoundEnvironment roundEnvironment) {
        this.processingEnv = processingEnv;
        this.mRoundEnvironment = roundEnvironment;
        this.mElementUtils = elementUtils;
        mTypes = processingEnv.getTypeUtils();
        mFiler = processingEnv.getFiler();
        util = new MessageUtil(processingEnv.getMessager());
    }

    public boolean createRequest() {
        util.logNote("processing...");
        Set<? extends Element> apiElements = mRoundEnvironment.getElementsAnnotatedWith(ZNetApi.class);
        Set<? extends Element> convertElements = mRoundEnvironment.getElementsAnnotatedWith(ZNetConvert.class);
        Set<? extends Element> callbackElements = mRoundEnvironment.getElementsAnnotatedWith(ZNetCallBack.class);

        if (!isElementEmpty(apiElements, ElementKind.INTERFACE, Modifier.PUBLIC)
                || !isElementEmpty(convertElements, ElementKind.CLASS, Modifier.PUBLIC)
                || !isElementEmpty(callbackElements, ElementKind.CLASS, Modifier.PUBLIC)
        ) {
            return false;
        }

        for (Element element : apiElements) {
            TypeElement apiElement = (TypeElement) element;
            PackageElement packageElement = mElementUtils.getPackageOf(apiElement);
            String packageName = packageElement.getQualifiedName().toString();
            String className = apiElement.getSimpleName().toString();
            ZNetApi apiAnnotation = apiElement.getAnnotation(ZNetApi.class);

            TypeElement convertElement = null;
            for (Element convert : convertElements) {
                ZNetConvert convertAnnotation = convert.getAnnotation(ZNetConvert.class);
                for (String value : convertAnnotation.value()) {
                    if (apiAnnotation.host().equals(value)) {
                        convertElement = (TypeElement) convert;
                        break;
                    }
                }
                if (convertElement != null) {
                    break;
                }
            }

            TypeElement callBackElement = null;
            for (Element callback : callbackElements) {
                ZNetCallBack callbackAnnotation = callback.getAnnotation(ZNetCallBack.class);
                for (String value : callbackAnnotation.value()) {
                    if (apiAnnotation.host().equals(value)) {
                        callBackElement = (TypeElement) callback;
                        break;
                    }
                }
                if (callBackElement != null) {
                    break;
                }
            }

            if (convertElement == null) {
                util.logError(null, "无法找到" + apiAnnotation.host() + "对应的Convert");
                return false;
            }
            if (callBackElement == null) {
                util.logError(null, "无法找到" + apiAnnotation.host() + "对应的CallBack");
                return false;
            }

            try {
                List<MethodSpec> methodSpecs = new ArrayList<>();
                List<? extends Element> childElements = apiElement.getEnclosedElements();
                if (childElements == null || apiElements.isEmpty()) {
                    util.logError(apiElement, "被ZNetApi注解的接口必须有 Observable<T> 返回值的方法");
                    return false;
                }
                for (Element childElement : childElements) {
                    if (!(childElement instanceof ExecutableElement)) {
                        continue;
                    }
                    ExecutableElement executableElement = (ExecutableElement) childElement;
                    String methodName = String.valueOf(childElement.getSimpleName());

                    // 添加 Map<String,Object> map 方法入参
                    ParameterizedTypeName inputMapTypeOfGroup = ParameterizedTypeName.get(
                            ClassName.get(Map.class),
                            ClassName.get(String.class),
                            ClassName.get(Object.class)
                    );
                    ParameterSpec.Builder map = ParameterSpec.builder(inputMapTypeOfGroup, "map");

                    // 添加 NetworkCallBack<T> 入参
                    TypeMirror typeMirror = executableElement.getReturnType();
                    // 获取Api返回类型
                    ParameterizedTypeName name = (ParameterizedTypeName) TypeName.get(typeMirror);
                    // 获取上述返回类型定义的泛型
                    ParameterizedTypeName subName = (ParameterizedTypeName) name.typeArguments.get(0);
                    // 获取包名
                    PackageElement callbackPackageElement = mElementUtils.getPackageOf(callBackElement);
                    ParameterizedTypeName inputNetworkCallBackType = ParameterizedTypeName.get(
                            // 指定类
                            ClassName.get(callbackPackageElement.getQualifiedName().toString(), callBackElement.getSimpleName().toString()),
                            // 定义泛型的子泛型
                            subName.typeArguments.get(0)
                    );
                    ParameterSpec.Builder networkCallBack = ParameterSpec.builder(inputNetworkCallBackType, "networkCallBack");

                    MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName)
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            .returns(void.class)
                            .addParameter(map.build())
                            .addParameter(networkCallBack.build());

                    methodBuilder.addStatement(CodeBlock.of("networkCallBack.setTag(" + className + "Enum." + methodName.toUpperCase() + ")"));

                    // 获取包名
                    PackageElement convertPackageElement = mElementUtils.getPackageOf(convertElement);
                    methodBuilder.addStatement("$T function = new $T()"
                            , ParameterizedTypeName.get(ClassName.get(convertPackageElement.getQualifiedName().toString(), convertElement.getSimpleName().toString()), subName.typeArguments.get(0))
                            , ParameterizedTypeName.get(ClassName.get(convertPackageElement.getQualifiedName().toString(), convertElement.getSimpleName().toString()), subName.typeArguments.get(0))
                    );

                    methodBuilder.beginControlFlow("try");

                    methodBuilder.addStatement("com.zenfer.network.framwork.ZNetwork.addObservable(" + className + "Selector." + methodName + "(map)).map($L).subscribe(networkCallBack)"
                            , "function");

                    methodBuilder.addStatement(CodeBlock.of("com.zenfer.network.framwork.ZNetRxUtils.getInstance().addDisposable(networkCallBack)"));
                    methodBuilder.nextControlFlow("catch($T e)", Exception.class);
                    methodBuilder.addStatement("e.printStackTrace()");
                    methodBuilder.addStatement("networkCallBack.onError(e)");
                    methodBuilder.endControlFlow();

                    methodSpecs.add(methodBuilder.build());
                }
                TypeSpec typeSpecSelector = TypeSpec.classBuilder(className + "Request")
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

    private boolean isElementEmpty(Set<? extends Element> elements, ElementKind
            kind, Modifier modifier) {
        if (elements == null || elements.isEmpty()) {
            return false;
        }

        for (Element element : elements) {
            if (element.getKind() != kind) {
                util.logError(element, element.getSimpleName().toString() + "必须是" + ElementKind.INTERFACE + "。");
                return false;
            }
            if (!element.getModifiers().contains(modifier)) {
                util.logError(element, element.getSimpleName().toString() + "必须是" + Modifier.PUBLIC + "。");
                return false;
            }
        }
        return true;
    }
}
