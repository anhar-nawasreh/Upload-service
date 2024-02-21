// Function to show the login form and hide other forms
function showLoginForm() {
    document.getElementById("loginForm").style.display = "block";
    document.getElementById("signUpForm").style.display = "none";
    document.getElementById("uploadForm").style.display = "none";
}

// Function to show the sign-up form and hide other forms
function showSignUpForm() {
    document.getElementById("loginForm").style.display = "none";
    document.getElementById("signUpForm").style.display = "block";
    document.getElementById("uploadForm").style.display = "none";
}

// Function to show the upload form and hide other forms
function showUploadForm() {
    document.getElementById("loginForm").style.display = "none";
    document.getElementById("signUpForm").style.display = "none";
    document.getElementById("uploadForm").style.display = "block";
}

// Function to handle login form submission
function login() {
    var email = document.getElementById("loginEmail").value;
    var password = document.getElementById("loginPassword").value;

console.log("h");
    // Prepare data for the POST request
    var data = {
        email: email,
        password: password
    };

    // Make a POST request to localhost:8081/login
    fetch('http://localhost:8081/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        // Check if the response is successful (status code 200-299)
        if (response.ok) {
            // Parse the JSON response
            return response.json();
        } else {
            // Handle non-successful responses (e.g., 404, 500)
            throw new Error('Failed to fetch');
        }
    })
    .then(data => {
        // Handle the successful response data
        console.log('Response:', data);
    })
    .catch(error => {
        // Handle any errors that occurred during the fetch
        console.error('Error:', error);
    });
}


// Function to handle sign-up form submission
function signUp() {
    // Implement sign-up functionality here
    // For example, you can collect user information and register the user
    return false; // Prevent form submission for demonstration purpose
}

// Function to handle video upload form submission
function uploadVideo() {
    // Implement video upload functionality here
    // For example, you can upload the video file to a server
    return false; // Prevent form submission for demonstration purpose
}
