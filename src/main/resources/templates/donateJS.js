document.getElementById('sendDonate').addEventListener('click', async () => {
    const inputAmount = document.getElementById('donateAmount');
    const inputMessage = document.getElementById('donateMessage');
    const inputDonaterNickname = document.getElementById('donateDonaterNickname');
    const amount = inputAmount.value;
    const message = inputMessage.value;
    const donaterNickname = inputDonaterNickname.value;

    if (amount) {
        const res = await fetch('http://localhost:8080/api/v1/donate/Dinidon', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({amount, message, donaterNickname, isPrivate: false })
        });

        const donate = await res.json();
        console.log(donate);
    }
})
