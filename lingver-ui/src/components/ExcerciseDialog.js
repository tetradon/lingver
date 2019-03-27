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
import classNames from 'classnames';

import CloseIcon from '@material-ui/icons/Close';
import CorrectIcon from "@material-ui/icons/Done";
import IncorrectIcon from "@material-ui/icons/Clear";
import ConfirmationDialog from "../components/ConfirmationDialog";
import IconButton from "@material-ui/core/IconButton";
import {exerciseService} from "../service/exerciseService";

function Transition(props) {
    return <Slide direction="up" {...props} />;
}

const styles = (theme) => ({
    green: {
        color: '#0ff235 !important',
    },
    red: {
        color: '#ef3b3b !important',
    },
    checked: {},
    answerButton: {
        textTransform: 'none',
        marginTop: theme.spacing.unit,
        '&:hover': {
            background: "#dad8ff",
        },
    },
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
});

class ExerciseDialog extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentIndex: 0,
            selectedAnswer: null,
            closeDialogIsOpen: false,
            correctAnswersCount: 0,
            results: []
        };

    }

    correctWasSelected(oneOfAnswers) {
        return this.state.selectedAnswer && oneOfAnswers.isCorrect === true
    }

    incorrectWasSelected(oneOfAnswers) {
        return this.state.selectedAnswer
            && this.state.selectedAnswer === oneOfAnswers
            && this.state.selectedAnswer.isCorrect === false
    }

    handleResponse = (event, answer) => {
        this.setState({selectedAnswer: answer});
    };

    onNext = () => {
        let {selectedAnswer, currentIndex, results} = this.state;
        if (selectedAnswer.isCorrect) {
            this.setState({correctAnswersCount: this.state.correctAnswersCount + 1})
        }
        results.push(
            {
                profileTranslationId: this.props.trainingSet[currentIndex].profileTranslationId,
                answerCorrect: selectedAnswer.isCorrect,
                exerciseId: this.props.trainingSet[currentIndex].exerciseId
            }
        );
        this.props.trainingSet[currentIndex].isUserAnswerCorrect = selectedAnswer.isCorrect;
        this.setState({selectedAnswer: null});
        this.setState({currentIndex: this.state.currentIndex + 1}, () => {
            if (this.isExerciseFinished()) {
                exerciseService.saveResults(this.state.results)
            }
        });

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
                <div>

                    <Grid key={item.profileTranslationId}
                          container
                          direction={"row"}
                          spacing={16}
                          alignItems={"center"}
                          justify={"center"}>
                        <Grid xs={6} item>
                            <Typography align={"center"} variant={"title"}> {item.question} </Typography>
                        </Grid>
                        <Grid xs={6} item>
                            <Grid container direction={"column"}>
                                {
                                    item.answers.map(answer => {
                                        return (
                                            <Grid item key={answer.value}>
                                                <Button
                                                    size={"large"}
                                                    disabled={this.state.selectedAnswer !== null}
                                                    onClick={(e) => this.handleResponse(e, answer)}
                                                    className={classNames(classes.answerButton,
                                                        {
                                                            [classes.red]: this.incorrectWasSelected(answer),
                                                            [classes.green]: this.correctWasSelected(answer)
                                                        }
                                                    )}
                                                >

                                                    {answer.value}
                                                    {
                                                        this.correctWasSelected(answer) ?
                                                            <CorrectIcon/> : this.incorrectWasSelected(answer) ?
                                                            <IncorrectIcon/> : null

                                                    }</Button>
                                            </Grid>

                                        )
                                    })
                                }
                            </Grid>
                        </Grid>
                    </Grid>
                </div>
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
                        <Typography variant="h6">Word-Translation</Typography>
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
                                        <Typography variant={"h3"}> Not bad! </Typography>
                                    </Grid>
                                    <Grid className={classes.dialogMargin}>
                                        <Typography variant={"h5"}>Your result
                                            is {this.state.correctAnswersCount} / {this.props.trainingSet.length}</Typography>
                                    </Grid>
                                </Grid>

                                :
                                questionList[this.state.currentIndex]

                        }

                    </DialogContent>
                    <DialogActions>
                        {this.state.selectedAnswer
                            ?
                            <Button size={"large"} onClick={this.onNext} color="primary">
                                Next
                            </Button>
                            : null}
                        {this.isExerciseFinished() ?
                            <Button size={"large"} onClick={this.props.onClose} color="primary">
                                Close
                            </Button> : null}
                    </DialogActions>
                    <ConfirmationDialog
                        open={this.state.closeDialogIsOpen}
                        onCancel={this.closeCloseDialog}
                        onConfirm={this.props.onClose}
                        title={"Confirm closing"}
                        contentText={"Are you sure?\n All your progress will be lost!"}
                    />
                </Dialog>
            </div>
        );
    }
}

ExerciseDialog.propTypes = {
    trainingSet: PropTypes.array,
    onClose: PropTypes.func,

};
export default withStyles(styles)(ExerciseDialog);