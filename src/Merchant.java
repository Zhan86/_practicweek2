public class Merchant extends PlayerCharacter implements Seller{
    public Merchant(String name, int health, int strength, int dexterity, int xp, int gold) {
        super(name, health, strength, dexterity, xp, gold);
    }
    @Override
    public String sell(Goods goods) {
        String result = "";
        if (goods == Goods.POTION) {
            result = "potion";
        }
        return result;
    }


}
