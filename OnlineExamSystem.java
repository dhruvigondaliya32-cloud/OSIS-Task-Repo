import java.util.*;

public class OnlineExamSystem{
    private String username;
    private String password;
    private boolean isLoggedIn;
    private int timeRemaining;
    private int questionCount;
    private int[] userAnswers;
    private int[] correcetAnswers;

    public OnlineExamSystem(String username, String password){
        this.username = username;
        this.password = password;
        System.out.println("sucessfully you are registered!  :");
        this.isLoggedIn = false;
        this.timeRemaining = 10;
        this.questionCount = 10;
        this.userAnswers = new int[questionCount];
        this.correcetAnswers = new int[questionCount];
        for(int i =0; i< questionCount; i++){
            correcetAnswers[i]=(int)Math.round(Math.random());
        }
    }
    public void login(){
        System.out.println("log in to give the exam");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Username:");
        String inputUsername = scanner.nextLine();
        System.out.println("password:");
        String inputPassword = scanner.nextLine();
        if (inputUsername.equals(username) && inputPassword.equals(password)){
            isLoggedIn = true;
            System.out.println("login successful Best of Luck dear");
        }
        else{
            System.out.println("invalid username or password");
        }
    }
    public void logout(){
        isLoggedIn = false;
        System.out.println("log out successful.");

    }
    public void startExam(){
        if (!isLoggedIn){
            System.out.println("please login first");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("you have"+ timeRemaining + "minutes to complete the exam.");
        for(int i =0; i< questionCount; i++){
            System.out.println("question"+(i+1)+":");
            System.out.println("1. option 1");
            System.out.println("2. option 2");
            System.out.println("your answer (1 or 2): ");
            int answer = scanner.nextInt();
            userAnswers[i]=answer;

        }
        System.out.println("would you like to submit?  \n 1: yes \n 2:no");
        int n = scanner.nextInt();
        if( n ==1 ){
            submitExam();
        }
        else{
            try{
                Thread.sleep(timeRemaining * 10 * 1000);
            } catch (InterruptedException e) {
               e.printStackTrace();
               submitExam();
            }
        }

    }
    public void submitExam(){
        if(!isLoggedIn){
            System.out.println("please login first");
            return;
        }
        int score= 0;
        for (int i =0; i< questionCount; i++){
            if(userAnswers[i]== correcetAnswers[i]){
                score++;

            }

        }
        System.out.println("your score is  " + score+"out of"+ questionCount+".");
        System.out.println("all the best:");
        logout();
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter your username or password:");
        String username = scanner.nextLine();
        String password = scanner.nextLine();
        OnlineExamSystem examSystem = new OnlineExamSystem(username,password);
        examSystem.login();
        examSystem.startExam();
    }
}
