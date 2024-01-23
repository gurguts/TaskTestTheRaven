async function deleteCustomer() {
    const id = document.getElementById('customerIdDelete').value;
    const response = await fetch('/api/v1/customers/' + id, {
        method: 'DELETE',
    });
    if (!response.ok) {
        document.getElementById('customerInfoDelete').innerText = await response.text();
    } else {
        document.getElementById('customerInfoDelete').innerText = 'Customer with id ' + id + ' deleted successfully';
    }
}

function checkInputDelete() {
    const customerId = document.getElementById('customerIdDelete').value;
    document.getElementById('deleteButton').disabled = !customerId;
}