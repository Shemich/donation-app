document.getElementById('sendDonate').addEventListener('click', async () => {
    const inputCountry = document.getElementById('country');
    const inputDonaterNickname = document.getElementById('donateDonaterNickname');
    const country = inputCountry.value;
    const donaterNickname = inputDonaterNickname.value;

    if (country) {
        const res = await fetch('http://localhost:8080/api/v1/donate/AlexZilla', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({country, donaterNickname, isPrivate: false })
        });

        const donate = await res.json();
        console.log(donate);
    }
})
