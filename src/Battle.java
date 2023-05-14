public class Battle {
    public void fight(PlayerCharacter hero, PlayerCharacter monster, FantasyWorld.FightCallback fightCallback) {

        Runnable runnable = () -> {
            int turn = 1;
            boolean isFightEnded = false;
            while (!isFightEnded) {
                System.out.println("----Move: " + turn + "----");
                if (turn++ % 2 != 0) {
                    isFightEnded = makeHit(monster, hero, fightCallback);
                } else {
                    isFightEnded = makeHit(hero, monster, fightCallback);
                }
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private Boolean makeHit(PlayerCharacter defender, PlayerCharacter attacker, FantasyWorld.FightCallback fightCallback) {
        int hit = attacker.attack();
        int defenderHealth = defender.getHealth() - hit;
        if (hit != 0) {
            System.out.println(String.format("%s Made hit for %d points!", attacker.getName(), hit));
            System.out.println(String.format("%s has %d hp...", defender.getName(), defenderHealth));
        } else {
            System.out.println(String.format("%s missed!", attacker.getName()));
        }
        if (defenderHealth <= 0 && defender instanceof Player) {
            System.out.println("Sorry, you perished in battle...");
            fightCallback.fightLost();
            return true;
        } else if (defenderHealth <= 0) {
            //Если здоровья больше нет и защищающийся – это монстр, то мы забираем от монстра его опыт и золото
            System.out.println(String.format("The enemy is defeated! You get %d xp and %d gold", defender.getXp(), defender.getGold()));
            attacker.setXp(attacker.getXp() + defender.getXp());
            attacker.setGold(attacker.getGold() + defender.getGold());
            fightCallback.fightWin();
            return true;
        } else {
            defender.setHealth(defenderHealth);
            return false;
        }
    }
}

