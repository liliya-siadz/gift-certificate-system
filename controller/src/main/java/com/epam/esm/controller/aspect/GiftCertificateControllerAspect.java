package com.epam.esm.controller.aspect;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.hateoas.HateoasLinker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Aspect for controller class {@link GiftCertificateController}
 * calls HATEOAS linker on returning client models  .
 */
@Aspect
@Component
public class GiftCertificateControllerAspect {

    /**
     * Adds links to client models .
     */
    private final HateoasLinker hateoasLinker;

    /**
     * Constructs <code>GiftCertificateControllerAspect</code> class
     * with injected HATEOAS linker .
     *
     * @param hateoasLinker {@link #hateoasLinker}
     */
    @Autowired
    public GiftCertificateControllerAspect(HateoasLinker hateoasLinker) {
        this.hateoasLinker = hateoasLinker;
    }

    /**
     * Adds links to returning value of method {@link GiftCertificateController#getAll .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.GiftCertificateController.getAll(..)))")
    public Object addLinksToGetAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        PageableClientModel<GiftCertificateClientModel> page = (PageableClientModel<GiftCertificateClientModel>)
                proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        page.getElements().forEach(hateoasLinker::addLinks);
        hateoasLinker.addLinks(linkTo(methodOn(GiftCertificateController.class)
                .getAll(page.getPageSize(), page.getPageNumber())).withSelfRel(), page);
        return page;
    }

    /**
     * Adds links to returning value of method {@link GiftCertificateController#search} .
     *
     * @param proceedingJoinPoint joint point for method
     * @param tagName             argument 'tagName' for method
     * @param name                argument 'name' for method
     * @param description         argument 'description' for method
     * @param sortField           argument 'sortField' for method
     * @param sortDirection       argument 'sortDirection' for method
     * @param pageSize            argument 'pageSize' for method
     * @param pageNumber          argument 'pageNumber' for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.GiftCertificateController.search(..)) "
            + " && args(tagName, name, description, sortField, sortDirection, pageSize, pageNumber)")
    public Object addLinksToSearch(ProceedingJoinPoint proceedingJoinPoint,
                                   String tagName, String name, String description,
                                   String sortField, String sortDirection,
                                   Integer pageSize, Integer pageNumber) throws Throwable {
        PageableClientModel<GiftCertificateClientModel> page = (PageableClientModel<GiftCertificateClientModel>)
                proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        page.getElements().forEach(hateoasLinker::addLinks);
        hateoasLinker.addLinks(linkTo(methodOn(GiftCertificateController.class)
                .search(tagName, name, description, sortField, sortDirection, pageSize, pageNumber))
                .withSelfRel().expand(), page);
        return page;
    }

    /**
     * Adds links to returning value of method {@link GiftCertificateController#searchByTags} .
     *
     * @param proceedingJoinPoint joint point for method
     * @param tags                argument 'tags' for method
     * @param pageSize            argument 'pageSize' for method
     * @param pageNumber          argument 'pageNumber' for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.GiftCertificateController.searchByTags(..)) "
            + "&& args(tags, pageSize, pageNumber)")
    public Object addLinksToSearchByTags(ProceedingJoinPoint proceedingJoinPoint, List<String> tags,
                                         Integer pageSize, Integer pageNumber) throws Throwable {
        PageableClientModel<GiftCertificateClientModel> page = (PageableClientModel<GiftCertificateClientModel>)
                proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        page.getElements().forEach(hateoasLinker::addLinks);
        hateoasLinker.addLinks(linkTo(methodOn(GiftCertificateController.class)
                .searchByTags(tags, pageSize, pageNumber)).withSelfRel().expand(), page);
        return page;
    }

    /**
     * Adds links to returning value of methods {@link GiftCertificateController#create},
     * {@link com.epam.esm.controller.GiftCertificateController#getById},
     * {@link com.epam.esm.controller.GiftCertificateController#update},
     * {@link com.epam.esm.controller.GiftCertificateController#updatePrice} .
     *
     * @param proceedingJoinPoint joint point for method
     * @return result of executed method with added HATEOAS links
     * @throws Throwable if invoked method throws anything
     */
    @Around("execution(* com.epam.esm.controller.GiftCertificateController.create(..))"
            + "|| execution(* com.epam.esm.controller.GiftCertificateController.getById(..))"
            + "|| execution(* com.epam.esm.controller.GiftCertificateController.update(..))"
            + "|| execution(* com.epam.esm.controller.GiftCertificateController.updatePrice(..))")
    public Object addLinks(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        GiftCertificateClientModel certificate = (GiftCertificateClientModel) proceedingJoinPoint
                .proceed(proceedingJoinPoint.getArgs());
        hateoasLinker.addLinks(certificate);
        return certificate;
    }
}
