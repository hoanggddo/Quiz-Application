import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class MyFrame extends JFrame implements ActionListener {
    JRadioButton[] radiobutton = new JRadioButton[4];
    JLabel question, title;
    JButton btn, btn1, resetBtn;
    int current = 0; // Tracks the current question
    int score = 0; // Tracks the score

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setTitle("Vietnamese Quiz");
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        title = new JLabel("Vietnamese Quiz");
        title.setBounds(230, 50, 360, 50);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        question = new JLabel();
        question.setBounds(200, 100, 1000, 50);
        question.setFont(new Font("Arial", Font.BOLD, 18));

        btn = new JButton("Next");
        btn.setBounds(250, 400, 150, 50);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.addActionListener(this);

        btn1 = new JButton("Submit");
        btn1.setBounds(420, 400, 150, 50);
        btn1.setFont(new Font("Arial", Font.BOLD, 18));
        btn1.addActionListener(this);
        btn1.setVisible(false);

        resetBtn = new JButton("Reset");
        resetBtn.setBounds(590, 400, 150, 50);
        resetBtn.setFont(new Font("Arial", Font.BOLD, 18));
        resetBtn.addActionListener(this);

        ButtonGroup bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            radiobutton[i] = new JRadioButton();
            radiobutton[i].setBounds(300, 200 + (i * 50), 300, 50);
            this.add(radiobutton[i]);
            bg.add(radiobutton[i]);
        }

        this.add(title);
        this.add(question);
        this.add(btn);
        this.add(btn1);
        this.add(resetBtn);

        this.setVisible(true);
        set(); // Call set() initially to display the first question
        this.setVisible(true);

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            if (checkAnswer()) score++;
            current++;
            if (current == 16) { // Assuming 10 questions
                btn.setVisible(false);
                btn1.setVisible(true);
            } else {
                set(); // Update the question and answers
            }
        }

        if (e.getSource() == btn1) {
            if (current < 16 && checkAnswer()){
                score++;
            }
            JOptionPane.showMessageDialog(this, "You scored: " + score + "/16");
            System.exit(0);
        }

        if (e.getSource() == resetBtn) {
            resetQuiz();
        }
    }

    void resetQuiz() {
        current = 0;
        score = 0;
        btn.setVisible(true);
        btn1.setVisible(false);
        set(); // Reload the first question
    }
    

    void set() {
        // Reset selection to avoid retaining the previous answer
        for (JRadioButton rb : radiobutton) {
            rb.setSelected(false);
        }
		if (current == 0) {
			question.setText("Q1: What does the word nhà mean in Vietnamese?");
			radiobutton[0].setText("House");
			radiobutton[1].setText("School");
			radiobutton[2].setText("Food");
			radiobutton[3].setText("Book");
		}
		if (current == 1) {
			question.setText("Q2: The Vietnamese word trường học refers to");
			radiobutton[0].setText("Food");
			radiobutton[1].setText("House");
			radiobutton[2].setText("School");
			radiobutton[3].setText("Streams");
		}
		if (current == 2) {
			question.setText("Q3: What is the Vietnamese word for friend?");
			radiobutton[0].setText("Bạn");
			radiobutton[1].setText("Mẹ");
			radiobutton[2].setText("Cha");
			radiobutton[3].setText("Con");
		}
		if (current == 3) {
			question.setText("Q4: What does phở mean?");
			radiobutton[0].setText("Spring roll");
			radiobutton[1].setText("Noodle Soup");
			radiobutton[2].setText("Sandwich");
			radiobutton[3].setText("Cake");
		}
		if (current == 4) {
			question.setText("Q5: If you want to order coffee in Vietnam, you ask for");
			radiobutton[0].setText("Trà");
			radiobutton[1].setText("Sữa");
			radiobutton[2].setText("Cà phê");
			radiobutton[3].setText("Nước");
		}
		if (current == 5) {
			question.setText("Q6: The Vietnamese word bánh mì refers to:");
			radiobutton[0].setText("Apple");
			radiobutton[1].setText("Noodles");
			radiobutton[2].setText("Sandwich");
			radiobutton[3].setText("Soup");
		}
		if (current == 6) {
			question.setText("Q7: What does một mean?");
			radiobutton[0].setText("One");
			radiobutton[1].setText("Two");
			radiobutton[2].setText("Three");
			radiobutton[3].setText("Four");
		}
		if (current == 7) {
			question.setText("Q8:  The word đỏ in Vietnamese refers to which color?");
			radiobutton[0].setText("Yellow");
			radiobutton[1].setText("Blue");
			radiobutton[2].setText("Red");
			radiobutton[3].setText("Green");
		}
		if (current == 8) {
			question.setText("Q9: What is the Vietnamese word for ten?");
			radiobutton[0].setText("Sáu");
			radiobutton[1].setText("Bảy");
			radiobutton[2].setText("Mười");
			radiobutton[3].setText("Chín");
		}
		if (current == 9) {
			question.setText("Q10: The phrase tạm biệt means:");
			radiobutton[0].setText("Thank you");
			radiobutton[1].setText("Sorry");
			radiobutton[2].setText("Hello");
			radiobutton[3].setText("Goodbye");
		}
        if (current == 10) {
			question.setText("Q11: How do you say please in Vietnamese?");
			radiobutton[0].setText("Xin lỗi");
			radiobutton[1].setText("Làm ơn");
			radiobutton[2].setText("Không có gì");
			radiobutton[3].setText("Được rồi");
		}
        if (current == 11) {
			question.setText("Q12: The phrase cảm ơn translates to:");
			radiobutton[0].setText("No Thanks");
			radiobutton[1].setText("Why");
			radiobutton[2].setText("Yes Please");
			radiobutton[3].setText("Thank You");
		}
        if (current == 12) {
			question.setText("Q13: What does the Vietnamese word đẹp mean?");
			radiobutton[0].setText("Big");
			radiobutton[1].setText("Beautiful");
			radiobutton[2].setText("Fantastic");
			radiobutton[3].setText("Fast");
		}
        if (current == 13) {
			question.setText("Q14: What is the Vietnamese word for water?");
			radiobutton[0].setText("Cơm");
			radiobutton[1].setText("Nước");
			radiobutton[2].setText("Trà");
			radiobutton[3].setText("Sữa");
		}
        if (current == 14) {
			question.setText("Q15: What does the word chó mean in Vietnamese?");
			radiobutton[0].setText("Bird");
			radiobutton[1].setText("Cat");
			radiobutton[2].setText("Dog");
			radiobutton[3].setText("Fish");
		}
        if (current == 15) {
			question.setText("Q16: The word buổi sáng refers to what time of day?");
			radiobutton[0].setText("Midnight");
			radiobutton[1].setText("Night");
			radiobutton[2].setText("Afternoon");
			radiobutton[3].setText("Morning");
		}
    }

    boolean checkAnswer() {
        int[] answers = {0, 2, 0, 1, 2, 2, 0, 2, 2, 3, 1, 3, 1, 1, 2, 3}; // Correct answer indices
        return radiobutton[answers[current]].isSelected();
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
