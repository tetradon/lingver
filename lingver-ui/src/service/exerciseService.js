import axios from 'axios'

export const exerciseService = {
    getWordTranslationExerciseSet,
    getTranslationWordExerciseSet,
    saveResults,
    saveSingleResult
};

function getWordTranslationExerciseSet(translationIds) {
    return axios.get(`/exercise/word-translation?ids=${translationIds}`);
}

function getTranslationWordExerciseSet(translationIds) {
    return axios.get(`/exercise/translation-word?ids=${translationIds}`);
}

function saveResults(results) {
    return axios.post('/exercise/results', results);
}

function saveSingleResult(result) {
    return axios.post('/exercise/result', result);
}




