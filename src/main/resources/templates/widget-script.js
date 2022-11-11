sessionStorage.setItem('token', "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBRE1JTiIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOjIsImlhdCI6MTY2ODEyNjI3MiwiZXhwIjoxNjY4NzMxMDcyfQ.gF6H7LHokam-Zv5tXAStgXqt_DKVSKWvYEzMo-LF49I")
console.log(sessionStorage.getItem('token'));

async function getWidget() {
    const res = await fetch(`http://localhost:8080/api/v1/widget/1`, {
        method: 'GET',
        headers: {
            'Authorization': "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBRE1JTiIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOjIsImlhdCI6MTY2ODEyNjc3NCwiZXhwIjoxNjY4NzMxNTc0fQ.ud-p0PtbpxKDdw3RR9BqsUTSyPCWY9Mkus0wf95Locc",
            "Access-Control-Allow-Origin" : "*",
            "Access-Control-Allow-Credentials" : true,
            "Accept": "*/*",
            "Accept-Encoding": "gzip, deflate, br",
            "Connection": "keep-alive",

        }
    });
    const widget = await res.json();
    console.log(widget);
    widgetToHTML(widget);
}

window.addEventListener('DOMContentLoaded', getWidget);

function widgetToHTML({id, donateAuthor, amount, donateMessage}) {
    const widgetList = document.getElementById('widget');
    window.speechSynthesis.cancel();

    // прочитать текст
    const text = donateMessage;
    const utterance = new SpeechSynthesisUtterance(text);
    window.speechSynthesis.speak(utterance);
    console.log("widgetToHTML");
    widgetList.insertAdjacentHTML('beforeend', `
     <div id="widget${id}">
            <audio src="point.mp3" autoplay="autoplay"></audio>
            <img src="https://media1.giphy.com/media/10FwycrnAkpshW/giphy.gif" alt="я джифка" width="100%">
            <span class="text1"> ${donateAuthor} - ${amount} RUB!</span>
            <span class="text2">${donateMessage}</span>
        </div>
    `);
}
