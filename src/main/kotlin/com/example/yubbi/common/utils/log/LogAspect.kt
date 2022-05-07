package com.example.yubbi.common.utils.log

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component
@Aspect
class LogAspect {
    private val log = LoggerFactory.getLogger(javaClass)

    @Pointcut("execution(* com.example.yubbi.services..*.*(..))")
    private fun cut() {
    }

    @Before("cut()")
    fun beforeParameterLog(joinPoint: JoinPoint) {
        val method: Method = getMethod(joinPoint)
        log.info("===== method name = {} =====", method.name)

        val args = joinPoint.args
        if (args == null) {
            log.info("no parameter")
        }
        for (arg in args) {
            if (arg != null) {
                log.info("parameter type = {}", arg.javaClass.simpleName)
            }
            log.info("parameter value = {}", arg)
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    fun afterReturnLog(joinPoint: JoinPoint, returnObj: Any?) {
        val method: Method = getMethod(joinPoint)
        log.info("===== method name = {} =====", method.name)

        if (returnObj != null) {
            log.info("return type = {}", returnObj.javaClass.simpleName)
        }
        log.info("return value = {}", returnObj)
    }

    private fun getMethod(joinPoint: JoinPoint): Method {
        val signature: MethodSignature = joinPoint.signature as MethodSignature
        return signature.method
    }
}
