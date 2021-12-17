package com.epam.esm.controller.aspect;

import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.controller.controller.TagController;
import com.epam.esm.controller.hateoas.HateoasLinker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Aspect for controller class {@link TagController}
 * calls HATEOAS linker on returning client models  .
 */
@Aspect
@Component
public class TagControllerAspect {

    /**
     * Adds links to client models .
     */
    private final HateoasLinker hateoasLinker;

    /**
     * Constructs <code>TagControllerAspect</code> class
     * with injected HATEOAS linker .
     *
     * @param hateoasLinker {@link #hateoasLinker}
     */
    @Autowired
    public TagControllerAspect(HateoasLinker hateoasLinker) {
        this.hateoasLinker = hateoasLinker;
    }

    /**
     * Adds links to returning value of method {@link TagController#getAll} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.controller.TagController.getAll(..)))")
    public Object addLinksToGetAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        PageableClientModel<TagClientModel> page = (PageableClientModel<TagClientModel>) proceedingJoinPoint
                .proceed(proceedingJoinPoint.getArgs());
        page.getElements().forEach(hateoasLinker::addLinks);
        hateoasLinker.addLinks(linkTo(methodOn(TagController.class)
                .getAll(page.getPageSize(), page.getPageNumber())).withSelfRel(), page);
        return page;
    }

    /**
     * Adds links to returning value of methods {@link TagController#getById}, {@link TagController#create},
     * {@link TagController#findMostPopularTag()} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.controller.TagController.getById(..))"
            + "|| execution(* com.epam.esm.controller.controller.TagController.create(..))"
            + "|| execution(* com.epam.esm.controller.controller.TagController.findMostPopularTag())")
    public Object addLinks(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        TagClientModel tag = (TagClientModel)
                ((args != null) ? proceedingJoinPoint.proceed(args) : proceedingJoinPoint.proceed());
        hateoasLinker.addLinks(tag);
        return tag;
    }
}
