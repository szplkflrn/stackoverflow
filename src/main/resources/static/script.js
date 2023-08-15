

function displayHomePage() {
    const innerHTML = `<center><div id="home">
  <p id="greetings">Welcome to STACKOVERFLOW!<p>
  <button id="questionlist">List All The Questions</button><br><br>
  <button id="newquestion">Add new Question</button>
  </div></center>`;
    document.getElementById("root").insertAdjacentHTML("beforeend", innerHTML);
}

async function fetchDetails(path) {
    const response = await fetch(path);
    const data = await response.json();
    console.log(data)
    return data;
}

async function get (){
    const data = await fetchDetails("http://localhost:8080/questions/all");
    const rootE = document.getElementById("root");


}


function main() {
    displayHomePage();
get();
}


main();