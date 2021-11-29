package com.epam.esm.controller.hateoas;

import com.epam.esm.clientmodel.PageableClientModel;
import org.springframework.hateoas.Link;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Adds links to client model type of {@link PageableClientModel} .
 */
public interface PageLinker {

    /**
     * Adds links to client model type of {@link PageableClientModel} .
     *
     * @param selfLink link to current model (self link)
     * @param page     current model to add links
     */
    void addPageLinks(Link selfLink, PageableClientModel<?> page);

    /**
     * Builds link for page (client model type of {@link PageableClientModel}) .
     *
     * @param href         href of current page link
     * @param linkRelation relation for building link
     * @param pageSize     value to query param 'pageSize' in building link
     * @param pageNumber   value to query param 'pageNumber' in building link
     * @return link that was built from passed params .
     */
    static Link buildPageLink(String href, String linkRelation, Integer pageSize, Integer pageNumber) {
        return Link.of(UriComponentsBuilder.fromUriString(href)
                        .replaceQueryParam("pageSize", pageSize)
                        .replaceQueryParam("pageNumber", pageNumber).build().toUriString())
                .withRel(linkRelation);
    }
}
