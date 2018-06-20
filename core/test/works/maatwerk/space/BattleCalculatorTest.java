package works.maatwerk.space;

import com.badlogic.gdx.math.Vector2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BattleCalculatorTest {

    private BattleCalculator battleCalculator;

    @Before
    public void setUp()  {
        battleCalculator = new BattleCalculator();
    }

    @Test
    public void BattleTest() {
        Ship ship = new Ship("a", 20, 100, 10, Vector2.Zero, new User("test"));
        Ship ship1 = new Ship("a", 100, 100, 100, Vector2.Zero, new User("test"));

        Assert.assertEquals(battleCalculator.calculateDamage(ship, ship1), 15.471814155578613, 10);

        ship1.setShield(250);

        Assert.assertEquals(battleCalculator.calculateDamage(ship, ship1), 0.7659313678741455, 10);

        ship1.setShield(0);
        ship1.setArmor(1000);
        Assert.assertEquals(battleCalculator.calculateDamage(ship, ship1), 0.0, 1);

        ship1.setArmor(150);
        Assert.assertEquals(battleCalculator.calculateDamage(ship, ship1), 38.0625, 1);

    }

    @Test
    public void DamageTest() {
        Ship ship1 = new Ship("a", 100, 100, 100, Vector2.Zero, new User("test"));

        battleCalculator.doDamage(20, ship1);

        Assert.assertEquals(ship1.getHull(), 100);

        battleCalculator.doDamage(50, ship1);

        Assert.assertEquals(ship1.getHull(), 100);

        battleCalculator.doDamage(50, ship1);

        Assert.assertEquals(ship1.getHull(), 100);

        battleCalculator.doDamage(50, ship1);

        Assert.assertEquals(ship1.getHull(), 50);
    }
}