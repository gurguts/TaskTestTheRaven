function checkInput() {
    const customerId = document.getElementById('customerId').value;
    document.getElementById('readButton').disabled = !customerId;
}

async function readCustomer() {
    const id = document.getElementById('customerId').value;
    const response = await fetch('/api/v1/customers/' + id);
    if (response.ok) {
        const customer = await response.json();
        document.getElementById('customerInfo').innerText =
            'ID: ' + customer.id + '\n' +
            'Full Name: ' + customer.fullName + '\n' +
            'Email: ' + customer.email + '\n' +
            'Phone: ' + customer.phone;
    } else {
        document.getElementById('customerInfo').innerText = await response.text();
    }
}