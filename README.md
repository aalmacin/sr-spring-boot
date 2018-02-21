# Spaced Repetition App
## What is the spaced repetition app?

### Scope
- Simple website that will keep track of all the topics you are learning
- A mobile app which connects to the API to keep track of the topics
- All topics are categorized by subject and the subject can be categorized again. There is a hierarchy
- The site will tell you what and when you need to study an item
- After studying for the topic, the site will ask you on how much do you think you understood the topic
- There are two memorization options. Short-term and long-term
- Each user will have their own profile
- Analysis on how much they understand a particular topic is shown in a graph
- Associate Social Media accounts on the app

---


### Typical User Flow
1. User sees the home page
2. Click on login
3. The user will be shown all the categories and an option to create one
4. The user will select a category
5. The user will have the option to see the topics and their schedule, to add a new topic, or view their progress
6. If the user selected a topic from schedule by clicking, the user will start reviewing the topic and then leave a feedback on how much they understand the topic. How much they understand will depend on how long they believe they will recall the topic. When a user adds a topic, the user will be required to study it right away. After the user studied the topic, the user will be required to state how much they understood the topic or to create sub-topics. The sub-topics will help in making the topics more granular. Once a sub-topic has been added, the parent topic will be removed from schedule will be replaced by the sub-topics.

---


### Understanding measurement (Long term)
- 1 - Does not understand at all. Already forgotten.
- 2 - Understand a little bit or partially. Will forget in 20-30 minutes.
- 3 - Understand most of it. Main point is understood. Will forget in 1 day.
- 4 - Understand it completely. Will be asked to review again in 2-3 weeks. When 4 is set again, the user will be reminded in 2-3 months.

---

### Iteration 1
- Foundation for models
- Simple website
- Long-term study
- Login, profile, registration
- Categorization, schedule, sub-topics
- Algorithm for scheduling

---

## Updates
- Schedule only exists to show the next study time
