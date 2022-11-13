
async function getBalance() {
    const res = await fetch('http://localhost:8080/api/v1/streamers/4');
    const streamer = await res.json();

    console.log(streamer);
    streamer.forEach(streamer => balanceToHTML(streamer))
}

window.addEventListener('DOMContentLoaded', getBalance);

function balanceToHTML({balance}) {
    const donationsList = document.getElementById('streamer');

    donationsList.insertAdjacentHTML('beforeend', `
     <div class="form-check" id="streamers${id}">
            <label class="form-check-label">
                ${balance} RUB
            </label>
        </div>
    `);
}