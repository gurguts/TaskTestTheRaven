function createCustomer() {
    var fullName = document.getElementById('fullName').value;
    var email = document.getElementById('email').value;
    var phone = document.getElementById('phone').value;

    var customerDTO = {
        fullName: fullName,
        email: email,
        phone: phone
    };

    fetch('/api/v1/customers', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(customerDTO),
    })
        .then(response => {
            if (!response.ok) {
                throw response;
            }
            return response.json();
        })
        .then(data => console.log(data))
        .catch(error => {
            error.text().then(errorMessage => {
                console.error('Error:', errorMessage);
            });
        });
}