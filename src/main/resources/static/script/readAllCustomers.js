async function readAllCustomers() {
    const table = document.getElementById('customersTable');
    table.innerHTML = `
                <tr>
                    <th>ID</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                </tr>
            `;

    const response = await fetch('/api/v1/customers');
    const customers = await response.json();

    customers.forEach((customer) => {
        const row = table.insertRow(-1);
        row.insertCell(0).innerHTML = customer.id;
        row.insertCell(1).innerHTML = customer.fullName;
        row.insertCell(2).innerHTML = customer.email;
        row.insertCell(3).innerHTML = customer.phone;
    });
}