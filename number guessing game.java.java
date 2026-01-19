import java.util.Random;
import java.util.Scanner;
public class Main{
    public static void main(String[] args){
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int guess;
        int attempts = 0;
        int min = 1;
        int max = 100;
        int randomNumber = random.nextInt(min,max);
        System.out.println("******************");
        System.out.println("number guessing game");
        System.out.println("guess a number between 1-100:");
        do{
            guess = scanner.nextInt();
            attempts++;

            if(guess < randomNumber){
                System.out.println("Too low try again");
            }
            else if(guess > randomNumber){
                System.out.println("Too high try again");
            }
            else{
                System.out.println("you have won! randomNumber is:" + randomNumber);

                System.out.println( "# attempts :"  +  attempts);
            }

        }while(guess != randomNumber);



        scanner.close();
        
    }
}
