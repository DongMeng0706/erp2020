package com.dong.common.aspect;

import com.dong.system.service.ILoginfoService ;
import com.dong.system.utils.HttpContextRequestUtil;
import com.dong.system.utils.ShiroUtils;
import com.dong.system.utils.httpIpUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LogAspectJ {
   @Autowired
   ILoginfoService logService;

    @Pointcut("@annotation(com.dong.common.aspect.Log)")
    public void pointCutLog(){}

    @Around("pointCutLog()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        com.dong.system.domain.Loginfo log = new com.dong.system.domain.Loginfo();
        if(ShiroUtils.getUser()!=null){
            log.setUserid(ShiroUtils.getUserId());
            log.setUsername(ShiroUtils.getUser().getSysUser().getName());
            log.setLoginname(ShiroUtils.getUser().getSysUser().getLoginname());
        }//退出获取


        long start = System.currentTimeMillis();
        Object proceed = point.proceed();
        long end = System.currentTimeMillis();

        if(ShiroUtils.getUser()!=null) {
            System.err.println(ShiroUtils.getUser().toString());
            log.setUserid(ShiroUtils.getUser().getSysUser().getId());
            log.setUsername(ShiroUtils.getUser().getSysUser().getName());
            log.setLoginname(ShiroUtils.getUser().getSysUser().getLoginname());

        }//登录获取

        insertDatabase(point,log,start,end);
        return proceed;
    }

    public void insertDatabase(ProceedingJoinPoint joinPoint, com.dong.system.domain.Loginfo log, Long start, Long end){

        log.setLogintime(new Date());//创建时间
        log.setTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(end), ZoneId.systemDefault()));//结束时间
        log.setConsumetime(((Long)(end-start)).toString());//总耗时

        Object[] args = joinPoint.getArgs();//参数太大就不往数据库写了
        //Arrays.toString(args);
       StringBuffer str = new StringBuffer();
        Arrays.stream(args).forEach((a)->{
            str.append(a);
        });
        log.setParams(str.toString());

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Log annotation = method.getAnnotation(Log.class);
        String value = annotation.value();
        log.setOperation(value);

        String classname = joinPoint.getTarget().getClass().getName();//全类名
        String methodname = method.getName();//方法名
        log.setMethod(methodname);
        log.setOperationmethod(classname+"."+methodname);
        HttpServletRequest httpServletRequest = HttpContextRequestUtil.getHttpServletRequest();
        String realIp = httpIpUtils.getRealIp(httpServletRequest);
        log.setLoginip(realIp);//IP地址





        logService.save(log);

    }

    public static void main (String[] args) {
        System.out.println(LocalDateTime.now());
    }
}
