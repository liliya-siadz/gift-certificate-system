package com.epam.esm.controller.aspect;

import com.epam.esm.controller.hateoas.HateoasLinker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Base abstract class for controller's aspects .
 */
@Component
public abstract class ControllerAspect {

    /**
     * Adds links to client models .
     */
    @Autowired
    protected HateoasLinker hateoasLinker;
}
