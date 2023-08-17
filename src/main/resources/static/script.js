function createQuestion(question) {
    return fetch("http://localhost:8080/questions/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(question),
    }).then((res) => res.json());
}

function deleteQuestion(question, id) {
    return fetch(`http://localhost:8080/questions/${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(question),
    }).then((res) => res.json());
}


function createAnswer(answer) {
    return fetch("http://localhost:8080/answers/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(answer),
    }).then((res) => res.json());
}


function onListClick(e) {
    e.preventDefault();

    const formData = new FormData(e.target);
    const question = Object.fromEntries(formData.entries());

    createQuestion(question)
        .then((response) => {
            e.target.reset();
        })
        .catch((error) => {
            console.error("Error adding question:", error);
        });

}

function displayHomePage() {
    const innerHTML = `<center><div id="home">
        <p id="greetings">Welcome to STACKOVERFLOW!</p>
        <button id="questionlist" onclick="listAllTheQuestions()">List All The Questions</button><br><br>
        <br>
        <form id="questionForm">
            <div>
                <label htmlFor="title"></label>
                <input type="text" id="inputField" placeholder="Enter question here:" name="title">
                
            </div>
            <br>
            <div>
                <button id="newquestion" type="submit">Add new Question</button>
            </div>
        </form><br><br>
        <div id="sortingButtons"></div>
        <div id="allQuestions"></div>
    </div></center>`;
    document.getElementById("root").insertAdjacentHTML("beforeend", innerHTML);

    const form = document.getElementById("questionForm");
    form.addEventListener("submit", onListClick);
}

async function fetchDetails(path) {
    const response = await fetch(path);
    const data = await response.json();
    return data;
}


function showTheAnswersForSpecificQuestion(id, answerContainer) {
    const url = `http://localhost:8080/answers/${id}`;

    fetchDetails(url)
        .then(data => {
            answerDisplayer(data, answerContainer);
        })
        .catch(error => {
            console.error("An error occurred:", error);
        });
}

function addAnswerToQuestion(e, question_id) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const answer = Object.fromEntries(formData.entries());
    answer.question_id = question_id;
    createAnswer(answer)
        .then((response) => {
            e.target.reset();
        })
        .catch((error) => {
            console.error("Error adding question:", error);
        });
}


function listAllTheQuestions() {
    fetchDetails("http://localhost:8080/questions/all")
        .then(data => {
            const rootE = document.getElementById("allQuestions");

            const questionListButton = document.getElementById("questionlist");
            questionListButton.style.display = "none";

            const backButton = document.createElement("button");
            backButton.textContent = "Back";
            backButton.addEventListener("click", () => {
                location.reload();
            });


            rootE.appendChild(backButton);
            questionDisplayer(data);


        })
        .catch(error => {
            console.error("Error fetching questions:", error);
        });
}

function answerDisplayer(data, answerContainer) {
    answerContainer.innerHTML = "";

    data.forEach(answer => {
        const answerContainer1 = document.createElement("div");
        answerContainer1.classList.add("answer-container");

        const answerContent = document.createElement("p");
        answerContent.textContent = `Answer: ${answer.answer}`;

        const formattedDate = new Date(answer.created.replace('T', ' ')).toLocaleString();
        const date = document.createElement("p");
        date.textContent = `Date: ${formattedDate}`;
        const emptyLine = document.createElement("hr");

        answerContainer1.appendChild(emptyLine);
        answerContainer1.appendChild(answerContent);
        answerContainer1.appendChild(date);

        answerContainer.appendChild(answerContainer1);

    });

}

function questionDisplayer(baseData, rootE) {
    baseData.forEach(question => {
        const questionContainer = document.createElement("div");
        questionContainer.classList.add("question-container");


        const title = document.createElement("h3");
        title.textContent = `Question: ${question.title}`;


        const formattedDate = new Date(question.created.replace('T', ' ')).toLocaleString();
        const date = document.createElement("p");
        date.textContent = `Date: ${formattedDate}`;

        const count = document.createElement("p");
        count.textContent = `Answers: ${question.answer_count}`;

        const answerContainer = document.createElement("div");
        const showTheAnswersButton = document.createElement("button");
        showTheAnswersButton.textContent = "Show the answers";
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete question";
        deleteButton.addEventListener("click", e => {
            deleteQuestion(question, question.id);
            document.getElementById("allQuestions").innerHTML = "";
            listAllTheQuestions();
        });

        const showTheAnswersBackButton = document.createElement("button");
        showTheAnswersBackButton.textContent = "Close";
        showTheAnswersBackButton.addEventListener("click", event => {
            answerContainer.innerHTML = "";
            questionContainer.removeChild(showTheAnswersBackButton);
            questionContainer.appendChild(showTheAnswersButton)
        })


        showTheAnswersButton.addEventListener("click", event => {
            const questionId = question.id;
            showTheAnswersForSpecificQuestion(questionId, answerContainer);
            questionContainer.removeChild(showTheAnswersButton);
            questionContainer.appendChild(showTheAnswersBackButton)

        });


        questionContainer.appendChild(title);
        questionContainer.appendChild(date);
        questionContainer.appendChild(count);
        questionContainer.appendChild(answerContainer);
        questionContainer.appendChild(deleteButton)

        if (question.answer_count > 0) {
            questionContainer.appendChild(showTheAnswersButton);
        }

        const answerForm = document.createElement("form");
        answerForm.classList.add("answer-form");
        answerForm.addEventListener("submit", (e) => {
            addAnswerToQuestion(e, question.id);
            document.getElementById("allQuestions").innerHTML = "";
            listAllTheQuestions()
        });


        const answerInput = document.createElement("input");
        answerInput.type = "text";
        answerInput.placeholder = "Enter your answer here...";
        answerInput.name = "answer";

        const submitButton = document.createElement("button");
        submitButton.type = "submit";
        submitButton.textContent = "Submit Answer";


        answerForm.appendChild(answerInput);
        answerForm.appendChild(submitButton);
        questionContainer.appendChild(answerForm);


        const allQ = document.getElementById("allQuestions");
        allQ.appendChild(questionContainer);

        const emptyLine = document.createElement("hr");
        allQ.appendChild(emptyLine);
        allQ.appendChild(document.createElement("br"));
    });
}

//Sorting
function sortingButton(sortBy) {
    const allQ = document.getElementById("allQuestions");
    allQ.innerHTML = "";
    const sortingButtons = document.getElementById('sortingButtons');

    sortingButtons.insertAdjacentHTML('beforeend', `<button id="sorting_${sortBy}">Sort by: ${sortBy}</button>`);

    const sortingButton = sortingButtons.querySelector(`#sorting_${sortBy}`);


    sortingButton.addEventListener('click', () => {
        sortingMechanism(sortBy);
    });

}

let ascendingOrder = true;

async function sortingMechanism(sortBy) {
    try {
        const response = await fetch("http://localhost:8080/questions/all");
        const questionsArray = await response.json();

        if (ascendingOrder) {
            questionsArray.sort(function (a, b) {
                if (a[sortBy] < b[sortBy]) {
                    return -1;
                }
                if (a[sortBy] > b[sortBy]) {
                    return 1;
                }
                return 0;
            });
        } else {
            questionsArray.sort(function (a, b) {
                if (a[sortBy] > b[sortBy]) {
                    return -1;
                }
                if (a[sortBy] < b[sortBy]) {
                    return 1;
                }
                return 0;
            });
        }

        const allQuestionsDiv = document.getElementById("allQuestions");
        allQuestionsDiv.innerHTML = "";

        questionDisplayer(questionsArray);


        ascendingOrder = !ascendingOrder;

    } catch (error) {
        console.error("Error fetching and sorting data:", error);
    }
}


function main() {
    displayHomePage();
    sortingButton("title");
    sortingButton("created");
    sortingButton("answer_count");
}


main();