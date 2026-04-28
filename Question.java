
public class Question {
    String prompt;
    String[] options;
    int correctIndex;
    String explanation;
    String hint;
    String imagePath;

    public Question(String prompt, String[] options, int correctIndex, String explanation, String hint, String imagePath) {
        this.prompt = prompt;
        this.options = options;
        this.correctIndex = correctIndex;
        this.explanation = explanation;
        this.hint = hint;
        this.imagePath = imagePath;
    }
}
