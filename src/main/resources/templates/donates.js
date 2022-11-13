
async function getAllDonations() {
    const res = await fetch('http://localhost:8080/api/v1/donate');
    const donations = await res.json();

    console.log(donations);
    donations.slice().reverse().forEach(donations => donationsToHTML(donations))
}

window.addEventListener('DOMContentLoaded', getAllDonations);

function donationsToHTML({id, donaterNickname, amount, message, dateOfExpiration}) {
    const donationsList = document.getElementById('donations');

    donationsList.insertAdjacentHTML('beforeend', `
     <div class="form-check" id="donations${id}">
     <div class="donate">
                <p align="center" style="margin-top: 60px; overflow: auto"><b>${donaterNickname} задонатил ${amount} RUB</b></p>
            </div>
        </div>
    `);
}