package com.epam.esm.controller.hateoas;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.RequestOrderClientModel;
import com.epam.esm.clientmodel.ResponseOrderClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.clientmodel.UserClientModel;
import org.springframework.hateoas.Link;

/**
 * Adds links to client models .
 */
public interface HateoasLinker {

    /**
     * Adds links to client model type of {@link PageableClientModel} .
     *
     * @param selfLink link to current model (self link)
     * @param page     current model to add links
     */
    void addLinks(Link selfLink, PageableClientModel<?> page);

    /**
     * Adds links to client model type of {@link TagClientModel} .
     *
     * @param tag current client model of Tag resource to add links
     */
    void addLinks(TagClientModel tag);

    /**
     * Adds links to client model type of {@link RequestOrderClientModel} .
     *
     * @param order current client model of Order resource to add links
     */
    void addLinks(RequestOrderClientModel order);

    /**
     * Adds links to client model type of {@link ResponseOrderClientModel} .
     *
     * @param order current client model of Order resource to add links
     */
    void addLinks(ResponseOrderClientModel order);

    /**
     * Adds links to client model type of {@link UserClientModel} .
     *
     * @param user current client model of User resource to add links
     */
    void addLinks(UserClientModel user);

    /**
     * Adds links to client model type of {@link GiftCertificateClientModel} .
     *
     * @param certificate current client model of Gift Certificate resource to add links
     */
    void addLinks(GiftCertificateClientModel certificate);
}
