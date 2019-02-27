import axios from 'axios'

export const userService = {
    login,
    logout,
    getActiveUser,
    updateActiveUser
};

function login(username, password) {
    let bodyFormData = new FormData();
    bodyFormData.set('username', username);
    bodyFormData.set('password', password);
    return axios.post('/login', bodyFormData, {withCredentials: true})
        .then(response => {
            localStorage.setItem('activeUser', JSON.stringify(response.data));
            return response.data;
        }).catch(error => {
            return Promise.reject(error);
        });
}

function logout() {
    axios.post("/logout");
    localStorage.removeItem('activeUser');
}

function getActiveUser() {
    return localStorage.getItem('activeUser');
}

function updateActiveUser() {
    localStorage.removeItem('activeUser');
    return axios.get('/profile')
        .then(response => {
            localStorage.setItem('activeUser', JSON.stringify(response.data));
        }).catch(error => {
            localStorage.removeItem('activeUser');
        });
}

