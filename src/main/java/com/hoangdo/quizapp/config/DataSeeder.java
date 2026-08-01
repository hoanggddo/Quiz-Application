package com.hoangdo.quizapp.config;

import com.hoangdo.quizapp.model.Question;
import com.hoangdo.quizapp.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds the question bank on first run: 15 questions each for Vocabulary,
 * Grammar, and History & Culture, each with a hint.
 *
 * A CommandLineRunner bean is guaranteed by Spring's own lifecycle to run
 * only after the full application context (including Hibernate's schema
 * creation) has finished initializing, so there's no risk of this running
 * before the "questions" table actually has all its columns.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final QuestionRepository questionRepository;

    public DataSeeder(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) {
        if (questionRepository.count() > 0) {
            return; // already seeded from a previous run
        }

        seedVocabulary();
        seedGrammar();
        seedHistoryAndCulture();
    }

    private void seedVocabulary() {
        String c = "Vocabulary";
        questionRepository.save(new Question(c, "What does \"xin chào\" mean?",
                "Goodbye", "Hello", "Thank you", "Please", "B",
                "It's the standard greeting you'd use when meeting someone."));

        questionRepository.save(new Question(c, "What does \"cảm ơn\" mean?",
                "Sorry", "Please", "Thank you", "Yes", "C",
                "You'd say this after someone does something kind for you."));

        questionRepository.save(new Question(c, "What does \"tạm biệt\" mean?",
                "Hello", "Goodbye", "Excuse me", "Welcome", "B",
                "Say this when leaving or ending a conversation."));

        questionRepository.save(new Question(c, "What does \"xin lỗi\" mean?",
                "Sorry / Excuse me", "Thank you", "Please", "Goodbye", "A",
                "Use this when you've made a mistake or need to get past someone."));

        questionRepository.save(new Question(c, "What does \"làm ơn\" mean?",
                "Please", "Sorry", "Yes", "No", "A",
                "You'd add this word when politely asking someone for something."));

        questionRepository.save(new Question(c, "What does \"vâng\" mean?",
                "No", "Yes (polite)", "Maybe", "Never", "B",
                "It's a respectful way to agree with someone older or in authority."));

        questionRepository.save(new Question(c, "What does \"không\" mean?",
                "Yes", "Please", "No", "Thanks", "C",
                "This word also shows up at the end of yes/no questions in Vietnamese."));

        questionRepository.save(new Question(c, "What does \"bạn\" mean?",
                "You (friend/peer)", "I / me", "He / she", "We", "A",
                "This is one of the most common ways to address a peer your own age."));

        questionRepository.save(new Question(c, "What does \"tôi\" mean?",
                "You", "I / me", "They", "It", "B",
                "This is the neutral, most commonly used way to refer to yourself."));

        questionRepository.save(new Question(c, "What does \"gia đình\" mean?",
                "Friend", "School", "Family", "Country", "C",
                "Think of the people you grew up living with."));

        questionRepository.save(new Question(c, "What does \"nước\" mean?",
                "Water / country", "Fire", "Food", "House", "A",
                "This word has two common meanings depending on context, one is a drink."));

        questionRepository.save(new Question(c, "What does \"ăn\" mean?",
                "To sleep", "To eat", "To walk", "To speak", "B",
                "You do this at every meal."));

        questionRepository.save(new Question(c, "What does \"đi\" mean?",
                "To stay", "To eat", "To go", "To sit", "C",
                "This verb shows up in directions and travel phrases constantly."));

        questionRepository.save(new Question(c, "What does \"yêu\" mean?",
                "Hate", "Love", "Like", "Miss", "B",
                "A strong feeling, often used in romantic or family contexts."));

        questionRepository.save(new Question(c, "What does \"đẹp\" mean?",
                "Ugly", "Big", "Beautiful", "Small", "C",
                "You'd use this word to compliment how something or someone looks."));
    }

    private void seedGrammar() {
        String c = "Grammar";
        questionRepository.save(new Question(c, "Which word means \"and\" in Vietnamese?",
                "và", "nhưng", "hoặc", "vì", "A",
                "This short word joins two nouns or ideas together, like 'apples ___ oranges.'"));

        questionRepository.save(new Question(c, "Which word means \"or\" in Vietnamese?",
                "và", "hoặc", "thì", "mà", "B",
                "You'd use this word when offering a choice between two things."));

        questionRepository.save(new Question(c, "Which word means \"but\" in Vietnamese?",
                "nhưng", "vì", "nếu", "của", "A",
                "This connects two contrasting ideas, like 'I'm tired ___ happy.'"));

        questionRepository.save(new Question(c, "Which word means \"because\" in Vietnamese?",
                "nếu", "và", "vì", "cũng", "C",
                "This word introduces a reason for something."));

        questionRepository.save(new Question(c, "Which word means \"if\" in Vietnamese?",
                "nếu", "mà", "rất", "chỉ", "A",
                "This word sets up a condition, like 'if it rains...'"));

        questionRepository.save(new Question(c, "Which word marks that an action already happened (past tense)?",
                "sẽ", "đang", "đã", "và", "C",
                "This short marker goes before a verb to show it happened in the past."));

        questionRepository.save(new Question(c, "Which word marks an action happening right now?",
                "đã", "đang", "sẽ", "của", "B",
                "This marker is equivalent to the '-ing' ending in English."));

        questionRepository.save(new Question(c, "Which word marks a future action?",
                "đã", "đang", "sẽ", "mà", "C",
                "This marker is placed before a verb to show something hasn't happened yet."));

        questionRepository.save(new Question(c, "Which word means \"very\"?",
                "rất", "chỉ", "cũng", "của", "A",
                "You'd use this to intensify an adjective, like '___ good.'"));

        questionRepository.save(new Question(c, "Which word means \"also\" or \"too\"?",
                "chỉ", "cũng", "rất", "mà", "B",
                "You'd use this when agreeing that something applies to you as well."));

        questionRepository.save(new Question(c, "Which word means \"only\"?",
                "chỉ", "rất", "và", "đã", "A",
                "This word limits something to just one thing, like '___ one left.'"));

        questionRepository.save(new Question(c, "Which word shows possession, similar to \"of\" in English?",
                "của", "mà", "thì", "vì", "A",
                "You'd use this between two nouns, like 'the book ___ my friend.'"));

        questionRepository.save(new Question(c, "Which word can act like \"then\" or mark the topic of a sentence?",
                "thì", "và", "nếu", "cũng", "A",
                "This word often follows the subject of a sentence to set up what comes next."));

        questionRepository.save(new Question(c, "Which word is often used like \"that\" or \"which\" to add extra detail?",
                "mà", "của", "rất", "chỉ", "A",
                "This word connects a clause that describes the noun before it."));

        questionRepository.save(new Question(c, "Vietnamese sentence order is most similar to which structure?",
                "Subject-Object-Verb", "Verb-Subject-Object", "Subject-Verb-Object", "Object-Verb-Subject", "C",
                "Vietnamese word order is actually very close to English's basic sentence structure."));
    }

    private void seedHistoryAndCulture() {
        String c = "History & Culture";
        questionRepository.save(new Question(c, "What is the capital of Vietnam?",
                "Ho Chi Minh City", "Da Nang", "Hanoi", "Hue", "C",
                "It's in the north of the country, not the largest city by population."));

        questionRepository.save(new Question(c, "What is Vietnam's most populous city?",
                "Hanoi", "Ho Chi Minh City", "Da Nang", "Can Tho", "B",
                "This city was formerly known as Saigon."));

        questionRepository.save(new Question(c, "What is the official currency of Vietnam?",
                "Baht", "Dong", "Yuan", "Peso", "B",
                "Its currency code is VND."));

        questionRepository.save(new Question(c, "What is the traditional long dress worn in Vietnam called?",
                "Kimono", "Hanbok", "Áo dài", "Sari", "C",
                "It's a long, fitted tunic worn over trousers, still commonly worn today."));

        questionRepository.save(new Question(c, "What is the Vietnamese Lunar New Year called?",
                "Tết", "Diwali", "Songkran", "Nowruz", "A",
                "This is the most important holiday of the year in Vietnamese culture."));

        questionRepository.save(new Question(c, "What is Vietnam's iconic noodle soup dish called?",
                "Pad Thai", "Phở", "Ramen", "Udon", "B",
                "It's typically made with rice noodles, broth, and either beef or chicken."));

        questionRepository.save(new Question(c, "Which region of the world is Vietnam located in?",
                "South Asia", "East Asia", "Southeast Asia", "Central Asia", "C",
                "This region also includes Thailand, Cambodia, and the Philippines."));

        questionRepository.save(new Question(c, "Ha Long Bay, a UNESCO World Heritage Site, is known for what natural feature?",
                "Active volcanoes", "Limestone karsts", "Glaciers", "Coral atolls", "B",
                "Thousands of these tall limestone islands rise out of the water in this bay."));

        questionRepository.save(new Question(c, "What color is Vietnam's national flag primarily?",
                "Blue with a white star", "Red with a yellow star", "Green with a red circle", "White with a blue stripe", "B",
                "The star has five points, one for each social class the country's founders recognized."));

        questionRepository.save(new Question(c, "What is the traditional conical hat worn in Vietnam called?",
                "Nón lá", "Sombrero", "Fedora", "Turban", "A",
                "It's made of woven palm leaves and is a common sight in rural areas."));

        questionRepository.save(new Question(c, "In what year was Vietnam reunified as one country after the war?",
                "1954", "1965", "1975", "1986", "C",
                "This was the year Saigon fell, ending the Vietnam War."));

        questionRepository.save(new Question(c, "What traditional Vietnamese instrument has only one string?",
                "Đàn bầu", "Guzheng", "Sitar", "Ukulele", "A",
                "Its name literally translates to 'gourd instrument,' referring to its resonator."));

        questionRepository.save(new Question(c, "What is the official language of Vietnam?",
                "Mandarin", "Khmer", "Tiếng Việt (Vietnamese)", "Thai", "C",
                "The country's own name for its language translates to 'Vietnamese speech/language.'"));

        questionRepository.save(new Question(c, "Which major river flows through southern Vietnam and several other Southeast Asian countries?",
                "Yangtze River", "Mekong River", "Ganges River", "Nile River", "B",
                "This river's delta in southern Vietnam is one of the most productive rice-growing regions in the world."));

        questionRepository.save(new Question(c, "In what year did Ho Chi Minh declare Vietnam's independence from France?",
                "1930", "1945", "1954", "1975", "B",
                "This declaration came shortly after the end of World War II."));
    }
}
