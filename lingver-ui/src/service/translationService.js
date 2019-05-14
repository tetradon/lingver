import axios from 'axios'

export const translationService = {
    addTranslation,
    searchTranslationsByWord,
    getTranslations,
    saveNewTranslation,
    removeTranslations
};

function addTranslation(translation) {
    return axios.post(process.env.REACT_APP_REST_API + '/profile/translations', translation);
}

function searchTranslationsByWord(word) {
    return axios.get(process.env.REACT_APP_REST_API + `/translations?word=${word}`)
        .then(response => {
            return response.data;
        }).catch(error => {
            return Promise.reject(error);
        });
}

function getTranslations(params) {
    return axios.get(process.env.REACT_APP_REST_API + '/profile/translations', {
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
    return axios.post(process.env.REACT_APP_REST_API + '/translations', translation)
        .then(response => {
            addTranslation(response.data)
        }).catch(error => {
            return Promise.reject(error);
        });
}


function removeTranslations(ids) {
    return axios.delete(process.env.REACT_APP_REST_API + '/profile/translations', {data: {ids: ids}});
}



