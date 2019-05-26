import React from 'react';
import Button from '@material-ui/core/Button';
import * as PropTypes from 'prop-types';
import {withStyles} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import classNames from 'classnames';
import CorrectIcon from "@material-ui/icons/Done";
import IncorrectIcon from "@material-ui/icons/Clear";

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
});


class ExercisePage extends React.Component {

    correctWasSelected(oneOfAnswers) {
        return this.props.selectedAnswer && oneOfAnswers.isCorrect === true;
    }

    incorrectWasSelected(oneOfAnswers) {
        return this.props.selectedAnswer
            && this.props.selectedAnswer === oneOfAnswers
            && this.props.selectedAnswer.isCorrect === false;
    }


    render() {
        const {classes} = this.props;
        return (
            <div>
                <Grid key={this.props.exerciseItem.profileTranslationId}
                      container
                      direction={"row"}
                      spacing={16}
                      alignItems={"center"}
                      justify={"center"}>
                    <Grid xs={6} item>
                        <Typography align={"center"} variant={"title"}> {this.props.exerciseItem.question} </Typography>
                    </Grid>
                    <Grid xs={6} item>
                        <Grid container direction={"column"}>
                            {
                                this.props.exerciseItem.answers.map(answer => {
                                    return (
                                        <Grid item key={answer.value}>
                                            <Button
                                                size={"large"}
                                                disabled={this.props.selectedAnswer !== null}
                                                onClick={(e) => this.props.onResponse(e, answer)}
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
        );

    }
}

ExercisePage.propTypes = {
    exerciseItem: PropTypes.object,
    selectedAnswer: PropTypes.object,
    onResponse: PropTypes.func
};
export default withStyles(styles)(ExercisePage);