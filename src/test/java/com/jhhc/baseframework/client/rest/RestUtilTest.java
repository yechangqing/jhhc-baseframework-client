package com.jhhc.baseframework.client.rest;

import com.jhhc.baseframework.test.Base;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author yecq
 */
public class RestUtilTest extends Base {

    @Autowired
    private RestUtil rest;

    @Test
    public void test_init() {
        assertThat(rest, not(nullValue()));
    }
}
