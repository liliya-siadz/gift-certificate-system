package com.epam.esm.controller.aspect;

import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.hateoas.HateoasLinker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Aspect for controller class {@link com.epam.esm.controller.OrderController}
 * calls HATEOAS linker on returning client models  .
 */
@Aspect
@Component
public class OrderControllerAspect {

    /**
     * Adds links to client models .
     */
    private final HateoasLinker hateoasLinker;

    /**
     * Constructs <code>OrderControllerAspect</code> class
     * with injected HATEOAS linker .
     *
     * @param hateoasLinker {@link #hateoasLinker}
     */
    @Autowired
    public OrderControllerAspect(HateoasLinker hateoasLinker) {
        this.hateoasLinker = hateoasLinker;
    }

    /**
     * Adds links to returning value of method {@link OrderController#getAll} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.OrderController.getAll(..)))")
    public Object addLinksToGetAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        PageableClientModel<OrderClientModel> page =
                (PageableClientModel<OrderClientModel>) proceedingJoinPoint.proceed();
        page.getElements().forEach(hateoasLinker::addLinks);
        hateoasLinker.addLinks(linkTo(methodOn(OrderController.class)
                .getAll(page.getPageSize(), page.getPageNumber())).withSelfRel(), page);
        return page;
    }

    /**
     * Adds links to returning value of methods {@link OrderController#create},
     * {@link OrderController#getById} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.OrderController.create(..))"
            + "|| execution(* com.epam.esm.controller.OrderController.getById(..))")
    public Object addLinks(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        OrderClientModel order = (OrderClientModel)
                ((args != null) ? proceedingJoinPoint.proceed(args) : proceedingJoinPoint.proceed());
        hateoasLinker.addLinks(order);
        return order;
    }
}
