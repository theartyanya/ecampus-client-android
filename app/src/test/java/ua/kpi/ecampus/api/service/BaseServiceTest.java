package ua.kpi.ecampus.api.service;

import org.junit.Before;

/**
 * Base class for tests of service classes
 * Created by Admin on 18.05.2016.
 */
public class BaseServiceTest {

    public JsonReader jsonReader;

    @Before
    public void setUp() throws Exception {
        jsonReader = new JsonReader();
    }

    public String getToken() {
        return "bearer token";
    }
}
