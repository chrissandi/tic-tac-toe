let gameId = null;
let currentPlayer = null;
let boardSize = 0;
let gameStatus = "IN_PROGRESS";

function toggleConsecutiveInput() {
    const checkbox = document.getElementById("withConsecutive");
    const input = document.getElementById("requiredConsecutive");
    input.disabled = !checkbox.checked;
}

async function startGame() {
    const size = parseInt(document.getElementById("boardSize").value);
    const playerX = document.getElementById("playerX").value;
    const playerO = document.getElementById("playerO").value;
    const withConsecutive = document.getElementById("withConsecutive").checked;
    const consecutiveInput = document.getElementById("requiredConsecutive").value;

    if (!size || !playerX || !playerO) {
        alert("Please fill in all fields.");
        return;
    }

    let requiredConsecutive = size;

    if (withConsecutive) {
        const inputValue = parseInt(consecutiveInput);
        if (!inputValue || inputValue < 3 || inputValue > size) {
            alert(`Required Consecutive must be between 3 and ${size}`);
            return;
        }
        requiredConsecutive = inputValue;
    }

    const url = `http://localhost:8080/api/games?boardSize=${size}&playerX=${playerX}&playerO=${playerO}&requiredConsecutive=${requiredConsecutive}`;

    const response = await fetch(url, { method: "POST" });
    const data = await response.json();

    gameId = data.gameId;
    boardSize = data.boardSize;
    currentPlayer = data.currentPlayer;
    gameStatus = data.status;

    updateStatus();
    await renderBoard();
}

function updateStatus() {
    const statusDiv = document.getElementById("status");
    if (gameStatus === "IN_PROGRESS") {
        statusDiv.textContent = `🎮 Turn: ${currentPlayer}`;
    } else {
        const statusText = gameStatus.replaceAll("_", " ").toLowerCase();
        statusDiv.textContent = `🏁 Game Over: ${capitalize(statusText)}`;
    }
}

function capitalize(text) {
    return text.charAt(0).toUpperCase() + text.slice(1);
}

async function renderBoard() {
    const response = await fetch(`http://localhost:8080/api/games/${gameId}`);
    const data = await response.json();

    currentPlayer = data.currentPlayer;
    gameStatus = data.status;

    const boardDiv = document.getElementById("board");
    boardDiv.innerHTML = "";
    boardDiv.style.gridTemplateColumns = `repeat(${boardSize}, 50px)`;

    data.board.forEach((row, i) => {
        row.forEach((cell, j) => {
            const cellDiv = document.createElement("div");
            cellDiv.className = "cell";
            cellDiv.textContent = cell !== " " ? cell : "";

            if (cell !== " ") {
                cellDiv.classList.add("disabled");
            } else if (gameStatus === "IN_PROGRESS") {
                cellDiv.addEventListener("click", () => makeMove(i, j));
            } else {
                cellDiv.classList.add("disabled");
            }

            boardDiv.appendChild(cellDiv);
        });
    });
    boardElement.style.gridTemplateColumns = `repeat(${boardSize}, 40px)`;
    updateStatus();
}

async function makeMove(row, col) {
    const response = await fetch(`http://localhost:8080/api/games/${gameId}/moves?row=${row}&col=${col}`, {
        method: "POST"
    });
    const data = await response.json();

    currentPlayer = data.currentPlayer;
    gameStatus = data.status;

    await renderBoard();
}
