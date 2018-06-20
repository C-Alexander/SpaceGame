package works.maatwerk.space;

import com.badlogic.gdx.math.Vector2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import works.maatwerk.space.models.Ship;
import works.maatwerk.space.models.User;

public class BattleCalculatorTest {

    private BattleCalculator battleCalculator;

    @Before
    public void setUp()  {
        battleCalculator = new BattleCalculator();
    }

    @Test
    public void BattleShieldTest() {
        Ship ship = new Ship("a", 20, 100, 10, Vector2.Zero, new User("test"));
        Ship ship1 = new Ship("a", 100, 100, 100, Vector2.Zero, new User("test"));

        float originalDamage = battleCalculator.calculateDamage(ship, ship1);
        Assert.assertEquals(battleCalculator.calculateDamage(ship, ship1), originalDamage, 10);

        ship1.setShield(250);

        Assert.assertEquals(battleCalculator.calculateDamage(ship, ship1) < originalDamage, true);

        ship1.setShield(0);
        Assert.assertEquals(battleCalculator.calculateDamage(ship, ship1) > originalDamage, true);


    }

    @Test
    public void BattleArmorTest() {
        Ship ship = new Ship("a", 20, 0, 10, Vector2.Zero, new User("test"));
        Ship ship1 = new Ship("a", 100, 0, 50, Vector2.Zero, new User("test"));

        Float originalDamage = battleCalculator.calculateDamage(ship, ship1);



        ship1.setArmor(1000);
        Assert.assertTrue(battleCalculator.calculateDamage(ship, ship1) < originalDamage);

        ship1.setArmor(150);
        Assert.assertTrue(battleCalculator.calculateDamage(ship, ship1) < originalDamage);

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