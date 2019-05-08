import axios from 'axios'

export const exerciseService = {
    getWordTranslationExerciseSet,
    getTranslationWordExerciseSet,
    saveResults,
    saveSingleResult
};

function getWordTranslationExerciseSet(translationIds) {
    return axios.get(process.env.SERVER + `/exercise/word-translation?ids=${translationIds}`);
}

function getTranslationWordExerciseSet(translationIds) {
    return axios.get(process.env.SERVER + `/exercise/translation-word?ids=${translationIds}`);
}

function saveResults(results) {
    return axios.post(process.env.SERVER + '/exercise/results', results);
}

function saveSingleResult(result) {
    return axios.post(process.env.SERVER + '/exercise/result', result);
}




