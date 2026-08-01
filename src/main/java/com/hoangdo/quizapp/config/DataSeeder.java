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

        questionRepository.save(new Question(c, "What does \"mẹ\" mean?",
                "Father", "Mother", "Sister", "Aunt", "B",
                "This is one of the first words most children learn to say."));

        questionRepository.save(new Question(c, "What does \"bố\" mean?",
                "Father", "Mother", "Brother", "Uncle", "A",
                "In the south of Vietnam, people more often say 'ba' for this word."));

        questionRepository.save(new Question(c, "What does \"anh\" mean?",
                "Younger sibling", "Older brother", "Grandfather", "Friend", "B",
                "This word is also used respectfully to address an older male peer."));

        questionRepository.save(new Question(c, "What does \"chị\" mean?",
                "Older sister", "Younger sister", "Mother", "Daughter", "A",
                "This word is also used respectfully to address an older female peer."));

        questionRepository.save(new Question(c, "What does \"em\" mean?",
                "Older sibling", "Younger sibling", "Parent", "Grandparent", "B",
                "This word is used for anyone younger than you, related or not."));

        questionRepository.save(new Question(c, "What does \"nhà\" mean?",
                "School", "Market", "House / home", "Hospital", "C",
                "This is where you live."));

        questionRepository.save(new Question(c, "What does \"trường học\" mean?",
                "Hospital", "School", "Market", "Restaurant", "B",
                "This is where students go to learn."));

        questionRepository.save(new Question(c, "What does \"bệnh viện\" mean?",
                "Hospital", "Pharmacy", "School", "Police station", "A",
                "You'd go here if you were seriously sick or injured."));

        questionRepository.save(new Question(c, "What does \"chợ\" mean?",
                "Supermarket", "Market", "Restaurant", "Bank", "B",
                "This is usually an open-air place where vendors sell fresh food."));

        questionRepository.save(new Question(c, "What does \"tiền\" mean?",
                "Time", "Money", "Food", "Water", "B",
                "You need this to buy things."));

        questionRepository.save(new Question(c, "What does \"sách\" mean?",
                "Pen", "Book", "Paper", "Table", "B",
                "You read this."));

        questionRepository.save(new Question(c, "What does \"bàn\" mean?",
                "Chair", "Table", "Door", "Window", "B",
                "You often eat or work at this piece of furniture."));

        questionRepository.save(new Question(c, "What does \"ghế\" mean?",
                "Table", "Chair", "Bed", "Shelf", "B",
                "You sit on this."));

        questionRepository.save(new Question(c, "What does \"xe\" mean?",
                "Vehicle / car", "Bicycle only", "Airplane", "Boat", "A",
                "This general word covers cars, motorbikes, and other vehicles."));

        questionRepository.save(new Question(c, "What does \"đường\" mean?",
                "River", "Road / street", "Mountain", "Forest", "B",
                "You walk or drive along this."));

        questionRepository.save(new Question(c, "What does \"ngày\" mean?",
                "Night", "Week", "Day", "Month", "C",
                "There are 24 hours in one of these."));

        questionRepository.save(new Question(c, "What does \"đêm\" mean?",
                "Morning", "Afternoon", "Night", "Noon", "C",
                "The sky is dark during this time."));

        questionRepository.save(new Question(c, "What does \"buổi sáng\" mean?",
                "Morning", "Evening", "Midnight", "Afternoon", "A",
                "This is the part of the day right after you wake up."));

        questionRepository.save(new Question(c, "What does \"buổi tối\" mean?",
                "Morning", "Evening / night", "Noon", "Dawn", "B",
                "This is the part of the day after the sun goes down."));

        questionRepository.save(new Question(c, "What does \"hôm nay\" mean?",
                "Yesterday", "Tomorrow", "Today", "Next week", "C",
                "This word refers to the current day."));

        questionRepository.save(new Question(c, "What does \"ngày mai\" mean?",
                "Yesterday", "Tomorrow", "Today", "Last year", "B",
                "This word refers to the day after today."));

        questionRepository.save(new Question(c, "What does \"hôm qua\" mean?",
                "Tomorrow", "Today", "Yesterday", "Next month", "C",
                "This word refers to the day before today."));

        questionRepository.save(new Question(c, "What does \"một\" mean?",
                "One", "Two", "Three", "Zero", "A",
                "This is the first counting number."));

        questionRepository.save(new Question(c, "What does \"hai\" mean?",
                "One", "Two", "Ten", "Five", "B",
                "This number comes right after 'một.'"));

        questionRepository.save(new Question(c, "What does \"mười\" mean?",
                "One", "Five", "Ten", "One hundred", "C",
                "This is the number of fingers most people have."));

        questionRepository.save(new Question(c, "What does \"lớn\" mean?",
                "Small", "Big", "Fast", "Slow", "B",
                "The opposite of this word is 'nhỏ.'"));

        questionRepository.save(new Question(c, "What does \"nhỏ\" mean?",
                "Big", "Small", "Tall", "Wide", "B",
                "The opposite of this word is 'lớn.'"));

        questionRepository.save(new Question(c, "What does \"nóng\" mean?",
                "Cold", "Hot", "Warm breeze", "Freezing", "B",
                "You'd feel this way standing next to a fire."));

        questionRepository.save(new Question(c, "What does \"lạnh\" mean?",
                "Hot", "Cold", "Humid", "Dry", "B",
                "You'd feel this way outside in winter."));

        questionRepository.save(new Question(c, "What does \"mưa\" mean?",
                "Snow", "Wind", "Rain", "Sunshine", "C",
                "You'd want an umbrella when this is happening."));

        questionRepository.save(new Question(c, "What does \"nắng\" mean?",
                "Rain", "Sunny / sunshine", "Cloudy", "Foggy", "B",
                "You'd wear sunglasses on a day like this."));

        questionRepository.save(new Question(c, "What does \"con mèo\" mean?",
                "Dog", "Cat", "Bird", "Fish", "B",
                "This common household pet says 'meo meo' in Vietnamese, similar to 'meow.'"));

        questionRepository.save(new Question(c, "What does \"nói\" mean?",
                "To listen", "To speak", "To read", "To write", "B",
                "You do this with your mouth to communicate."));

        questionRepository.save(new Question(c, "What does \"nghe\" mean?",
                "To see", "To hear / listen", "To smell", "To taste", "B",
                "You do this with your ears."));

        questionRepository.save(new Question(c, "What does \"ngủ\" mean?",
                "To wake up", "To sleep", "To run", "To cook", "B",
                "You do this at night in bed."));
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

        questionRepository.save(new Question(c, "Which word means \"not yet\"?",
                "chưa", "rồi", "đã", "sẽ", "A",
                "You'd use this to answer that something hasn't happened, but might later."));

        questionRepository.save(new Question(c, "Which word marks that an action has already been completed?",
                "chưa", "rồi", "sẽ", "đang", "B",
                "This word often appears at the end of a sentence, like 'ăn ___' (already ate)."));

        questionRepository.save(new Question(c, "Which word means \"who\"?",
                "ai", "gì", "đâu", "nào", "A",
                "You'd use this to ask about a person."));

        questionRepository.save(new Question(c, "Which word means \"what\"?",
                "ai", "gì", "đâu", "sao", "B",
                "You'd use this to ask about a thing, like 'you want ___?'"));

        questionRepository.save(new Question(c, "Which word means \"where\"?",
                "ở đâu", "khi nào", "tại sao", "bao nhiêu", "A",
                "You'd use this to ask about a location."));

        questionRepository.save(new Question(c, "Which word means \"when\"?",
                "ở đâu", "khi nào", "thế nào", "gì", "B",
                "You'd use this to ask about time."));

        questionRepository.save(new Question(c, "Which word means \"why\"?",
                "tại sao", "thế nào", "bao nhiêu", "nào", "A",
                "You'd use this to ask for a reason."));

        questionRepository.save(new Question(c, "Which word means \"how\"?",
                "tại sao", "thế nào", "ai", "đâu", "B",
                "You'd use this to ask about the manner or way something is done."));

        questionRepository.save(new Question(c, "Which word means \"how much / how many\"?",
                "bao nhiêu", "nào", "gì", "đâu", "A",
                "You'd use this to ask about quantity or price."));

        questionRepository.save(new Question(c, "Which word means \"this\"?",
                "này", "đó", "kia", "nào", "A",
                "You'd use this word to point at something close to you."));

        questionRepository.save(new Question(c, "Which word means \"that\"?",
                "này", "đó", "ai", "gì", "B",
                "You'd use this word to point at something further away, but still nearby."));

        questionRepository.save(new Question(c, "Which word marks a specific, known plural (a definite group of people or things)?",
                "các", "những", "mỗi", "cả", "A",
                "You'd use this before a noun when referring to 'the [plural noun]' as a whole group."));

        questionRepository.save(new Question(c, "Which word marks an indefinite plural, similar to \"some\"?",
                "các", "những", "mỗi", "tất cả", "B",
                "You'd use this before a noun to mean 'some of the [noun]' rather than all of them."));

        questionRepository.save(new Question(c, "Which word means \"each\" or \"every\"?",
                "mỗi", "cả", "những", "các", "A",
                "You'd use this to talk about individual members of a group one at a time."));

        questionRepository.save(new Question(c, "Which word means \"all\"?",
                "mỗi", "tất cả", "những", "nào", "B",
                "You'd use this to refer to the complete set of something, with nothing left out."));

        questionRepository.save(new Question(c, "Which word means \"want\"?",
                "muốn", "phải", "nên", "được", "A",
                "This word expresses desire, like '___ ăn' (want to eat)."));

        questionRepository.save(new Question(c, "Which word expresses ability, similar to \"can\"?",
                "phải", "nên", "có thể", "muốn", "C",
                "This phrase expresses that something is possible or that you're capable of doing it."));

        questionRepository.save(new Question(c, "Which word means \"must\" or \"have to\"?",
                "muốn", "phải", "được", "nên", "B",
                "This word expresses obligation, not just preference."));

        questionRepository.save(new Question(c, "Which word means \"should\"?",
                "phải", "nên", "muốn", "được", "B",
                "This word gives advice or a recommendation, softer than 'phải.'"));

        questionRepository.save(new Question(c, "Which word can mean \"allowed to\" or express a positive ability/result?",
                "được", "bị", "phải", "muốn", "A",
                "This word often has a positive connotation, unlike its counterpart 'bị.'"));

        questionRepository.save(new Question(c, "Which word marks a passive action with a negative connotation, similar to \"suffer\"?",
                "được", "bị", "nên", "hãy", "B",
                "You'd use this word for something unfortunate that happens to you, like getting sick or scolded."));

        questionRepository.save(new Question(c, "Which word is used to soften a command, similar to \"let's\" or \"please\"?",
                "hãy", "đừng", "chưa", "rồi", "A",
                "This word comes before a verb to make a suggestion or gentle command."));

        questionRepository.save(new Question(c, "Which word means \"don't,\" used for negative commands?",
                "hãy", "đừng", "chưa", "không", "B",
                "You'd use this word to tell someone not to do something."));

        questionRepository.save(new Question(c, "Which word is a formal/polite marker often used when making a request?",
                "xin", "làm ơn", "vâng", "dạ", "A",
                "This word often appears at the start of a formal request, like '___ chào' (a formal hello)."));

        questionRepository.save(new Question(c, "Which particle softens a sentence, similar to adding \"okay?\" at the end?",
                "nhé", "à", "thôi", "luôn", "A",
                "You'd add this to the end of a suggestion to make it sound friendly, like 'đi ___' (let's go, okay?)."));

        questionRepository.save(new Question(c, "Which particle turns a statement into a casual question?",
                "nhé", "à", "nữa", "cũng", "B",
                "You'd add this to the end of a sentence, similar to raising your voice in English to ask a question."));

        questionRepository.save(new Question(c, "Which phrase turns a statement into a tag question, similar to \", right?\"",
                "phải không", "cũng không", "chưa xong", "vẫn chưa", "A",
                "You'd add this to the end of a sentence when you expect the listener to agree with you."));

        questionRepository.save(new Question(c, "Which phrase means \"both\"?",
                "cả hai", "mỗi cái", "từng cái", "tất cả", "A",
                "You'd use this phrase to refer to two things together, not just one."));

        questionRepository.save(new Question(c, "Which structure means \"although...but\"?",
                "tuy...nhưng", "càng...càng", "vừa...vừa", "hoặc...hoặc", "A",
                "This pairs a concession with a contrasting result, like 'although tired, but happy.'"));

        questionRepository.save(new Question(c, "Which structure means \"the more...the more\"?",
                "tuy...nhưng", "càng...càng", "vừa...vừa", "nếu...thì", "B",
                "This structure shows two things increasing together, like 'the more you practice, the better you get.'"));

        questionRepository.save(new Question(c, "Which structure means \"both...and\" for simultaneous actions or qualities?",
                "vừa...vừa", "càng...càng", "tuy...nhưng", "hoặc...hoặc", "A",
                "This structure describes two things being true or happening at the same time."));

        questionRepository.save(new Question(c, "Which word means \"just\" or \"recently\"?",
                "mới", "lại", "nữa", "luôn", "A",
                "You'd use this word to say something happened a short time ago, like '___ đến' (just arrived)."));

        questionRepository.save(new Question(c, "Which word means \"again\"?",
                "mới", "lại", "nữa", "thôi", "B",
                "You'd use this word when an action repeats, like 'nói ___' (say again)."));

        questionRepository.save(new Question(c, "Which word means \"always\"?",
                "luôn", "thường", "ít khi", "hay", "A",
                "This word expresses that something happens every single time, without exception."));

        questionRepository.save(new Question(c, "Which word means \"usually\"?",
                "luôn", "thường", "chưa", "mới", "B",
                "This word expresses that something happens most of the time, but not necessarily always."));
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

        questionRepository.save(new Question(c, "Which country borders Vietnam to the north?",
                "Thailand", "China", "Myanmar", "Malaysia", "B",
                "This is the largest country by population in the world."));

        questionRepository.save(new Question(c, "Which two countries border Vietnam to the west?",
                "Laos and Cambodia", "Thailand and Myanmar", "China and Laos", "Cambodia and Thailand", "A",
                "Both of these countries also border the Mekong River."));

        questionRepository.save(new Question(c, "What sea lies along Vietnam's eastern coast?",
                "Arabian Sea", "South China Sea", "Yellow Sea", "Andaman Sea", "B",
                "Vietnam refers to this body of water as the 'East Sea.'"));

        questionRepository.save(new Question(c, "What type of government does modern Vietnam have?",
                "Constitutional monarchy", "One-party socialist republic", "Federal democracy", "Military junta", "B",
                "It's led by the Communist Party of Vietnam."));

        questionRepository.save(new Question(c, "What was the last imperial dynasty of Vietnam called?",
                "Lý Dynasty", "Trần Dynasty", "Nguyễn Dynasty", "Lê Dynasty", "C",
                "This dynasty ruled from 1802 until 1945."));

        questionRepository.save(new Question(c, "Who was the last emperor of Vietnam?",
                "Bảo Đại", "Gia Long", "Minh Mạng", "Tự Đức", "A",
                "He abdicated in 1945 as the Nguyễn Dynasty came to an end."));

        questionRepository.save(new Question(c, "Which city served as the imperial capital under the Nguyễn Dynasty?",
                "Hanoi", "Hue", "Da Nang", "Saigon", "B",
                "This city's Imperial City complex is now a UNESCO World Heritage Site."));

        questionRepository.save(new Question(c, "Vietnam was colonized by which European power?",
                "Britain", "Portugal", "France", "Spain", "C",
                "Vietnam was part of French Indochina for roughly a century."));

        questionRepository.save(new Question(c, "What battle in 1954 ended French colonial rule in Vietnam?",
                "Battle of Hanoi", "Battle of Điện Biên Phủ", "Battle of Hue", "Battle of Saigon", "B",
                "This decisive battle led directly to the Geneva Accords."));

        questionRepository.save(new Question(c, "How many points does the star on Vietnam's flag have?",
                "3", "4", "5", "6", "C",
                "Each point is said to represent a different social class: workers, farmers, soldiers, intellectuals, and youth."));

        questionRepository.save(new Question(c, "In the Vietnamese zodiac, which animal replaces the rabbit found in the Chinese zodiac?",
                "Dog", "Cat", "Tiger", "Horse", "B",
                "Both zodiacs share 11 of the same 12 animals, this is the one exception."));

        questionRepository.save(new Question(c, "What is the modern Vietnamese writing system, based on the Latin alphabet, called?",
                "Chữ Nôm", "Chữ Hán", "Chữ Quốc Ngữ", "Chữ Phạn", "C",
                "It replaced older Chinese-character-based writing systems and is what Vietnamese is written in today."));

        questionRepository.save(new Question(c, "What popular Vietnamese sandwich is served on a baguette?",
                "Bún chả", "Bánh mì", "Gỏi cuốn", "Phở", "B",
                "This dish reflects French colonial influence on Vietnamese cuisine."));

        questionRepository.save(new Question(c, "What is the traditional Vietnamese iced coffee drink called?",
                "Cà phê sữa đá", "Trà đá", "Nước mía", "Sinh tố", "A",
                "It's typically made with strong drip coffee and sweetened condensed milk."));

        questionRepository.save(new Question(c, "Besides Ha Long Bay, which ancient trading port town is a UNESCO World Heritage Site known for its lanterns?",
                "Hoi An", "Da Lat", "Nha Trang", "Vung Tau", "A",
                "This town's old quarter is famous for its glowing lanterns at night."));

        questionRepository.save(new Question(c, "What is Vietnam's highest mountain, located near Sapa?",
                "Bach Ma", "Fansipan", "Ba Vi", "Lang Biang", "B",
                "It's sometimes nicknamed the 'Roof of Indochina.'"));

        questionRepository.save(new Question(c, "Vietnam's shape is often compared to which letter?",
                "C", "S", "Z", "L", "B",
                "The country is long, narrow, and curved along the coastline."));

        questionRepository.save(new Question(c, "What traditional Vietnamese performance art involves puppets performing on water?",
                "Cải lương", "Múa rối nước", "Chèo", "Tuồng", "B",
                "This art form originated in the rice paddies of the Red River Delta."));

        questionRepository.save(new Question(c, "Who is considered the founding leader of modern independent Vietnam?",
                "Ngô Đình Diệm", "Hồ Chí Minh", "Bảo Đại", "Võ Nguyên Giáp", "B",
                "Vietnam's largest city was later renamed after this leader."));

        questionRepository.save(new Question(c, "In which city is Ho Chi Minh's mausoleum located?",
                "Ho Chi Minh City", "Hanoi", "Hue", "Da Nang", "B",
                "It's located in Vietnam's capital, not the city that shares his name."));

        questionRepository.save(new Question(c, "Along which parallel was Vietnam divided after the 1954 Geneva Accords?",
                "The 17th parallel", "The 20th parallel", "The 38th parallel", "The 30th parallel", "A",
                "This created a North and South Vietnam until reunification in 1975."));

        questionRepository.save(new Question(c, "What is the symbol used for Vietnam's currency?",
                "$", "¥", "₫", "£", "C",
                "This symbol represents the Vietnamese Dong."));

        questionRepository.save(new Question(c, "What two colors are considered especially lucky during Tết?",
                "Blue and white", "Red and gold", "Green and black", "Purple and silver", "B",
                "You'll see envelopes of these colors used for gifting lucky money during the holiday."));

        questionRepository.save(new Question(c, "What is the traditional Vietnamese custom of chewing betel nut and leaf called?",
                "Ăn trầu", "Uống trà", "Đốt nhang", "Gói bánh", "A",
                "This custom historically symbolized hospitality and was common at weddings."));

        questionRepository.save(new Question(c, "Vietnam is one of the world's largest exporters of which beverage crop?",
                "Tea", "Coffee", "Cocoa", "Sugarcane", "B",
                "Vietnam is consistently ranked among the top two producers of this crop worldwide, mostly grown in the Central Highlands."));

        questionRepository.save(new Question(c, "What is the official name of unified Vietnam, proclaimed in 1976?",
                "Democratic Republic of Vietnam", "Socialist Republic of Vietnam", "Republic of Vietnam", "United Provinces of Vietnam", "B",
                "This name has been used since North and South Vietnam were reunified."));

        questionRepository.save(new Question(c, "On what date is Vietnam's National Day (Independence Day) celebrated?",
                "July 4", "September 2", "October 1", "January 1", "B",
                "This is the date in 1945 that Ho Chi Minh read the Declaration of Independence in Hanoi."));

        questionRepository.save(new Question(c, "What does \"Thăng Long,\" the historic former name of Hanoi, mean?",
                "Golden River", "Ascending Dragon", "Peaceful Valley", "Mountain Capital", "B",
                "Legend says a king saw a dragon rise from the water at this site, inspiring the name."));

        questionRepository.save(new Question(c, "Which national park in Vietnam is famous for its extensive cave systems?",
                "Cat Ba National Park", "Phong Nha-Ke Bang National Park", "Cuc Phuong National Park", "Ba Be National Park", "B",
                "It's home to Son Doong, one of the largest caves in the world."));

        questionRepository.save(new Question(c, "Which UNESCO site near Hoi An contains ruins of the ancient Cham civilization?",
                "My Son Sanctuary", "Trang An", "Citadel of Ho Dynasty", "Complex of Hue Monuments", "A",
                "These brick temple ruins were built by the Champa Kingdom starting around the 4th century."));

        questionRepository.save(new Question(c, "What popular Vietnamese dish consists of grilled pork with rice noodles and dipping sauce?",
                "Bún chả", "Bánh xèo", "Cơm tấm", "Mì Quảng", "A",
                "This dish became especially well known internationally after being featured in a famous photo with a visiting U.S. president."));

        questionRepository.save(new Question(c, "What are fresh, translucent Vietnamese spring rolls usually called?",
                "Chả giò", "Gỏi cuốn", "Bánh cuốn", "Nem nướng", "B",
                "Unlike the fried version, these are wrapped in rice paper and served fresh, not cooked."));

        questionRepository.save(new Question(c, "Which grain is Vietnam one of the world's top exporters of?",
                "Wheat", "Corn", "Rice", "Barley", "C",
                "The Mekong Delta is one of the most productive growing regions for this crop."));

        questionRepository.save(new Question(c, "What is the common name for the Vietnam War as it's referred to within Vietnam itself?",
                "The French War", "The American War", "The Northern War", "The Independence War", "B",
                "From the Vietnamese perspective, this name distinguishes it from the earlier conflict against France."));

        questionRepository.save(new Question(c, "What is Vietnam's most iconic rice-growing region, often called the country's 'rice basket'?",
                "Red River Delta", "Mekong Delta", "Central Highlands", "Annamite Range", "B",
                "This vast delta in the south is fed by a river that also flows through Cambodia, Laos, and China."));
    }
}
