import axios from 'axios'

export const translationService = {
    addTranslation,
    searchTranslationsByWord,
    getTranslations,
    saveNewTranslation,
    removeTranslations
};

function addTranslation(translation) {
    return axios.post(process.env.SERVER + '/profile/translations', translation);
}

function searchTranslationsByWord(word) {
    return axios.get(process.env.SERVER + `/translations?word=${word}`)
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
            sortDirection: params.sortDirection.toUpperCase(),
            search: params.search
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


function removeTranslations(ids) {
    return axios.delete('/profile/translations', {data: {ids: ids}});
}



