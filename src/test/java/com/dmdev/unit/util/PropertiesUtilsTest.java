package com.dmdev.unit.util;

import com.dmdev.util.PropertiesUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class PropertiesUtilsTest {

    private static final String URL_KEY = "db.url" ;
    private static final String USER_KEY = "db.user" ;
    private static final String DRIVER_KEY = "db.driver" ;


    @BeforeAll
    static void setUpBeforeClass() {
        new Properties();
    }

    @Test
    void shouldReturnCorrectProperties() {
        String actualUrl = PropertiesUtil.get(URL_KEY);
        String actualUser = PropertiesUtil.get(USER_KEY);
        String actualDriver = PropertiesUtil.get(DRIVER_KEY);

        String expectedUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1" ;
        String expectedUser = "sa" ;
        String expectedDriver = "org.h2.Driver" ;

        assertThat(actualUrl).isEqualTo(expectedUrl);
        assertThat(actualUser).isEqualTo(expectedUser);
        assertThat(actualDriver).isEqualTo(expectedDriver);
    }
}