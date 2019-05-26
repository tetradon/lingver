import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';

import MuiDialogTitle from '@material-ui/core/DialogTitle';
import Slide from '@material-ui/core/Slide';
import * as PropTypes from 'prop-types';
import {withStyles} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";

import CloseIcon from '@material-ui/icons/Close';
import ConfirmationDialog from "../components/ConfirmationDialog";
import IconButton from "@material-ui/core/IconButton";
import {exerciseService} from "../service/exerciseService";
import ExercisePage from "./ExercisePage";
import {Trans} from "react-i18next";
import {exerciseKeys} from "../constants";

function Transition(props) {
    return <Slide direction="up" {...props} />;
}

const englishSpeaker = new SpeechSynthesisUtterance();
englishSpeaker.lang = 'en-US';
englishSpeaker.rate = 0.5;

const styles = (theme) => ({
    dialogTitle: {
        borderBottom: `1px solid ${theme.palette.divider}`,
        padding: theme.spacing.unit * 2,
    },
    closeButton: {
        position: 'absolute',
        right: theme.spacing.unit,
        top: theme.spacing.unit,
    },
    dialogMargin: {
        marginTop: theme.spacing.unit * 5
    },
    grow: {
        flexGrow: 1,
    },
});


class ExerciseDialog extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentIndex: 0,
            selectedAnswer: null,
            closeDialogIsOpen: false,
            correctAnswersCount: 0,
            results: [],
        };
        this.speakQuestion();
    }

    speakQuestion = () => {
        const {currentIndex} = this.state;
        const {trainingSet} = this.props;
        englishSpeaker.text = trainingSet[currentIndex].question;
        speechSynthesis.speak(englishSpeaker);
    };

    speakAnswer = () => {
        const {currentIndex} = this.state;
        const {trainingSet} = this.props;
        const correct = trainingSet[currentIndex].answers.filter(answer => {
            return answer.isCorrect;
        })[0];
        englishSpeaker.text = correct.value;
        speechSynthesis.speak(englishSpeaker);
    };

    handleResponse = (event, answer) => {
        const {currentIndex} = this.state;
        const {trainingSet} = this.props;
        this.setState({selectedAnswer: answer});
        if (!this.isExerciseFinished() && trainingSet[currentIndex].exerciseKey === exerciseKeys.translationWord) {
            this.speakAnswer();
        }
    };

    onNext = () => {
        let {selectedAnswer, currentIndex} = this.state;
        let {trainingSet} = this.props;
        if (selectedAnswer.isCorrect) {
            this.setState({correctAnswersCount: this.state.correctAnswersCount + 1})
        }
        this.props.trainingSet[currentIndex].isUserAnswerCorrect = selectedAnswer.isCorrect;
        this.setState({selectedAnswer: null});
        this.setState({currentIndex: this.state.currentIndex + 1}, () => {
            if (!this.isExerciseFinished() && trainingSet[currentIndex].exerciseKey === exerciseKeys.wordTranslation) {
                this.speakQuestion();
            }
        });

        exerciseService.saveSingleResult({
                profileTranslationIds: this.props.trainingSet[currentIndex].profileTranslationIds,
                answerCorrect: selectedAnswer.isCorrect,
                exerciseId: this.props.trainingSet[currentIndex].exerciseId
            }
        );
    };

    openCloseDialog = () => {
        this.setState({closeDialogIsOpen: true})
    };

    closeCloseDialog = () => {
        this.setState({closeDialogIsOpen: false})
    };

    isExerciseFinished = () => {
        return this.state.currentIndex === this.props.trainingSet.length && this.state.current !== 0;
    };

    render() {
        const {classes} = this.props;
        const questionList = this.props.trainingSet.map(item => {
            return (
                <ExercisePage exerciseItem={item} selectedAnswer={this.state.selectedAnswer}
                              onResponse={this.handleResponse}/>
            )
        });
        return (
            <div>
                <Dialog
                    className={classes.dialogMargin}
                    fullWidth
                    maxWidth={'sm'}
                    open={true}
                    TransitionComponent={Transition}
                    onClose={this.props.onClose}
                    scroll={'body'}
                    disableBackdropClick
                    disableEscapeKeyDown
                >
                    <MuiDialogTitle disableTypography className={classes.dialogTitle}>
                        <Typography variant="h6">{this.props.exerciseName}</Typography>
                        <IconButton className={classes.closeButton}
                                    onClick={this.isExerciseFinished() ? this.props.onClose : this.openCloseDialog}>
                            <CloseIcon/>
                        </IconButton>
                    </MuiDialogTitle>
                    <DialogContent>
                        {
                            this.isExerciseFinished()
                                ?
                                <Grid container className={classes.dialogMargin}
                                      alignContent={"center"}
                                      alignItems={"center"}
                                      direction={"column"}>
                                    <Grid item>
                                        <Typography variant={"h3"}>
                                            <Trans>Not bad!</Trans>
                                        </Typography>
                                    </Grid>
                                    <Grid item className={classes.dialogMargin}>
                                        <Typography variant={"h5"}>
                                            <Trans>Your result is</Trans>
                                        </Typography>
                                        <Typography align={"center"} variant={"h5"}>
                                            {this.state.correctAnswersCount} / {this.props.trainingSet.length}
                                        </Typography>
                                    </Grid>
                                </Grid>
                                :
                                questionList[this.state.currentIndex]

                        }
                    </DialogContent>
                    <DialogActions style={{justifyContent: 'space-between'}}>
                        {
                            !this.isExerciseFinished()
                                ?
                                <Typography>
                                    {this.state.currentIndex + 1} <Trans>from</Trans> {this.props.trainingSet.length}
                                </Typography>
                                :
                                null
                        }
                        <div style={styles.grow}/>
                        {
                            this.state.selectedAnswer
                                ?
                                <Button size={"large"} onClick={this.onNext} color="primary">
                                    <Trans>Next</Trans>
                                </Button>
                                :
                                null
                        }
                        {
                            this.isExerciseFinished()
                                ?
                                <Button size={"large"} onClick={this.props.onClose} color="primary">
                                    <Trans>Close</Trans>
                                </Button>
                                :
                                null
                        }
                    </DialogActions>
                    <ConfirmationDialog
                        open={this.state.closeDialogIsOpen}
                        onCancel={this.closeCloseDialog}
                        onConfirm={this.props.onClose}
                        title={<Trans>Confirm closing</Trans>}
                        contentText={<Trans>Are you sure? Your progress will be saved</Trans>}
                    />
                </Dialog>
            </div>
        );
    }
}

ExerciseDialog.propTypes = {
    trainingSet: PropTypes.array,
    onClose: PropTypes.func,
    exerciseName: PropTypes.object //trans string
};
export default withStyles(styles)(ExerciseDialog);