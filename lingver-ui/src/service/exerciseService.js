import axios from 'axios'

export const exerciseService = {
    getWordTranslationExerciseSet
};

function getWordTranslationExerciseSet(translationIds) {
    return axios.get(`/exercise/word-translation?ids=${translationIds}`);
}



