package ua.kpi.ecampus.model.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import ua.kpi.ecampus.model.pojo.Bulletin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Administrator on 19.05.2016.
 */
public class BulletinDaoTest {

    private BulletinDao mDao;

    @Before
    public void setUp() {
        mDao = new BulletinDao();
    }

    @Test
    public void setData() {
        mDao.setData(stubCollection1());
        assertEquals(1, mDao.getData().size());

        mDao.setData(new ArrayList<>());
        assertEquals(1, mDao.getData().size());

        mDao.setData(stubCollection2());
        assertEquals(2, mDao.getData().size());
    }

    @Test
    public void getData() {
        mDao.setData(stubCollection1());
        assertNotNull(mDao.getData());
    }

    private Collection<Bulletin> stubCollection1() {
        return new ArrayList<Bulletin>() {{
            add(new Bulletin("1", "a", "a", "2016"));
        }};
    }

    private Collection<Bulletin> stubCollection2() {
        return new ArrayList<Bulletin>() {{
            add(new Bulletin("1", "a", "a", "2016"));
            add(new Bulletin("2", "a", "a", "2017"));
        }};
    }
}
