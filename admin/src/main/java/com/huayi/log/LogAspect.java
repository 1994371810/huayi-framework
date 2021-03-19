package com.huayi.log;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.huayi.log.enums.EnableLog;
import com.huayi.log.service.LogService;
import com.huayi.util.IPUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author gjw
 * @Date 2021/3/19 14:30
 **/
@Configuration
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LogAspect {

    @Autowired
    private LogService logService;

    @Pointcut("@annotation( enableLog )")
    public void pointCut(EnableLog enableLog){}

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5,5,5, TimeUnit.SECONDS,new LinkedBlockingQueue(50));

    @Around("pointCut( enableLog )")
    public Object around(ProceedingJoinPoint proceedingJoinPoint,EnableLog enableLog){

        String methodName   = getMethodName(proceedingJoinPoint);
        String args         = getArgs(proceedingJoinPoint);
        String uri          = getUri();
        String ip           = IPUtil.getIpAddr();
        String ipInfo       = IPUtil.getIpInfo(ip);
        String errorInfo    = "";
        String returnInfo   = "";

        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            errorInfo = throwable.getMessage();
        }
        returnInfo = (  ObjectUtil.isBasicType(result) || result instanceof String) ? result.toString() : JSONUtil.toJsonStr(result);

        long end = System.currentTimeMillis();

        LogDto logDto = new LogDto(methodName,uri,args, StrUtil.isBlank(errorInfo),ip,ipInfo,end-start,errorInfo,returnInfo);

        executor.execute( () ->{ logService.save(logDto); });

        return result;
    }

    private String getMethodName( ProceedingJoinPoint proceedingJoinPoint ){
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        return className+"."+methodName;
    }

    private String getArgs(ProceedingJoinPoint proceedingJoinPoint){

        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();

        String[] parameterNames = signature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();

        if(parameterNames == null || parameterNames.length<1){
            return "";
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parameterNames.length; i++) {
            Object o = args[i];
            sb.append(parameterNames[i]);
            sb.append(":");

            if( o ==null ){
                sb.append("null");
                sb.append(i==args.length-1 ? "" : ", ");
                continue;
            }

            sb.append(  (  ObjectUtil.isBasicType(o) || o instanceof String) ? o.toString() : JSONUtil.toJsonStr(o));
            sb.append(i==args.length-1 ? "" : ", ");
        }

        return sb.toString();
    }

    private String getUri(){
        ServletRequestAttributes a = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String uri =a.getRequest().getRequestURI();
        return uri;
    }
}
