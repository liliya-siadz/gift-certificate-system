package com.epam.esm.controller.hateoas;

import com.epam.esm.clientmodel.PageableClientModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static com.epam.esm.controller.hateoas.PageLinker.buildPageLink;

/**
 * Implementation of {@link PageLinker} interface,
 * for adding links to client model type of {@link PageableClientModel} .
 */
@Component
public class PageLinkerImpl implements PageLinker {
    @Override
    public void addPageLinks(Link selfLink, PageableClientModel<?> page) {
        addSelfPageLink(selfLink, page);
        String href = selfLink.getHref();
        addPreviousPageLink(href, page);
        addNextPageLink(href, page);
    }

    private void addSelfPageLink(Link selfLink, PageableClientModel<?> page) {
        page.add(selfLink);
    }

    private void addPreviousPageLink(String href, PageableClientModel<?> page) {
        int pageNumber = page.getPageNumber();
        if (pageNumber > 1) {
            page.add(buildPageLink(href, "previous page", page.getPageSize(), --pageNumber));
        }
    }

    private void addNextPageLink(String href, PageableClientModel<?> page) {
        int pageNumber = page.getPageNumber();
        if (pageNumber < page.getTotalPages()) {
            page.add(buildPageLink(href, "next page", page.getPageSize(), ++pageNumber));
        }
    }
}
