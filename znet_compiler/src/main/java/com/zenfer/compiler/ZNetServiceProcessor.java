package com.zenfer.compiler;

import android.support.annotation.StringDef;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.zenfer.annotation.ZNetApi;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import retrofit2.http.GET;
import retrofit2.http.POST;

@AutoService(Processor.class)
public class ZNetServiceProcessor extends AbstractProcessor {

    private Messager mMessager;
    private Elements mElementUtils;
    private Types mTypes;
    private Filer mFiler;


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        logNote("processing...");
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ZNetApi.class);
        if (elements == null || elements.isEmpty()) {
            return false;
        }

        mTypes = processingEnv.getTypeUtils();
        mFiler = processingEnv.getFiler();

        for (Element element : elements) {
            if (element.getKind() != ElementKind.INTERFACE) {
                logError(element, "被ZNetApi注解的必须是接口。");
                return false;
            }
            if (!element.getModifiers().contains(Modifier.PUBLIC)) {
                logError(element, "被ZNetApi注解的接口必须是公开的。");
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
                MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("get")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addException(Exception.class)
                        .addParameter(ParameterSpec.builder(String.class, "tag").addAnnotation(ClassName.get(packageName, className + "Enum")).build())
                        .addParameter(Object.class, "params");
                List<? extends Element> childElements = classElement.getEnclosedElements();
                if (childElements == null || elements.isEmpty()) {
                    logError(classElement, "被ZNetApi注解的接口必须有 Observable<NetWordResult> 返回值的方法");
                    return false;
                }
                // 添加方法返回类型
                methodBuilder.returns(TypeName.get(((ExecutableElement) childElements.get(0)).getReturnType()));
                ParameterSpec.Builder builder = ParameterSpec.builder(classElement.getClass(), "api");
                methodBuilder.addStatement(CodeBlock.of(element.getSimpleName().toString() + " api = com.zenfer.network.framwork.ZNetwork.getInstance().getApi(" + element.getSimpleName().toString() + ".class)"));
                // 开始写switch方法
                methodBuilder.beginControlFlow("switch (tag)");
                for (Element childElement : childElements) {
                    if (!(childElement instanceof ExecutableElement)) {
                        continue;
                    }
                    methodBuilder.addCode("case " + className + "Enum." + childElement.getSimpleName().toString().toUpperCase() + ":\n");
                    ExecutableElement executableElement = (ExecutableElement) childElement;
                    String methodName = String.valueOf(childElement.getSimpleName());
                    //地址检查
                    POST post = executableElement.getAnnotation(POST.class);
                    GET get = executableElement.getAnnotation(GET.class);
//                    PUT put = executableElement.getAnnotation(PUT.class);
//                    DELETE delete = executableElement.getAnnotation(DELETE.class);
                    if (post == null && get == null
//                            && put == null && delete == null
                    ) {
                        logError(executableElement, "方法必须有Post、Get、Put、Delete注解");
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
                        logError(executableElement, "Url地址不允许为空。");
                        return false;
                    }
                    methodBuilder.addCode("return api." + methodName + requestTypeValue + ";\n");
                }
                methodBuilder.addCode("default: \nthrow new Exception(\"can not match the request tag \\\"\" + tag + \"\\\"\");");
                methodBuilder.endControlFlow();

                TypeSpec typeSpecSelector = TypeSpec.classBuilder(className + "Selector")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(methodBuilder.build())
                        .build();
                //　执行selector文件生成
                JavaFile javaSelectorFile = JavaFile.builder(packageName, typeSpecSelector).build();
                javaSelectorFile.writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                e.printStackTrace();
                logNote("process finish with error ...");
                return false;
            }
        }
        logNote("process finish ...");
        return true;
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(ZNetApi.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void logError(Element element, String str) {
        log(Diagnostic.Kind.ERROR, element, str);
    }

    private void logNote(String str) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, str);
    }

    private void log(Diagnostic.Kind kind, Element element, String str) {
        processingEnv.getMessager().printMessage(kind, str, element);
    }
}
