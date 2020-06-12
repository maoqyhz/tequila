package xyz.fur.skeleton.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 实现自己的条件封装类，按照定义的规范拼接 Predicate
 * @author Fururur
 * @create 2019-07-19-10:04
 */
public class SimpleSpecification<T> implements Specification<T> {

    // 一级封装查询条件
    // 例如select * from table where 1 = 1 and [query.get(0)] or [query.get(1)] and ...
    // 对于一些复杂的情况，还是直接lambda实现Specification就好
    private List<QueryBean> querys;


    public SimpleSpecification(List<QueryBean> querys) {
        this.querys = querys;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        int index = 0;
        Predicate resultPre = null;
        for (QueryBean query : querys) {
            if (0 == index++) {
                resultPre = generatePredicate(root, criteriaBuilder, query);
                continue;
            }
            Predicate pre = generatePredicate(root, criteriaBuilder, query);
            if (null == pre) continue;
            switch (query.getJoin()) {
                case and:
                    resultPre = criteriaBuilder.and(resultPre, pre);
                    break;
                case or:
                    resultPre = criteriaBuilder.or(resultPre, pre);
                    break;
            }
        }
        return resultPre;
    }

    @SuppressWarnings("unchecked")
    private Predicate generatePredicate(Root<T> root, CriteriaBuilder criteriaBuilder, QueryBean query) {
        switch (query.getOperator()) {
            case eq:
                return criteriaBuilder.equal(root.get(query.getKey()), query.getValue());
            case gt:
                return criteriaBuilder.gt(root.get(query.getKey()), (Number) query.getValue());
            case ge:
                return criteriaBuilder.ge(root.get(query.getKey()), (Number) query.getValue());
            case lt:
                return criteriaBuilder.lt(root.get(query.getKey()), (Number) query.getValue());
            case le:
                return criteriaBuilder.le(root.get(query.getKey()), (Number) query.getValue());
            case notEqual:
                return criteriaBuilder.notEqual(root.get(query.getKey()), query.getValue());
            case like:
                return criteriaBuilder.like(root.get(query.getKey()), "%" + query.getValue() + "%");
            case notLike:
                return criteriaBuilder.notLike(root.get(query.getKey()), "%" + query.getValue() + "%");
            case greaterThan:
                return criteriaBuilder.greaterThan(root.get(query.getKey()), (Comparable) query.getValue());
            case greaterThanOrEqualTo:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(query.getKey()), (Comparable) query.getValue());
            case lessThan:
                return criteriaBuilder.lessThan(root.get(query.getKey()), (Comparable) query.getValue());
            case lessThanOrEqualTo:
                return criteriaBuilder.lessThanOrEqualTo(root.get(query.getKey()), (Comparable) query.getValue());
            default:
                return null;
        }
    }
}
