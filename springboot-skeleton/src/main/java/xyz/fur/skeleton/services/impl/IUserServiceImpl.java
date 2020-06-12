package xyz.fur.skeleton.services.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.fur.skeleton.entity.User;
import xyz.fur.skeleton.repo.jpa.IUserRepository;
import xyz.fur.skeleton.services.IUserService;
import xyz.fur.skeleton.services.base.IBaseServiceImpl;
import xyz.fur.skeleton.specification.Ops;
import xyz.fur.skeleton.specification.SimpleSpecificationBuilder;
import xyz.fur.skeleton.utils.CaffeineCacheManager;

import java.util.concurrent.TimeUnit;


/**
 * @author Fururur
 * @create 2019-07-18-17:13
 */
@Service
public class IUserServiceImpl extends IBaseServiceImpl<User, Long> implements IUserService {
    private final IUserRepository userRepository;

    private final CaffeineCacheManager caffeineCacheUtils;

    public IUserServiceImpl(IUserRepository userRepository, CaffeineCacheManager caffeineCacheUtils) {
        this.userRepository = userRepository;
        this.caffeineCacheUtils = caffeineCacheUtils;
        initCache();
    }

    /**
     * 缓存创建
     *
     */
    private void initCache() {
        caffeineCacheUtils.createCache("userCount", Caffeine.newBuilder()
                .maximumSize(1024)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build());
    }

    @Override
    public Page<User> findUserByConditions(int age, int pageNo, int pageSize) {
        Pageable pager = PageRequest.of(pageNo - 1, pageSize);
        SimpleSpecificationBuilder<User> builder = new SimpleSpecificationBuilder<>();
        Page<User> pages = userRepository.findAll(builder
                .add("age", Ops.gt, 40)
                .generateSpecification(), pager);
        return pages;
    }

    /**
     * 直接使用caffeine cache API创建缓存
     * @return
     */
    @Override
    public Long countBy() {
        Cache<String,Long> cache = caffeineCacheUtils.getCache("userCount");
        Long c = cache.getIfPresent("user_total");
        if (c == null) {
            c = userRepository.count();
            cache.put("user_total", c);
        }
        return c;
    }
}
