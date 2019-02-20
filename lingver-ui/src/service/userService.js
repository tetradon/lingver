import axios from 'axios'

export const userService = {
    login,
    logout,
    getUser
};

function login(username, password) {
    let bodyFormData = new FormData();
    bodyFormData.set('username', username);
    bodyFormData.set('password', password);
    return axios.post('/login', bodyFormData, {withCredentials: true})
        .then(response => {
            localStorage.setItem('isAuthenticated', JSON.stringify(response.data));
            return response.data;
        }).catch(error => {
            return Promise.reject(error);
        });
}

function logout() {
    axios.post("/logout");
    localStorage.removeItem('isAuthenticated');
}

function getUser() {
    return localStorage.getItem('isAuthenticated');
}

