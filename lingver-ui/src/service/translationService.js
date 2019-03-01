import axios from 'axios'

export const translationService = {
    addTranslation,
    searchTranslationsByWord,
    getTranslations,
    saveNewTranslation
};

function addTranslation(translation) {
    return axios.post('/profile/translations', translation);
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

function saveNewTranslation(translation) {
    return axios.post('/translations', translation)
        .then(response => {
            addTranslation(response.data)
        }).catch(error => {
            return Promise.reject(error);
        });
}


