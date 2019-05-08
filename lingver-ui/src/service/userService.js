import axios from 'axios'

export const userService = {
    login,
    logout,
    getActiveUser,
    removeUserFromStorage
};

function login(username, password) {
    let bodyFormData = new FormData();
    bodyFormData.set('username', username);
    bodyFormData.set('password', password);
    return axios.post(process.env.REACT_APP_REST + '/login', bodyFormData, {withCredentials: true})
        .then(response => {
            localStorage.setItem('activeUser', JSON.stringify(response.data));
            return response.data;
        }).catch(error => {
            return Promise.reject(error);
        });
}

function logout() {
    axios.post(process.env.REACT_APP_REST + "/logout");
    removeUserFromStorage();
}

function getActiveUser() {
    return localStorage.getItem('activeUser');
}

function removeUserFromStorage() {
    return localStorage.removeItem('activeUser');
}

