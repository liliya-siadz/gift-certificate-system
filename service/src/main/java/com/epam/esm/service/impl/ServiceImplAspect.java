package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.clientmodel.UserClientModel;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Aspect for classes from package {@link com.epam.esm.service.impl} .
 */
@Aspect
@Component
public class ServiceImplAspect {

    /**
     * Clear id of client model before it will be created .
     * {@link com.epam.esm.service.impl.TagServiceImpl#create} .
     *
     * @param model model that will be created
     */
    @Before("execution(* com.epam.esm.service.impl.TagServiceImpl.create(..)) && args(model)")
    public void clearId(TagClientModel model) {
        model.setId(null);
    }

    /**
     * Clear id of client model before it will be created .
     * {@link com.epam.esm.service.impl.GiftCertificateServiceImpl#create} .
     *
     * @param model model that will be created
     */
    @Before("execution(* com.epam.esm.service.impl.GiftCertificateServiceImpl.create(..)) && args(model)")
    public void clearId(GiftCertificateClientModel model) {
        model.setId(null);
    }

    /**
     * Clear id of client model before it will be created .
     * {@link com.epam.esm.service.impl.OrderServiceImpl#create} .
     *
     * @param model model that will be created
     */
    @Before("execution(* com.epam.esm.service.impl.OrderServiceImpl.create(..)) && args(model)")
    public void clearId(OrderClientModel model) {
        model.setId(null);
    }

    /**
     * Clear id of client model before it will be created .
     * {@link com.epam.esm.service.impl.UserServiceImpl#create} .
     *
     * @param model model that will be created
     */
    @Before("execution(* com.epam.esm.service.impl.UserServiceImpl.create(..)) && args(model)")
    public void clearId(UserClientModel model) {
        model.setId(null);
    }
}
