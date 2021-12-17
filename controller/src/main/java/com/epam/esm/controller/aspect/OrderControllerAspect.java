package com.epam.esm.controller.aspect;

import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.controller.controller.OrderController;
<<<<<<< HEAD
=======
import com.epam.esm.controller.hateoas.HateoasLinker;
>>>>>>> 79b8fef (1) Jwt token created; 2) Signup and login functions realized)
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Aspect for controller class {@link OrderController}
 * calls HATEOAS linker on returning client models  .
 */
@Aspect
@Component
public class OrderControllerAspect extends ControllerAspect {

    /**
     * Adds links to returning value of method {@link OrderController#getAll} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.controller.OrderController.getAll(..)))")
    public Object addLinksToGetAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        PageableClientModel<OrderClientModel> page = (PageableClientModel<OrderClientModel>)
                proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        page.getElements().forEach(hateoasLinker::addLinks);
        hateoasLinker.addLinks(linkTo(methodOn(OrderController.class)
                .getAll(page.getPageSize(), page.getPageNumber())).withSelfRel(), page);
        return page;
    }

    /**
     * Adds links to returning value of methods {@link OrderController#getById},
     * {@link OrderController#create} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.controller.OrderController.getById(..))"
            + "|| execution(* com.epam.esm.controller.controller.OrderController.create(..))")
    public Object addLinksToResponseModel(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        OrderClientModel order = (OrderClientModel) proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        hateoasLinker.addLinks(order);
        return order;
    }
}
