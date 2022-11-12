
async function getAllTips() {
    const res = await fetch('https://admin.netmonet.co/api/external/v1/transaction/restaurant/overall/comments', {
        method: 'GET',
        mode: "no-cors",
        headers: {
            'Authorization': "Token ZWUwNzMwZWQtYjQxOS00YzJlLWJkYzctYjA3OGNlZTA4MTQ0Oks1RGhFN3o4MzJJR1RObzN2MnZYZmRBRk1mWnQ0UFNzcEkzSzNL",
            "Access-Control-Allow-Origin" : "*",
            "Access-Control-Allow-Credentials" : true,
            "Accept": "*/*",
            "Accept-Encoding": "gzip, deflate, br",
            "Connection": "keep-alive",
        }
    });

    const tips = await res.json();

    console.log(tips);
    tips.forEach(tips => tipsToHTML(tips))
}

window.addEventListener('DOMContentLoaded', getAllTips);

function tipsToHTML({name, message, amount}) {
    const donationsList = document.getElementById('donations');

    donationsList.insertAdjacentHTML('beforeend', `
     <div class="form-check" id="tips${id}">
            <label class="form-check-label">
                <input type="checkbox" class="form-check-input">
                ${name} ${amount} ${message} 
            </label>
        </div>
    `);
}