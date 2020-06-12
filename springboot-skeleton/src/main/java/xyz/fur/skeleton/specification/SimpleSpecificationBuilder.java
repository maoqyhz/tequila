package xyz.fur.skeleton.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * SpecificationBuilder 封装类，构建 fluent api
 * @author Fururur
 * @create 2019-07-19-10:33
 */
public class SimpleSpecificationBuilder<T> {
    private List<QueryBean> querys;


    public SimpleSpecificationBuilder(String key, Ops operator, Object value) {
        QueryBean query = new QueryBean();
        query.setJoin(QueryBean.Join.and);
        query.setKey(key);
        query.setOperator(operator);
        query.setValue(value);
        querys = new ArrayList<>();
        querys.add(query);
    }

    public SimpleSpecificationBuilder() {
        querys = new ArrayList<>();
    }

    /**
     * 完成条件的添加
     *
     * @return
     */
    private SimpleSpecificationBuilder<T> add(String key, Ops operator, Object value, QueryBean.Join join) {
        QueryBean so = new QueryBean();
        so.setKey(key);
        so.setValue(value);
        so.setOperator(operator);
        so.setJoin(join);
        querys.add(so);
        return this;
    }

    /**
     * 添加or条件的重载
     *
     * @return this，方便后续的链式调用
     */
    public SimpleSpecificationBuilder<T> addOr(String key, Ops operator, Object value) {
        return this.add(key, operator, value, QueryBean.Join.or);
    }

    /**
     * 添加and的条件
     *
     * @return
     */
    public SimpleSpecificationBuilder<T> add(String key, Ops operator, Object value) {
        return this.add(key, operator, value, QueryBean.Join.and);

    }

    public Specification<T> generateSpecification() {
        return new SimpleSpecification<>(querys);
    }
}
