package hotel.management.system.be.application.account.repository;

import hotel.management.system.be.application.account.dto.AccountDetailsRequest;
import hotel.management.system.be.application.account.filters.AccountDetailsFilter;
import hotel.management.system.be.application.account.vo.AccountSpecificationVO;
import hotel.management.system.be.entities.AccountEntity;
import hotel.management.system.be.entities.AccountRole;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;


/**
 * Specyfikacje wyszukiwania kont użytkowników
 */
class AccountSpecification {

    private AccountSpecification() {
    }

    /**
     * Gets specification and page.
     *
     * @param request  request z filtrami
     * @param username login użytkownika
     * @return specyfikacje wyszukiwania
     */
    static AccountSpecificationVO getSpecificationAndPage(AccountDetailsRequest request, String username) {
        return AccountSpecificationVO.builder()
                .pageRequest(PageRequest.of(request.getPage(), request.getSize()))
                .specification(
                        ObjectUtils.isEmpty(request.getFilters())
                                ? Specification.where(null)
                                : specificationForAccountDetails(request.getFilters(), username))
                .build();
    }

    private static Specification<AccountEntity> specificationForAccountDetails(AccountDetailsFilter filters, String username) {
        Specification<AccountEntity> specification = notEqualUsername(username);
        if (StringUtils.hasLength(filters.getId())) {
            specification = specification.and(byId(filters.getId()));
        }
        if (StringUtils.hasLength(filters.getUsername())) {
            specification = specification.and(byUsername(filters.getUsername()));
        }
        if (StringUtils.hasLength(filters.getFirstName())) {
            specification = specification.and(byFirstName(filters.getFirstName()));
        }
        if (StringUtils.hasLength(filters.getSecondName())) {
            specification = specification.and(bySecondName(filters.getSecondName()));
        }
        if (!ObjectUtils.isEmpty(filters.getRole())) {
            specification = specification.and(byRole(filters.getRole()));
        }
        return specification;
    }

    private static Specification<AccountEntity> byId(String id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), UUID.fromString(id));
    }

    private static Specification<AccountEntity> byUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("username"), username);
    }

    private static Specification<AccountEntity> byFirstName(String firstName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("firstName"), firstName);
    }

    private static Specification<AccountEntity> bySecondName(String secondName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("secondName"), secondName);
    }

    private static Specification<AccountEntity> byRole(AccountRole role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"), role);
    }

    private static Specification<AccountEntity> notEqualUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("username"), username);
    }
}
