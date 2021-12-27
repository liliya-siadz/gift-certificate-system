package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.preparator.Preparator;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.ResourceNames;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.epam.esm.service.ResourceNames.getResourceName;

/**
 * Implementation of {@link OrderService} interface,
 * for presenting access to service operations with Order .
 */
@Service
public class OrderServiceImpl extends AbstractService<OrderEntity, OrderClientModel>
        implements OrderService {

    /**
     * Preparator for preparing Order request client models to service operations .
     */
    @Autowired
    private Preparator<OrderClientModel> preparator;

    /**
     * Service for operations with Gift Certificate .
     */
    @Autowired
    private GiftCertificateService certificateService;

    /**
     * Mappers for mapping Gift Certificate entities to client models and otherwise .
     */
    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    /**
     * Service for operations with User .
     */
    @Autowired
    private UserService userService;

    /**
     * Constructs <code>TagServiceImpl</code> class
     * with dao, mapper, validator and Gift Certificate service
     *
     * @param repository         {@link #repository}
     * @param mapper             {@link #mapper}
     * @param certificateService {@link #certificateService}
     * @param userService        {@link #userService}
     */
    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper,
                            GiftCertificateService certificateService,
                            UserService userService) {
        super(repository, mapper);
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
        userService.findById(model.getUserId());
        List<GiftCertificateClientModel> certificates = new ArrayList<>(model.getCertificates());
        model.setCost(calculateOrderCost(certificates));
        model.getCertificates().clear();
        long orderId = repository.save(mapper.toEntity(model)).getId();
        updateNewOrderCertificates(orderId, certificates);
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
        return mapper.toClientModel(((OrderRepository) repository).findByUserId(
                userId, PageRequest.of(--pageNumber, pageSize)));
    }

    @Override
    public OrderClientModel findUserOrder(Long userId, Long orderId) {
        if ((userId == null) || (orderId == null)) {
            throw new IllegalArgumentException("Parameter 'userId' or 'orderId' is null.");
        }
        if (!userService.isExist(userId)) {
            throw new ResourceWithIdNotFoundException(ResourceNames.getResourceName(UserEntity.class), userId);
        }
        return mapper.toClientModel(repository.findById(orderId).orElseThrow(() -> new ResourceWithIdNotFoundException
                (getResourceName(repository.getEntityClass()), orderId)));
    }

    private void updateNewOrderCertificates(Long orderId, List<GiftCertificateClientModel> certificates) {
        if ((orderId == null) || (certificates == null) || (certificates.stream().anyMatch(Objects::isNull))) {
            throw new IllegalArgumentException(
                    "Parameter 'orderId' or 'certificates' is null, or list 'certificates' contains null value.");
        }
        certificates.forEach(certificate -> boundCertificateToOrder(certificate.getId(), orderId));
    }

    private void boundCertificateToOrder(Long certificateId, Long orderId) {
        OrderEntity entity = repository.findById(orderId).orElseThrow(
                () -> new ResourceWithIdNotFoundException(getResourceName(repository.getEntityClass()), orderId));
        entity.getCertificates().add(giftCertificateMapper.toEntity(certificateService.findById(certificateId)));
        repository.save(entity);
    }

    private BigDecimal calculateOrderCost(List<GiftCertificateClientModel> certificates) {
        return certificates.stream().map(certificate -> certificateService.findById(certificate.getId()).getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
