import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FantasyWorld {
    private static BufferedReader br;
    private static PlayerCharacter player = null;
    private static Battle battle = null;

    private static void printNavigation() {
        System.out.println("Where to go?");
        System.out.println("1. To merchant");
        System.out.println("2. To the dark forest");
        System.out.println("3. Exit");
    }


    private static void command(String string) throws IOException {

        if (player == null) {                                           // new player
            player = new Player(
                    string,
                    100,
                    20,
                    20,
                    0,
                    0
            );
            System.out.println(String.format("%s volunteered to save our world from dragons! May his armor be strong and his biceps round!", player.getName()));


            printNavigation();
        }

        switch (string) {
            case "1": {
                System.out.println("Merchant is out");
                command(br.readLine());
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3":
                System.exit(1);
                break;
            case "yes":
                command("2");
                break;
            case "no": {
                printNavigation();
                command(br.readLine());
            }
        }

        command(br.readLine());
    }

    private static void commitFight() {
        battle.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s won!You have %d of xp, %d gold and %d hp.", player.getName(), player.getXp(), player.getGold(), player.getHealth()));
                System.out.println("Would you like to continue battle, or return to the town? (yes/no)");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {

            }
        });
    }

    private static PlayerCharacter createMonster() {

        int random = (int) (Math.random() * 10);

        if (random % 2 == 0) return new Goblin(
                "Goblin",
                50,
                10,
                10,
                100,
                20
        );
        else return new Skeleton(
                "Skeleton",
                25,
                20,
                20,
                100,
                10
        );
    }


    public static void main(String[] args) {


        br = new BufferedReader(new InputStreamReader(System.in));

        battle = new Battle();

        System.out.println("Enter Player Name:");

        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public interface FightCallback {
        void fightWin();

        void fightLost();
    }
}