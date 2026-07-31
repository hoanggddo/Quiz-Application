-- Seed a few starter questions per category so the API is usable immediately.
-- Replace/expand with the original question bank from the Swing app.

INSERT INTO questions (category, question_text, option_a, option_b, option_c, option_d, correct_option)
SELECT 'Vocabulary', 'What does "xin chào" mean?', 'Goodbye', 'Hello', 'Thank you', 'Please', 'B'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE question_text = 'What does "xin chào" mean?');

INSERT INTO questions (category, question_text, option_a, option_b, option_c, option_d, correct_option)
SELECT 'Vocabulary', 'What does "cảm ơn" mean?', 'Sorry', 'Please', 'Thank you', 'Yes', 'C'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE question_text = 'What does "cảm ơn" mean?');

INSERT INTO questions (category, question_text, option_a, option_b, option_c, option_d, correct_option)
SELECT 'Grammar', 'Which word means "and" in Vietnamese?', 'và', 'nhưng', 'hoặc', 'vì', 'A'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE question_text = 'Which word means "and" in Vietnamese?');

INSERT INTO questions (category, question_text, option_a, option_b, option_c, option_d, correct_option)
SELECT 'History & Culture', 'What is the capital of Vietnam?', 'Ho Chi Minh City', 'Da Nang', 'Hanoi', 'Hue', 'C'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE question_text = 'What is the capital of Vietnam?');
