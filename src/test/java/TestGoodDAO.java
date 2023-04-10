import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sql.Goods;
import sql.GoodsDAO;

import static org.junit.Assert.*;

public class TestGoodDAO {

    private GoodsDAO goodsDAO;

    @Before
    public void setUp() {
        goodsDAO = new GoodsDAO();
    }

    @After
    public void tearDown() {
        goodsDAO = null;
    }

    @Test
    public void testInsert() {
        Goods goods = new Goods();
        goods.setName("测试商品");
        goods.setId(2);
        goods.setPrice(String.valueOf(100));
        goods.setQuantity(String.valueOf(10));
        Goods insertedGoods = goodsDAO.insert(goods);
        assertNotNull(insertedGoods);
        assertEquals(goods.getName(), insertedGoods.getName());
        assertEquals(goods.getPrice(), insertedGoods.getPrice());
        assertEquals(goods.getQuantity(), insertedGoods.getQuantity());
        goodsDAO.delete(insertedGoods.getId());
    }

    @Test
    public void testAlter() {
        Goods goods = new Goods();
        goods.setName("test");
        goods.setId(2);
        goods.setPrice(String.valueOf(10));
        goods.setQuantity(String.valueOf(100));
        Goods insertedGoods = goodsDAO.insert(goods);
        assertNotNull(insertedGoods);
        assertEquals(goods.getName(), insertedGoods.getName());
        assertEquals(goods.getPrice(), insertedGoods.getPrice());
        assertEquals(goods.getQuantity(), insertedGoods.getQuantity());
        Goods alteredGoods = new Goods();
        alteredGoods.setId(insertedGoods.getId());
        alteredGoods.setName("updatedGoods");
        alteredGoods.setPrice(String.valueOf(20));
        alteredGoods.setQuantity(String.valueOf(200));
        goodsDAO.alter(alteredGoods);
        Goods updatedGoods = goodsDAO.select(alteredGoods.getId());
        assertNotNull(updatedGoods);
        goodsDAO.delete(alteredGoods.getId());
    }

    @Test
    public void testDelete() {
        Goods goods = new Goods();
        goods.setId(2);
        goods.setName("test");
        goods.setPrice(String.valueOf(10));
        goods.setQuantity(String.valueOf(100));
        Goods insertedGoods = goodsDAO.insert(goods);
        assertNotNull(insertedGoods);
        assertEquals(goods.getName(), insertedGoods.getName());
        assertEquals(goods.getPrice(), insertedGoods.getPrice());
        assertEquals(goods.getQuantity(), insertedGoods.getQuantity());
        goodsDAO.delete(insertedGoods.getId());
    }
}
