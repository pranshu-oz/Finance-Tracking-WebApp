

function sendValue(id) {

    fetch("/Transaction/View/" + id)
        .then(response => response.json())
        .then(data => {

            document.getElementById("transId").innerHTML = data.id;
            document.getElementById("transDescription").innerHTML = data.description;
            document.getElementById("transDate").innerHTML = data.date;
            document.getElementById("transTransactionType").innerHTML = data.transactionType;
            document.getElementById("transAmount").innerHTML = data.amount;
            document.getElementById("transCategory").innerHTML = data.category.name;
        })
        .catch(error => console.error("Error", error));
}

function openPopup() {
    var open = new bootstrap.Modal(document.getElementById("transctionalModal"));
    open.show();
}