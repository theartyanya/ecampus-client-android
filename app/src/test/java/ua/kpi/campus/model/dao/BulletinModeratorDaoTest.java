package ua.kpi.ecampus.model.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import ua.kpi.ecampus.model.pojo.Bulletin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Administrator on 20.05.2016.
 */
public class BulletinModeratorDaoTest {

    private BulletinModeratorDao mDao;

    @Before
    public void setUp() {
        mDao = new BulletinModeratorDao();
    }

    @Test
    public void setData() {
        mDao.setData(stubCollection1());
        assertEquals(1, mDao.getData().size());

        // check empty list
        mDao.setData(new ArrayList<>());
        assertEquals(1, mDao.getData().size());

        // check addition of new elements
        mDao.setData(stubCollection2());
        assertEquals(3, mDao.getData().size());

        // check repetitive items
        mDao.setData(stubCollection2());
        assertEquals(3, mDao.getData().size());
    }

    @Test
    public void getData() {
        mDao.setData(stubCollection1());
        assertNotNull(mDao.getData());
    }

    @Test
    public void getNotExpired() {
        mDao.setData(stubCollection2());
        assertEquals(1, mDao.getNotExpired().size());
    }

    @Test
    public void getDeleted() {
        mDao.setData(stubCollection1());
        assertEquals(1, mDao.getDeleted().size());
    }

    private Collection<Bulletin> stubCollection1() {
        Bulletin b = new Bulletin("1", "a", "a", "2016");
        b.setDateStart("2016-05-01");
        b.setDateStop("2017-05-01");
        return new ArrayList<Bulletin>() {{
            add(b);
        }};
    }

    private Collection<Bulletin> stubCollection2() {
        Collection<Bulletin> c = new ArrayList<>();
        Bulletin b = new Bulletin("2", "b", "b", "2016");
        b.setDateStart("2016-05-01");
        b.setDateStop("2017-05-01");
        c.add(b);
        b = new Bulletin("3", "c", "c", "2016");
        b.setDateStart("2017-05-01");
        b.setDateStop("2018-05-01");
        c.add(b);
        return c;
    }
}
