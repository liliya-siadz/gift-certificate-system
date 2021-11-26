package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.preparator.Preparator;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.ResourceNames;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link OrderService} interface,
 * for presenting access to service operations with Order .
 */
@Service
public class OrderServiceImpl extends AbstractService<OrderEntity, OrderClientModel> implements OrderService {

    /**
     * Dao class for repository operations .
     */
    @Autowired
    private OrderDao dao;

    /**
     * Mapper for mapping from entity to client model and otherwise .
     */
    @Autowired
    private Mapper<OrderEntity, OrderClientModel> mapper;

    /**
     * Preparator for preparing Order client models to service operations .
     */
    @Autowired
    private Preparator<OrderClientModel> preparator;

    /**
     * Service for operations with Gift Certificate .
     */
    @Autowired
    private GiftCertificateService certificateService;

    /**
     * Service for operations with User .
     */
    @Autowired
    private UserService userService;

    /**
     * Constructs <code>TagServiceImpl</code> class
     * with dao, mapper, validator and Gift Certificate service
     *
     * @param dao                {@link #dao}
     * @param mapper             {@link #mapper}
     * @param certificateService {@link #certificateService}
     * @param userService        {@link #userService}
     */
    public OrderServiceImpl(OrderDao dao, OrderMapper mapper, GiftCertificateService certificateService,
                            UserService userService) {
        super(dao, mapper);
        this.certificateService = certificateService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public OrderClientModel create(OrderClientModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        preparator.prepareForCreate(model);
        List<GiftCertificateClientModel> certificates = new ArrayList<>(model.getCertificates());
        model.setCost(calculateOrderCost(certificates));
        model.getCertificates().clear();
        model.setUser(userService.findById(model.getUser().getId()));
        long orderId = dao.create(mapper.toEntity(model)).getId();
        certificateService.updateNewOrderCertificates(orderId, certificates);
        return findById(orderId);
    }

    @Override
    public PageableClientModel<OrderClientModel> findUserOrders(Long userId, Integer pageSize, Integer pageNumber) {
        if ((userId == null) || (pageSize == null) || (pageNumber == null)) {
            throw new IllegalArgumentException("Parameter 'id' or 'pageSize' or 'pageNumber' is null.");
        }
        if (!userService.isExist(userId)) {
            throw new ResourceWithIdNotFoundException(ResourceNames.getResourceName(UserEntity.class), userId);
        }
        return mapper.toClientModel(dao.findUserOrders(userId, pageSize, pageNumber));
    }

    @Override
    public OrderClientModel findUserOrder(Long userId, Long orderId) {
        if ((userId == null) || (orderId == null)) {
            throw new IllegalArgumentException("Parameter 'userId' or 'orderId' is null.");
        }
        if (!userService.isExist(userId)) {
            throw new ResourceWithIdNotFoundException(ResourceNames.getResourceName(UserEntity.class), userId);
        }
        if (!isExist(orderId)) {
            throw new ResourceWithIdNotFoundException(ResourceNames.getResourceName(OrderEntity.class), orderId);
        }
        return mapper.toClientModel(dao.findUserOrder(userId, orderId));
    }

    private BigDecimal calculateOrderCost(List<GiftCertificateClientModel> certificates) {
        return certificates.stream().map(certificate -> certificateService.findById(certificate.getId()).getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
