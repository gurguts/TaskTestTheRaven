async function updateCustomer() {
    const id = document.getElementById('customerIdUpdate').value;
    const newId = document.getElementById('newId').value;
    const fullName = document.getElementById('fullNameUpdate').value;
    const phone = document.getElementById('phoneUpdate').value;

    const customerDTO = {
        id: newId,
        fullName: fullName,
        phone: phone
    };

    const response = await fetch('/api/v1/customers/' + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(customerDTO),
    });

    if (response.ok) {
        const customer = await response.json();
        document.getElementById('customerInfoUpdate').innerText =
            'ID: ' + customer.id + '\n' +
            'Full Name: ' + customer.fullName + '\n' +
            'Email: ' + customer.email + '\n' +
            'Phone: ' + customer.phone;
    } else {
        document.getElementById('customerInfo').innerText = await response.text();
    }
}