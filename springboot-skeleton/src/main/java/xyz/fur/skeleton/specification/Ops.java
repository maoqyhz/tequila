package xyz.fur.skeleton.specification;

/**
 * 定义比较符
 * filed = value
 * filed > value
 * field >= value
 * field < value
 * field <= value
 * field != value
 * field like value
 * field not like value
 * field > obj
 * field >= obj
 * field < obj
 * field <= obj
 *
 * @author Fururur
 * @create 2019-07-19-13:57
 */
public enum Ops {
    eq, gt, ge, lt, le, notEqual, like, notLike, greaterThan, greaterThanOrEqualTo, lessThan, lessThanOrEqualTo
}