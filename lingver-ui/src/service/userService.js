import axios from 'axios'

export const userService = {
    login,
    logout,
};

function login(username, password) {
    let bodyFormData = new FormData();
    bodyFormData.set('username', username);
    bodyFormData.set('password', password);

    return axios.post('/login', bodyFormData, {withCredentials: true})
        .then(response => {
            localStorage.setItem('user', JSON.stringify(response.data));
            return response.data;
        }).catch(error => {
            return Promise.reject(error);
        });
}

function logout() {
    fetch("/logout", {method: "POST"});
    localStorage.removeItem('user');
}

