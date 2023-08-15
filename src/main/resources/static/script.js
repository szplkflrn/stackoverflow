


function displayHomePage() {
    const innerHTML = `<div id="home">
  <p id="greetings">Welcome to STACKOVERFLOW!<p>
  </div>`;
    document.getElementById("root").insertAdjacentHTML("beforeend", innerHTML);
}

displayHomePage();