package com.epam.esm.controller.aspect;

import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.ResponseOrderClientModel;
import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.controller.UserController;
import com.epam.esm.controller.hateoas.HateoasLinker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Aspect for controller class {@link UserController}
 * calls HATEOAS linker on returning client models  .
 */
@Aspect
@Component
public class UserControllerAspect {

    /**
     * Adds links to client models .
     */
    private final HateoasLinker hateoasLinker;

    /**
     * Constructs <code>UserControllerAspect</code> class
     * with injected HATEOAS linker .
     *
     * @param hateoasLinker {@link #hateoasLinker}
     */
    @Autowired
    public UserControllerAspect(HateoasLinker hateoasLinker) {
        this.hateoasLinker = hateoasLinker;
    }

    /**
     * Adds links to returning value of method {@link UserController#getAll} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.UserController.getAll(..))")
    public Object addLinksToGetAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        PageableClientModel<UserClientModel> page =
                (PageableClientModel<UserClientModel>) proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        page.getElements().forEach(hateoasLinker::addLinks);
        hateoasLinker.addLinks(linkTo(methodOn(UserController.class)
                .getAll(page.getPageSize(), page.getPageNumber())).withSelfRel().expand(), page);
        return page;
    }

    /**
     * Adds links to returning value of method {@link UserController#getUserOrders} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.UserController.getUserOrders(..))"
            + " && args(id, pageSize, pageNumber)")
    public Object addLinksToGetUserOrders(ProceedingJoinPoint proceedingJoinPoint, Long id,
                                          Integer pageSize, Integer pageNumber) throws Throwable {
        PageableClientModel<ResponseOrderClientModel> page = (PageableClientModel<ResponseOrderClientModel>)
                proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        page.getElements().forEach(hateoasLinker::addLinks);
        hateoasLinker.addLinks(linkTo(methodOn(UserController.class)
                .getUserOrders(id, pageSize, pageNumber)).withSelfRel(), page);
        return page;
    }

    /**
     * Adds links to returning value of method {@link UserController#getById(Long)} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.UserController.getById(..))")
    public Object addLinks(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        UserClientModel user = (UserClientModel) proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        hateoasLinker.addLinks(user);
        return user;
    }

    /**
     * Adds links to returning value of method {@link UserController#getUserOrderById} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.UserController.getUserOrderById(..))")
    public Object addLinksToGetUserOrderById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ResponseOrderClientModel order = (ResponseOrderClientModel) proceedingJoinPoint
                .proceed(proceedingJoinPoint.getArgs());
        hateoasLinker.addLinks(order);
        return order;
    }
}
