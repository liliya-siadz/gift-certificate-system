package com.epam.esm.repository;

import com.epam.esm.entity.TagEntity;
import org.springframework.data.jpa.repository.Query;

/**
 * Presents access to repository operations with Tag .
 */
public interface TagRepository extends Repository<TagEntity> {

    /**
     * Finds most popular Tag (most used Tag of User with the biggest amount of Orders) .
     *
     * @return most popular Tag
     */
    @Query(value = "SELECT tag.id, tag.name FROM tag"
            + " JOIN gift_certificates_tags"
            + " ON gift_certificates_tags.tag_id=tag.id"
            + " JOIN orders_gift_certificates"
            + " ON orders_gift_certificates.gift_certificate_id =gift_certificates_tags.gift_certificate_id"
            + " JOIN public.order"
            + " ON public.order.id=public.orders_gift_certificates.order_id"
            + " AND public.order.user_id="
            + "(SELECT public.user.id FROM public.user"
            + " JOIN public.order"
            + " ON public.order.user_id=public.user.id"
            + " GROUP BY public.user.id ORDER BY sum(public.order.cost) DESC LIMIT 1)"
            + " GROUP BY tag.id ORDER BY count(tag.id) DESC LIMIT 1", nativeQuery = true)
    TagEntity findMostPopularTag();

    @Override
    default Class<TagEntity> getEntityClass() {
        return TagEntity.class;
    }
}
