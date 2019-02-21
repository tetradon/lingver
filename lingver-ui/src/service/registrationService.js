import axios from 'axios'

export const registrationService = {
    register
};

function register(user) {
    return axios.post('/register', user, {withCredentials: true})
        .then(response => {
            return response.data;
        }).catch(error => {
            return Promise.reject(error);
        });
}



