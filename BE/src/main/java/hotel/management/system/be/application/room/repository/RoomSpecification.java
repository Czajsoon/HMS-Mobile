package hotel.management.system.be.application.room.repository;

import hotel.management.system.be.application.room.dto.RoomDetailsRequest;
import hotel.management.system.be.application.room.filters.RoomDetailsFilter;
import hotel.management.system.be.application.room.vo.RoomSpecificationVO;
import hotel.management.system.be.entities.RoomEntity;
import hotel.management.system.be.entities.RoomStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

/**
 * The type Room specification.
 */
public class RoomSpecification {

    private RoomSpecification() {
    }

    /**
     * Gets specification and page.
     *
     * @param request the request
     * @return the specification and page
     */
    static RoomSpecificationVO getSpecificationAndPage(RoomDetailsRequest request) {
        return RoomSpecificationVO.builder()
                .pageRequest(PageRequest.of(request.getPage(), request.getSize()))
                .specification(
                        ObjectUtils.isEmpty(request.getFilters())
                                ? Specification.where(null)
                                : specificationForRoomDetails(request.getFilters()))
                .build();
    }

    private static Specification<RoomEntity> specificationForRoomDetails(RoomDetailsFilter filters) {
        Specification<RoomEntity> specification= new Specification<RoomEntity>() {
            @Override
            public Predicate toPredicate(Root<RoomEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };

        if (!ObjectUtils.isEmpty(filters.getBalconyAvailable())) {
            specification = specification.and(byBalconyAvailable(filters.getBalconyAvailable()));
        }
        if (!ObjectUtils.isEmpty(filters.getLevel())) {
            specification = specification.and(byLevel(filters.getLevel()));
        }
        if (!ObjectUtils.isEmpty(filters.getRoomStatus())) {
            specification = specification.and(byRoomStatus(filters.getRoomStatus()));
        }
        if (!ObjectUtils.isEmpty(filters.getPricePerNight())) {
            specification = specification.and(byPricePerNight(filters.getPricePerNight()));
        }
        if (!ObjectUtils.isEmpty(filters.getBedsNumber())) {
            specification = specification.and(byBedsNumber(filters.getBedsNumber()));
        }
        return specification;
    }

    private static Specification<RoomEntity> byBalconyAvailable(Boolean balconyAvailable) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("balconyAvailable"), balconyAvailable);
    }

    private static Specification<RoomEntity> byLevel(Integer level) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("level"), level);
    }

    private static Specification<RoomEntity> byRoomStatus(RoomStatus roomStatus) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("roomStatus"), roomStatus);
    }

    private static Specification<RoomEntity> byPricePerNight(BigDecimal pricePerNight) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("pricePerNight"), pricePerNight);
    }

    private static Specification<RoomEntity> byBedsNumber(Integer bedsNumber) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bedsNumber"), bedsNumber);
    }

    private static Specification<RoomEntity> notEqualLevel(String level) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("level"), level);
    }
}
