import axios from 'axios'

export const registrationService = {
    register
};

function register(user) {
    return axios.post(process.env.SERVER + '/register', user, {withCredentials: true})
        .then(response => {
            return response.data;
        }).catch(error => {
            return Promise.reject(error);
        });
}



