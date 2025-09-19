

function sendValue(id) {

    fetch("/Transaction/View/" + id)
        .then(response => response.json())
        .then(data => {

            document.getElementById("transId").innerHTML = data.id;
            document.getElementById("transDescription").innerHTML = data.description;
            document.getElementById("transDate").innerHTML = data.date;
            document.getElementById("transTransactionType").innerHTML = data.transactionType.toString();
            document.getElementById("transAmount").innerHTML = data.amount;
            // document.addEventListener("DOMContentLoaded",function(){
            //     var myModal=new bootstrap.Modal(document.getElementById('transactionalModal'));
            //     myModal.show();
            // })

        })
        .catch(error => console.error("Error", error));
}

function openPopup() {
    var open = new bootstrap.Modal(document.getElementById("transctionalModal"));
    open.show();
}