import React, {Component} from 'react';
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import * as PropTypes from "prop-types";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import TrainIcon from "@material-ui/icons/FitnessCenterTwoTone";
import {exerciseService} from "../../service/exerciseService"
import ExerciseDialog from "../ExcerciseDialog";
import {withSnackbar} from "notistack";
import {Trans} from "react-i18next";

class ExerciseMenu extends Component {
    state = {
        trainMenuAnchor: null,
        exerciseDialogIsOpen: false,
        trainingSet: [],
        exerciseName: ''
    };

    handleTrainClick = event => {
        this.setState({trainMenuAnchor: event.currentTarget});
    };

    handleTrainClose = () => {
        this.setState({trainMenuAnchor: null});
    };

    closeExerciseDialog = () => {
        this.setState({exerciseDialogIsOpen: false});
        this.props.onQueryParamsChange();
        this.props.clearSelected();
    };

    handleWordTranslationClick = () => {
        this.handleTrainClose();
        exerciseService.getWordTranslationExerciseSet(this.props.selected)
            .then(response => {
                this.setState({trainingSet: response.data});
                this.setState({exerciseName: <Trans>Word - Translation</Trans>});
                this.setState({exerciseDialogIsOpen: true});
            })
            .catch((error) => {
                error.response.data.forEach((error) => {
                    this.props.enqueueSnackbar(error.message);
                });
            });
    };

    handleTranslationWordClick = () => {
        this.handleTrainClose();
        exerciseService.getTranslationWordExerciseSet(this.props.selected)
            .then(response => {
                this.setState({trainingSet: response.data});
                this.setState({exerciseName: <Trans>Translation - Word</Trans>});
                this.setState({exerciseDialogIsOpen: true});
            })
            .catch((error) => {
                error.response.data.forEach((error) => {
                    this.props.enqueueSnackbar(error.message);
                });
            });

    };


    render() {
        const {trainMenuAnchor, exerciseDialogIsOpen} = this.state;
        const open = Boolean(trainMenuAnchor);

        if (!this.props.hidden) {
            return (
                <div>
                    {
                        exerciseDialogIsOpen
                            ?
                            <ExerciseDialog
                                onClose={this.closeExerciseDialog}
                                trainingSet={this.state.trainingSet}
                                exerciseName={this.state.exerciseName}
                            />
                            : null
                    }

                    <Tooltip title={<Trans>Train</Trans>}>
                        <IconButton onClick={this.handleTrainClick}>
                            <TrainIcon/>
                        </IconButton>
                    </Tooltip>
                    <Menu
                        id="long-menu"
                        anchorEl={trainMenuAnchor}
                        open={open}
                        onClose={this.handleTrainClose}
                    >
                        <MenuItem onClick={this.handleWordTranslationClick}>
                            <Trans>Word - Translation</Trans>
                        </MenuItem>
                        <MenuItem onClick={this.handleTranslationWordClick}>
                            <Trans>Translation - Word</Trans>
                        </MenuItem>
                    </Menu>
                </div>
            )
        } else {
            return null;
        }
    };
}

ExerciseMenu.propTypes = {
    clearSelected: PropTypes.func,
    hidden: PropTypes.bool,
    selected: PropTypes.array,
    onQueryParamsChange: PropTypes.func
};
export default withSnackbar(ExerciseMenu);