package com.epam.esm.dao.impl;

import com.epam.esm.configuration.TestDaoConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Suite
@SelectClasses({GiftCertificateDaoImplTest.class, TagDaoImplTest.class})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoConfiguration.class})
@ActiveProfiles("test")
public class TestDaoSuite {
    @Autowired
    private static JdbcTemplate jdbcTemplate;

    @AfterAll
    static void dropTestDatabase(){
        jdbcTemplate.execute("DROP DATABASE gift_certificates_test");
    }
}
