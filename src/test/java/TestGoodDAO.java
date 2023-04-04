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
        goods.setPrice(100);
        goods.setQuantity(10);
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
        goods.setPrice(String.valueOf(10));
        goods.setQuantity(100);
        Goods insertedGoods = goodsDAO.insert(goods);
        assertNotNull(insertedGoods);
        assertEquals(goods.getName(), insertedGoods.getName());
        assertEquals(goods.getPrice(), insertedGoods.getPrice());
        assertEquals(goods.getQuantity(), insertedGoods.getQuantity());
        Goods alteredGoods = new Goods();
        alteredGoods.setId(insertedGoods.getId());
        alteredGoods.setName("updatedGoods");
        alteredGoods.setPrice(20);
        alteredGoods.setQuantity(200);
        goodsDAO.alter(alteredGoods);
        Goods updatedGoods = goodsDAO.select(alteredGoods.getId());
        assertNotNull(updatedGoods);
        assertEquals(alteredGoods.getName(), updatedGoods.getName());
        assertEquals(alteredGoods.getPrice(), updatedGoods.getPrice());
        assertEquals(alteredGoods.getQuantity(), updatedGoods.getQuantity());
        goodsDAO.delete(alteredGoods.getId());
    }

    @Test
    public void testDelete() {
        Goods goods = new Goods();
        goods.setName("test");
        goods.setPrice(10);
        goods.setQuantity(100);
        Goods insertedGoods = goodsDAO.insert(goods);
        assertNotNull(insertedGoods);
        assertEquals(goods.getName(), insertedGoods.getName());
        assertEquals(goods.getPrice(), insertedGoods.getPrice());
        assertEquals(goods.getQuantity(), insertedGoods.getQuantity());
        goodsDAO.delete(insertedGoods.getId());
        assertNull(goodsDAO.select(insertedGoods.getId()));
    }
}
