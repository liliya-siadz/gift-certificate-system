package com.epam.esm.service;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents map of classes (client models or entity) to resource names .
 */
public class ResourceNames {
    /**
     * Map of resource names, where key - class of client model or entity model
     * of resource, value - name of resource .
     */
    private static final Map<Class<?>, String> resourceNames = new HashMap<>();

    static {
        resourceNames.put(GiftCertificateClientModel.class, "Gift Certificate");
        resourceNames.put(GiftCertificateEntity.class, "Gift Certificate");
        resourceNames.put(TagClientModel.class, "Tag");
        resourceNames.put(TagEntity.class, "Tag");
        resourceNames.put(UserClientModel.class, "User");
        resourceNames.put(UserEntity.class, "User");
        resourceNames.put(OrderClientModel.class, "Order");
        resourceNames.put(OrderEntity.class, "Order");
    }

    private ResourceNames() {
    }

    /**
     * Retrieves resource name by class of client model .
     *
     * @param clientModelClass class of client model
     * @return resource name if any value exists by this key,
     * otherwise - simple name of passed class
     */
    public static String getResourceName(Class<?> clientModelClass) {
        String resourceName = ResourceNames.resourceNames.get(clientModelClass);
        return resourceName == null ? clientModelClass.getSimpleName() : resourceName;
    }
}
