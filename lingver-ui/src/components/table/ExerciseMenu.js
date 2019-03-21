import React, {Component} from 'react';
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import * as PropTypes from "prop-types";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import TrainIcon from "@material-ui/icons/FitnessCenterTwoTone";
import {exerciseService} from "../../service/exerciseService"

class ExerciseMenu extends Component {
    state = {
        trainMenuAnchor: null,
    };

    handleTrainClick = event => {
        this.setState({trainMenuAnchor: event.currentTarget});
    };

    handleTrainClose = () => {
        this.setState({trainMenuAnchor: null});
    };

    handleWordTranslationClick = () => {
        exerciseService.getWordTranslationExerciseSet(this.props.selected);
    };




    render() {
        const {trainMenuAnchor} = this.state;
        const open = Boolean(trainMenuAnchor);

        if (!this.props.hidden) {
            return (
                <div>
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
                        <MenuItem onClick={this.handleTrainClose}>
                            Translation - Translation
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
    selected: PropTypes.array
};
export default ExerciseMenu;