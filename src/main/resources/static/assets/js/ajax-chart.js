
window.onload = function () {
    getTotalExpanse();
    getTotalIncome();
    getRecentTransaction();
}

function getTotalIncome() {

    fetch("/total-income")
        .then(response => response.json())
        .then(data => {
            document.getElementById("totalIncome").insertAdjacentText("afterbegin", "₹" + data.toLocaleString("en-US"));
            console.log(data.toLocaleString("en-US"));

        })
        .catch(error => console.error("Error", error));
}

function getTotalExpanse() {

    fetch("/total-expanse")
        .then(response => response.json())
        .then(data => {
            document.getElementById("totalExpenses").insertAdjacentText("afterbegin", "₹" + data.toLocaleString("en-US"));
            console.log(data);

        })
        .catch(error => console.error("Error", error));
}

function getRecentTransaction() {
    fetch("/recent-transaction")
        .then(response => response.json())
        .then(data => {
            data.forEach(tr => {
                document.getElementById("recentTransaction").insertAdjacentHTML("beforeend", "<tr><td><a href='#' class='text-muted'>" + tr.id + "</a></td><td>" + tr.date + "</td><td>" + tr.amount + "</td><td>" + tr.description + "</td><td>" + tr.transactionType + "</td></tr>");
            })
        });
}


