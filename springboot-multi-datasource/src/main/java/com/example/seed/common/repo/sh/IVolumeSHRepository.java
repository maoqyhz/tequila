package com.example.seed.common.repo.sh;

import com.example.seed.common.repo.hz.IVolumeRepository;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 数据源repository配置
 * <p>
 * 如果两个数据源是相同的，repository可以采用继承的形式
 *
 * @author Fururur
 * @date 2020-03-13-15:19
 */
@Qualifier("volumeSHRepository")
public interface IVolumeSHRepository extends IVolumeRepository {

}