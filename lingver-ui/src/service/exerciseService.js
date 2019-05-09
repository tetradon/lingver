import axios from 'axios'

export const exerciseService = {
    getWordTranslationExerciseSet,
    getTranslationWordExerciseSet,
    saveResults,
    saveSingleResult
};

function getWordTranslationExerciseSet(translationIds) {
    return axios.get(process.env.REACT_APP_REST_API + `/exercise/word-translation?ids=${translationIds}`);
}

function getTranslationWordExerciseSet(translationIds) {
    return axios.get(process.env.REACT_APP_REST_API + `/exercise/translation-word?ids=${translationIds}`);
}

function saveResults(results) {
    return axios.post(process.env.REACT_APP_REST_API + '/exercise/results', results);
}

function saveSingleResult(result) {
    return axios.post(process.env.REACT_APP_REST_API + '/exercise/result', result);
}




