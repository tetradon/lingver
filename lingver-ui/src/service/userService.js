export const userService = {
    login,
    logout,
};

function login(username, password) {

    /* let bodyFormData = new FormData();
     let user = {};
     bodyFormData.set('username', username);
     bodyFormData.set('password', password);
     axios.post('/login', bodyFormData)
         .then(function (response) {
            localStorage.setItem('user', JSON.stringify(response));
         });
     return user;*/
    let bodyFormData = new FormData();
    bodyFormData.set('username', username);
    bodyFormData.set('password', password);

    const requestOptions = {
        method: 'POST',
        body: bodyFormData
    };

    return fetch('/login', requestOptions)
        .then(handleResponse)
        .then(user => {
            if (user) {
                localStorage.setItem('user', JSON.stringify(user));
            }
            return user;
        });

}

function logout() {
    fetch("/logout", {method: "POST"});
    localStorage.removeItem('user');
}

function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        if (!response.ok) {
            if (response.status === 401) {
                // auto logout if 401 response returned from api
                logout();
                window.location.reload(true);
            }

            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }

        return data;
    });
}