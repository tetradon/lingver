import axios from 'axios'

export const exerciseService = {
    getWordTranslationExerciseSet,
    getTranslationWordExerciseSet,
    saveResults
};

function getWordTranslationExerciseSet(translationIds) {
    return axios.get(`/exercise/word-translation?ids=${translationIds}`);
}

function getTranslationWordExerciseSet(translationIds) {
    return axios.get(`/exercise/translation-word?ids=${translationIds}`);
}

function saveResults(results) {
    return axios.post('/exercise/result',
        results
    );
}




