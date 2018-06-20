package works.maatwerk.space;

import works.maatwerk.space.models.Ship;

class BattleCalculator {

    public float calculateDamage(Ship attackingShip, Ship defendingShip) {
        Float attackDamage = 100f; //todo:: base attack damage -- set to skill + weapon later.

        Float modifiedAttackDamage = (attackDamage / 255) * defendingShip.getHull(); //bigger ships = bigger suface of attack
        if (defendingShip.getArmor() / 4 > modifiedAttackDamage && defendingShip.getShield() == 0)
            return 0f; //nullify attack -- you cant kill a bear with a toothpick

        modifiedAttackDamage *=  Math.min(1f-((1f / 256f) * (float)(defendingShip.getShield() + 1)), 1.0f);


        if (defendingShip.getShield() == 0)
        modifiedAttackDamage -= Math.min(defendingShip.getArmor() / defendingShip.getHull(), modifiedAttackDamage * 0.9f);
        return modifiedAttackDamage;
        }

    public void doDamage(float v, Ship ship1) {
        if (ship1.getShield() != 0) {
            ship1.setShield((int) Math.max(ship1.getShield() - v, 0));
        } else {
            ship1.setHull((int)(ship1.getHull() - v));
            ship1.setArmor((int)(ship1.getArmor() - (v /2)));
        }
    }
}
