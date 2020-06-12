package com.example.seed.common.repo.hz;

import com.example.seed.common.entity.Volume;
import com.example.seed.common.repo.base.IBaseRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;

/**
 * 数据源repository配置
 *
 * @author Fururur
 * @date 2020-03-13-15:19
 */
@Primary
@Qualifier("volumeHZRepository")
public interface IVolumeRepository extends IBaseRepository<Volume, Integer> {
    Volume findByUserIdAndIp(Integer userId, String ip);
}
