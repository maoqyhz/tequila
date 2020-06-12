package com.example.seed.controller;

import com.example.seed.common.repo.hz.IVolumeRepository;
import com.example.seed.common.repo.sh.IVolumeSHRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ApiController Tester.
 *
 * @author Fururur
 * @since <pre>12/12/2019</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiControllerTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Autowired
    private IVolumeRepository volumeRepository;

    @Autowired
    private IVolumeSHRepository volumeSHRepository;

    /**
     * Method: query()
     */
    @Test
    public void testQuery() throws Exception {
        System.out.println(volumeRepository.count());
        System.out.println(volumeSHRepository.count());
    }
}
