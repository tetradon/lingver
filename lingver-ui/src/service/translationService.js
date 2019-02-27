import axios from 'axios'

export const translationService = {
    addTranslation,
    searchTranslationsByWord,
    getTranslations,
};

function addTranslation(translation) {
    return axios.post('/profile/translations', translation)
        .then(response => {
            return response.data;
        }).catch(error => {
            return Promise.reject(error);
        });
}

function searchTranslationsByWord(word) {
    return axios.get(`/translations?word=${word}`)
        .then(response => {
            return response.data;
        }).catch(error => {
            return Promise.reject(error);
        });
}

function getTranslations(params) {
    return axios.get('/profile/translations', {
        params: {
            size: params.size,
            page: params.page,
            sortField: params.sortField,
            sortDirection: params.sortDirection.toUpperCase()
        }
    });
}


