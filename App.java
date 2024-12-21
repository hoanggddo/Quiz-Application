import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String q1 = "What does the word nhà mean in Vietnamese?\n" 
                        + "(a) House\n(b) School\n(c) Food\n(d) Book";
        String q2 = "The Vietnamese word trường học refers to\n"
                        + "(a) Food\n(b) House\n(c) School\n(d) Book";
        String q3 = "What is the Vietnamese word for friend?\n" 
                        + "(a) Bạn\n(b) Mẹ\n(c) Cha\n(d) Con";
        String q4 = "What does phở mean?\n"
                        + "(a) Spring roll\n(b) Noodle soup\n(c) Fried rice\n(d) Sweet dessert";
        String q5 = "If you want to order coffee in Vietnam, you ask for:\n" 
                        + "(a) Trà\n(b) Sữa\n(c) Cà phê\n(d) Nước";
        String q6 = "The Vietnamese word bánh mì refers to:\n"
                        + "(a) Rice\n(b) Noodles\n(c)Sandwich\n(d) Soup";
        String q7 = "What does một mean?\n" 
                        + "(a) One\n(b) Two\n(c) Three\n(d) Four";
        String q8 = "The word đỏ in Vietnamese refers to which color?\n"
                        + "(a) Blue\n(b) Yellow\n(c) Red\n(d) Green";
        String q9 = "What is the Vietnamese word for ten?\n" 
                        + "(a) Sáu\n(b) Bảy\n(c) Mười\n(d) Chín";
        String q10 = "The phrase tạm biệt means:\n"
                        + "(a) Thank you\n(b) Goodbye\n(c) Hello\n(d) Sorry";
        String q11 = "How do you say please in Vietnamese?\n" 
                        + "(a) Xin lỗi\n(b) Làm ơn\n(c) Không có gì\n(d) Được rồi";
        String q12 = "The phrase cảm ơn translates to:\n"
                        + "(a) Sorry\n(b) Please\n(c) Thank you\n(d) You are welcome";
        String q13 = "What does the Vietnamese word đẹp mean?\n" 
                        + "(a) Big\n(b) Beautiful\n(c) Fast\n(d) Small";
        String q14 = "What is the Vietnamese word for water?\n"
                        + "(a) Cơm\n(b) Nước\n(c) Trà\n(d) Sữa";
        String q15 = "What does the word chó mean in Vietnamese?\n" 
                        + "(a) Cat\n(b) Bird\n(c) Dog\n(d) Fish";
        String q16 = "The word buổi sáng refers to what time of day?\n"
                        + "(a) Morning\n(b) Afternoon\n(c) Evening\n(d) Night";
        Question[] questions = {
            new Question(q1, "a"),
            new Question(q2, "c"),
            new Question(q3, "a"),
            new Question(q4, "b"),
            new Question(q5, "c"),
            new Question(q6, "c"),
            new Question(q7, "a"),
            new Question(q8, "c"),
            new Question(q9, "c"),
            new Question(q10, "b"),
            new Question(q11, "b"),
            new Question(q12, "c"),
            new Question(q13, "b"),
            new Question(q14, "b"),
            new Question(q15, "c"),
            new Question(q16, "a")
        };
        takeTest(questions);
    }

    public static void takeTest(Question[] questions) {
        int score = 0;
        Scanner keyboardInput = new Scanner(System.in); // Scanner instantiated once outside the loop
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i].prompt);
            String answer = keyboardInput.nextLine();
            if (answer.equalsIgnoreCase(questions[i].answer)) { // Use equalsIgnoreCase for case-insensitive matching
                score++;
            }
        }
        System.out.println("You scored " + score + " out of " + questions.length);
        keyboardInput.close(); // Close Scanner to release resources
    }
}

