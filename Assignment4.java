import java.util.*;

public class Assignment4 {
    public static void main(String[] args) throws CloneNotSupportedException {
        Player player = new Player();
        Game game = new Game(player);
        game.play();
    }
}

class Game{
    private final Tile[] carpet;
    private final Random random;
    private final Player player;
    private final Scanner input;
    private final Calculator<Integer> calculatorInt;
    private final Calculator<String> calculatorStr;

    Game(Player player){
        input = new Scanner(System.in);
        calculatorInt = new Calculator<>();
        calculatorStr = new Calculator<>();
        this.player = player;
        random = new Random();
        carpet = new Tile[20];
        Toy[] listOfToys = new Toy[20];
        String toysName[] = {"Weebles", "SuperBall", "Yo-Yo", "Sock Monkey", "Beach Ball", "Slinky", "Legos", "Frisbee", "Corn Popper", "Barbie", "Ken", "Hot Wheels", "Simon", "Care Bear", "Furby", "Koosh Ball", "Slap Bracelet", "Glo Warm", "Transformers", "Rubik's Cube"};

        for (int i = 0; i < 20; i++) {
            listOfToys[i] = new Toy(toysName[i]);
        }

        for (int i = 0; i < 20; i++) {

            carpet[i] = new Tile(listOfToys[random.nextInt(20)]);
        }
    }

    public void play() throws CloneNotSupportedException {
        System.out.print("hit enter to initialize the game");
        input.nextLine();
        System.out.println("Game is Ready");
        for (int i = 1; i < 6; i++) {
            System.out.print("hit enter to take a hop no: " + i);
            input.nextLine();
            int landPos = random.nextInt(21);
            if(landPos%2==0){
                Tile tile = getTile(landPos);
                if(tile!=null){
                    player.getToy(tile.giveSoftToy());
                }
            }else {
                String response;
                System.out.println("Question answer round. Integer or strings?");
                response = input.nextLine();
                while (!(response.equals("Integer") || response.equals("strings"))) {
                    System.out.println("please enter the correct input");
                    System.out.println("Question answer round. Integer or strings?");
                    response = input.nextLine();
                }
                boolean result;
                if (response.equals("Integer")) {
                    int a = random.nextInt();
                    int b = 0;
                    while(b==0){
                        b= random.nextInt();
                    }
                    System.out.println("Calculate the result of " + a + " divided by " + b + ", (You need to enter the Integer Division)");
                    int playerAnswer = -1;
                    while(playerAnswer==-1) {
                        playerAnswer = takeIntInput();
                        input.nextLine();
                    }
                    result = calculatorInt.calculate(a, b, playerAnswer);
                } else {
                    String a = randomString();
                    String b = randomString();
                    System.out.println("Calculate the concatenation of strings " + a + " and " + b);
                    String playerAnswer = input.nextLine();
                    result = calculatorStr.calculate(a, b, playerAnswer);
                }
                if(result){
                    player.getToy(getTile(landPos).giveSoftToy());
                }else{
                    System.out.println("you did not enter the right result");
                }
            }
        }
        System.out.println("Game Over\nSoft toys won by you are:");
        player.displayToys();
    }

    public String randomString(){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int randomNo = random.nextInt(letters.length());
            char ch = letters.charAt(randomNo);
            sb.append(ch);
        }
        return sb.toString();
    }

    public int takeIntInput(){
        int playerAnswer = -1;
        try{
            playerAnswer = input.nextInt();
        }catch (InputMismatchException e){
            System.out.println("please enter the data type of the question and answer same");
        }
        return playerAnswer;
    }


    public Tile getTile(int landPos){
        Tile tile = null;
        try{
            tile = carpet[landPos];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("You are too energetic and zoomed past all the tiles. Muddy Puddle Splash!");
        }
        return tile;
    }
}



class Calculator<T>{
    boolean result = false;

    public boolean calculate(T a, T b, T playerAnswer){
        if(a.getClass().getName().equals("java.lang.Integer")){
            int rightAnswer;
            rightAnswer = ((int)a/(int)b);
            if((int)playerAnswer==rightAnswer){
                result = true;
            }
        }else{
            String rightAnswer = a + (String)b;
            if(rightAnswer.equals(playerAnswer)){
                result = true;
            }
        }
        return result;
    }
}

class Player{
    private ArrayList<Toy> softToys;

    Player(){
        softToys = new ArrayList<>();
    }

    public void getToy(Toy softToy){
        softToys.add(softToy);
        System.out.println("You Won " + softToy);
    }

    public void displayToys(){
        for (int i = 0; i<softToys.size()-1; i++) {
            System.out.print(softToys.get(i) + ", ");
        }
        System.out.println(softToys.get(softToys.size()-1));
    }

}

class Tile{
    private final Toy softToy;

    Tile(Toy softToy){
        this.softToy = softToy;
    }

    public Toy giveSoftToy() throws CloneNotSupportedException {
        return (Toy)softToy.clone();
    }
}

class Toy implements Cloneable{
    private final String name;

    Toy(String name){
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    @Override
    public String toString(){
        return name;
    }
}