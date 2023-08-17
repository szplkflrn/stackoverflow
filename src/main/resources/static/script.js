function createQuestion(question) {
    return fetch("http://localhost:8080/questions/", {
        method: "POST",
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
    const answer = Object.fromEntries(formData.entries());

    createQuestion(answer)
        .then((response) => {
            console.log("Answer added:", response);
            e.target.reset();
        })
        .catch((error) => {
            console.error("Error adding question:", error);
        });
}

function displayHomePage() {
    const innerHTML = `<center><div id="home">
        <p id="greetings">Welcome to STACKOVERFLOW!</p>
        <div id="sortingButtons"></div>
        <form id="questionForm">
            <div>
                <label htmlFor="title"></label>
                <input type="text" placeholder="Enter question here:" name="title">
            </div>
            <div>
                <button id="newquestion" type="submit">Add new Question</button>
            </div>
        </form><br><br>
        <button id="questionlist" onclick="listAllTheQuestions()">List All The Questions</button>
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

function addAnswerToQuestion(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const question = Object.fromEntries(formData.entries());

    createQuestion(question)
        .then((response) => {
            ("Question added:", response);
            e.target.reset();
        })
        .catch((error) => {
            console.error("Error adding question:", error);
        });
}


function listAllTheQuestions() {
    fetchDetails("http://localhost:8080/questions/all")
        .then(data => {
            const rootE = document.getElementById("root");

            const questionListButton = document.getElementById("questionlist");
            questionListButton.style.display = "none";

            const backButton = document.createElement("button");
            backButton.textContent = "Back";
            backButton.classList.add("back-button");
            backButton.addEventListener("click", () => {
                location.reload();
            });

            const backButtonContainer = document.createElement("div");
            backButtonContainer.classList.add("back-button-container");
            backButtonContainer.appendChild(backButton);

            rootE.appendChild(backButtonContainer);


            questionDisplayer(data);

        })
        .catch(error => {
            console.error("Error fetching questions:", error);
        });
}

function questionDisplayer(baseData) {
    baseData.forEach(question => {
        const questionContainer = document.createElement("div");
        questionContainer.classList.add("question-container");


        const title = document.createElement("h3");
        title.textContent = `Title: ${question.title}`;

        const description = document.createElement("p");
        description.textContent = `Description: ${question.description}`;

        const formattedDate = new Date(question.created.replace('T', ' ')).toLocaleString();
        const date = document.createElement("p");
        date.textContent = `Date: ${formattedDate}`;

        const count = document.createElement("p");
        count.textContent = `Answers: ${question.answer_count}`;

        questionContainer.appendChild(title);
        questionContainer.appendChild(description);
        questionContainer.appendChild(date);
        questionContainer.appendChild(count);

        const form = document.createElement("form");
        form.addEventListener("submit", addAnswerToQuestion);
        const input = document.createElement("input");
        input.type = "text";
        input.placeholder = "Enter answer here:";
        form.appendChild(input);

        const answerbutton = document.createElement("button");
        answerbutton.textContent = `Add answer`;
        answerbutton.type = "button";
        answerbutton.onclick = addAnswerToQuestion;

        form.appendChild(answerbutton);
        questionContainer.appendChild(form);

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

    sortingButtons.insertAdjacentHTML('beforeend', `<button id="sorting_${sortBy}">sorting${sortBy}</button>`);

    const sortingButton = sortingButtons.querySelector(`#sorting_${sortBy}`);

    sortingButton.addEventListener('click', () => {
        console.log("Működik!");
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