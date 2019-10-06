import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class Poker {
    // Rate
    static int twoPair = 1;
    static int threeCard = 1;
    static int strait = 2;
    static int flash = 4;
    static int fullHouse = 5;
    static int fourCard = 10;
    static int straitFlash = 20;
    static int fiveCard = 50;
    static int loyalStraitFlash = 100;

    int[] drawCards = new int[5];

    int coin = 100;
    int bet = 0;

    public static void main(String[] args) {
        
    }

    public void startGame(){
        Scanner stdIn = new Scanner(System.in);

        System.out.println("あなたは今" + coin + "枚のコインを持っています。");
        System.out.println("何枚かけますか？");
        bet = stdIn.nextInt();
        stdIn.close();
        if(0 >= bet){
            System.out.println("1枚以上掛けてください");
            startGame();
        }
        else if(bet instanceof Integer){
            System.out.println("正しい値を入力してください。");
            startGame();
        }
        else {
            dealCards();
        }
    }

    public static String convertDealCardToShow(int dealCards){
        if(dealCards < 13)
            return "♠︎" + dealCards;
        else if(dealCards < 26)
            return "♦︎" + dealCards % 13;
        else if(dealCards < 39)
            return "❤︎" + dealCards % 13;
        else if(dealCards < 52)
            return "♣︎" + dealCards % 13;
        else
            return "JOKER";
    }

    public void dealCards(){
        boolean isDealed = true;
        Random rand = new Random();
    int randomCard;
        while(isDealed)
            randomCard = rand.nextInt(54);
            if(Arrays.asList(drawCards).contains(randomCard)){
                // do nothing
            }else{
                drawCards.push(randomCard);
            }

            if(drawCards.length == 5){
                isDealed = false;
            }

        System.out.println("配られたカードは");

        
        changeCards();

    }
    
    public static void changeCards(){
        System.out.println("どのカードを交換しますか");
        Scanner stdIn = new Scanner(System.in);
        // カードを交換する
    }
    
    public static PokerHands judgePokerHands(){
        
    }

}