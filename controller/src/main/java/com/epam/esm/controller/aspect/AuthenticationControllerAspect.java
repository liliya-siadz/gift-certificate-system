package com.epam.esm.controller.aspect;

import com.epam.esm.clientmodel.UserClientModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for controller class {@link com.epam.esm.controller.controller.AuthenticationController}
 * calls HATEOAS linker on returning client models  .
 */
@Aspect
@Component
public class AuthenticationControllerAspect extends ControllerAspect {

    /**
     * Adds links to returning value of method
     * {@link com.epam.esm.controller.controller.AuthenticationController#signup} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.controller.AuthenticationController.signup(..))")
    public Object addLinks(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        UserClientModel user = (UserClientModel) proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        hateoasLinker.addLinks(user);
        return user;
    }
}
