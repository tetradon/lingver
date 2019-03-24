import React, {Component} from 'react';
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import * as PropTypes from "prop-types";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import TrainIcon from "@material-ui/icons/FitnessCenterTwoTone";
import {exerciseService} from "../../service/exerciseService"
import ExerciseDialog from "../ExcerciseDialog";

class ExerciseMenu extends Component {
    state = {
        trainMenuAnchor: null,
        exerciseDialogIsOpen: false,
        trainingSet: []
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
    };

    handleWordTranslationClick = () => {
        this.handleTrainClose();
        exerciseService.getWordTranslationExerciseSet(this.props.selected)
            .then(response => {
                this.setState({trainingSet: response.data});
            });
        this.setState({exerciseDialogIsOpen: true});

    };

    handleTranslationWordClick = () => {
        this.handleTrainClose();
        exerciseService.getTranslationWordExerciseSet(this.props.selected)
            .then(response => {
                this.setState({trainingSet: response.data});
            });
        this.setState({exerciseDialogIsOpen: true});
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
                                trainingSet={this.state.trainingSet}/>
                            : null
                    }

                    <Tooltip title="Train">
                        <IconButton
                            aria-label="Train"
                            aria-haspopup="true"
                            onClick={this.handleTrainClick}
                        >
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
                            Word - Translation
                        </MenuItem>
                        <MenuItem onClick={this.handleTranslationWordClick}>
                            Translation - Word
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
    hidden: PropTypes.bool,
    selected: PropTypes.array,
    onQueryParamsChange: PropTypes.func
};
export default ExerciseMenu;