import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class Poker {
    // Rate
    final static int twoPair = 1;
    final static int threeCard = 1;
    final static int strait = 2;
    final static int flash = 4;
    final static int fullHouse = 5;
    final static int fourCard = 10;
    final static int straitFlash = 20;
    final static int fiveCard = 50;
    final static int loyalStraitFlash = 100;

    static int[] drawCards = new int[5];
    static String[] drawCardsForPrint = new String[5];

    static int coin = 100;
    static int bet = 0;

    static int howOnePair = 0;
    static boolean isThreeCard = false;
    static boolean isFourCard = false;
    static boolean isFiveCard = false;
    static boolean isStrait = false;
    static boolean isFlash = false;
    static boolean isLoyal = false;
    static boolean isFullHouse = false;

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame(){
        Scanner stdIn = new Scanner(System.in);

        System.out.println("あなたは今" + Poker.coin + "枚のコインを持っています。");
        System.out.println("何枚かけますか？");
        Poker.bet = stdIn.nextInt();
        // stdIn.close();
        // if(bet instanceof Integer){
        //     System.out.println("正しい値を入力してください。");
        //     startGame();
        // }
        if(0 >= Poker.bet){
            System.out.println("1枚以上掛けてください");
            startGame();
        }
        else {
            Poker.coin -= bet;
            dealCards();
        }
    }

    public static String convertDealCardToShow(int dealCards){
        if(dealCards < 13){
            if(dealCards == 12){
                return "♠︎" + (1);
            }
            return "♠︎" + (dealCards + 2);
        }
        else if(dealCards < 26){
            if(dealCards == 25){
                return "♦︎" + (1);
            }
            return "♦︎" + (dealCards % 13 + 2);
        }
        else if(dealCards < 39){
            if(dealCards == 38){
                return "❤︎" + (1);
            }
            return "❤︎" + (dealCards % 13 + 2);
        }
        else if(dealCards < 52){
            if(dealCards == 51){
                return "♣︎" + (1);
            }
            return "♣︎" + (dealCards % 13 + 2);
        }
        else{
            return "JOKER";
        }
    }

    public static void dealCards(){
        boolean isDealed = true;
        Random rand = new Random();
        Scanner stdIn = new Scanner(System.in);
        int randomCard = 0;
        int order = 0;
        while(isDealed){
            randomCard = rand.nextInt(51);
            if(Arrays.asList(Poker.drawCards).contains(randomCard)){
                // do nothing
            }else{
                Poker.drawCards[order] = randomCard;
                Poker.drawCardsForPrint[order] = Poker.convertDealCardToShow(randomCard);
                order += 1;
            }

            if(Poker.drawCardsForPrint[4] != null){
                isDealed = false;
            }
        }
        System.out.println("配られたカードは");

        for(int index = 0;index < Poker.drawCardsForPrint.length;index++){
            System.out.println(Poker.drawCardsForPrint[index]);
        }
        
        changeCards();

        System.out.println("あなたのカードは");

        for(int index = 0;index < Poker.drawCardsForPrint.length;index++){
            System.out.println(Poker.drawCardsForPrint[index]);
        }

        String hand = judgePokerHands(0);
        System.out.println(hand);
        if(Poker.coin >0){
            initializeHandFlag();
            startGame();
        }else{
            stdIn.close();
            System.out.println("コインが0枚になったので終了です。");
        }
    }
    
    public static void changeCards(){
        Scanner stdIn = new Scanner(System.in);
        System.out.println("どのカードを交換しますか");
        // カードを交換する
        int changeOrder;
        Random rand = new Random();
        int randomCard = 0;
        String[] changeTargets = stdIn.nextLine().split(" ");
        for(int order = 0;order < changeTargets.length;order ++){
            changeOrder = Integer.parseInt(changeTargets[order]);
            boolean isDealed = true;
            while(isDealed){
                randomCard = rand.nextInt(51);
                if(Arrays.asList(Poker.drawCards).contains(randomCard)){
                    // do nothing
                }else{
                    Poker.drawCards[changeOrder - 1] = randomCard;
                    Poker.drawCardsForPrint[changeOrder - 1] = Poker.convertDealCardToShow(randomCard);
                    isDealed = false;
                }
            }
                
        }
        

    }
    
    public static String judgePokerHands(int Joker){
        int[][] cardTableForJudge = new int[5][14];
        if(Joker == 0){
            for(int order = 0;order < Poker.drawCards.length;order++){
                int row = Poker.drawCards[order] / 13;
                int column = Poker.drawCards[order] % 13;
                cardTableForJudge[row][column] = 1;
            }
            // Set Sum of rows
            for(int order = 0;order < 4;order++){
                int sum = getSum(cardTableForJudge[order]);
                cardTableForJudge[order][13] = sum;
                if(sum == 5){
                    Poker.isFlash = true;
                }
                int isStraitOrLoyal = judgeIsStrait(cardTableForJudge[order]);
                if(isStraitOrLoyal == 1){
                    Poker.isStrait = true;
                }else if(isStraitOrLoyal == 2){
                    Poker.isStrait = true;
                    Poker.isLoyal = true;
                }else{
                    // do nothing
                }
            }

            // Set Sum of columns
            for(int order = 0;order < 13;order++) {
                // 直す
                int sum = getSum(getRowArray(cardTableForJudge,order));
                cardTableForJudge[4][order] = sum;
                if(sum == 3){
                    Poker.isThreeCard = true;
                }
                if(sum == 4){
                    Poker.isFourCard = true;
                }
                if(sum == 2){
                    Poker.howOnePair += 1;
                }
            }

        }

        // Show Data for tests
        for(int i = 0;i < 5;i++){
            for(int j = 0;j < 14;j++){
                System.out.print(cardTableForJudge[i][j]);
            }
            System.out.print("\n");
        }

        if(Poker.isFourCard){
            Poker.coin += Poker.bet * Poker.fourCard;
            return "FOUR CARD!!";
        }else if(Poker.howOnePair == 2){
            Poker.coin += Poker.bet * Poker.twoPair;
            return "TWO PAIR!!";
        }else if(Poker.howOnePair ==1 & Poker.isThreeCard){
            Poker.coin += Poker.bet * Poker.fullHouse;
            return "FULL HOUSE!!";
        }else if(Poker.isThreeCard){
            Poker.coin += Poker.bet * Poker.threeCard;
            return "THREE CARD!!";
        }else if(Poker.isStrait){
            if(Poker.isFlash){
                if(Poker.isLoyal){
                    Poker.coin += Poker.bet * Poker.loyalStraitFlash;
                    return "LOYAL STRAIT FLASH!!";
                }else{
                    Poker.coin += Poker.bet * Poker.straitFlash;
                    return "STRAIT FLASH!!";
                }
            }
            Poker.coin += Poker.bet * Poker.strait;
            return "STRAIT!!";
        }else if(Poker.isFlash){
            Poker.coin += Poker.bet * Poker.flash;
            return "FLASH!!";
        }else{
            return "NO HAND!!";
        }
    }

    public static int getSum(int[] Target){
        int sum = 0;
        for(int index = 0;index < Target.length;index++){
            sum += Target[index];
        }
        return sum;
    }

    public static int judgeIsStrait(int[] Target){
        boolean hasPossibilyty = true;
        boolean isStart = false;
        int whenStart = -1;
        for(int order = 0;order < 13;order++){
            if(!isStart){
                if(Target[order] == 1){
                    isStart = true;
                    whenStart = order;
                }
            }else{
                if(Target[order] != 1){
                    hasPossibilyty = false;
                    return 0;
                }
            }
        }
        if(whenStart == 9){
            return 2;
        }else if(whenStart == -1){
            return 0;
        }
        return 1;
    }

    public static int[] getRowArray(int[][] Target,int targetRow){
        int[] rowArray = new int[Target.length];
        for(int row = 0;row < Target.length;row++) {
            for(int column = 0;column < Target[0].length;column++){
                if(column == targetRow){
                    rowArray[row] = Target[row][column];
                }
            }
        }
        return rowArray;
    }

    public static void initializeHandFlag(){
        Poker.howOnePair = 0;
        Poker.isThreeCard = false;
        Poker.isFourCard = false;
        Poker.isFiveCard = false;
        Poker.isStrait = false;
        Poker.isFlash = false;
        Poker.isLoyal = false;
        Poker.isFullHouse = false;
    }
}