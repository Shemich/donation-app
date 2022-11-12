
async function getAllDonations() {
    const res = await fetch('http://localhost:8080/api/v1/donate');
    const donations = await res.json();

    console.log(donations);
    donations.forEach(donations => donationsToHTML(donations))
}

window.addEventListener('DOMContentLoaded', getAllDonations);

function donationsToHTML({id, donaterNickname, isPrivate, amount, message}) {
    const donationsList = document.getElementById('donations');

    donationsList.insertAdjacentHTML('beforeend', `
     <div class="form-check" id="donations${id}">
            <label class="form-check-label">
                <input type="checkbox" class="form-check-input" ${isPrivate && 'checked'}>
                ${donaterNickname} ${amount} ${message} 
            </label>
        </div>
    `);
}