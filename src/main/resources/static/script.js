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

function addAnswerToQuestion(e){
    e.preventDefault();
    const formData = new FormData(e.target);
    const question = Object.fromEntries(formData.entries());

    createQuestion(question)
        .then((response) => {
            console.log("Question added:", response);
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


            data.forEach(question => {
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

                rootE.appendChild(questionContainer);

                const emptyLine = document.createElement("hr");
                rootE.appendChild(emptyLine);
                rootE.appendChild(document.createElement("br"));
            });
        })
        .catch(error => {
            console.error("Error fetching questions:", error);
        });
}


function main() {
    displayHomePage();
}


main();