import React, {Component} from 'react';
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import * as PropTypes from "prop-types";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import TrainIcon from "@material-ui/icons/FitnessCenterTwoTone";

const options = [
    'Word - Translation',
    'Translation - Word'
];

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
                        {options.map(option => (
                            <MenuItem key={option} onClick={this.handleTrainClose}>
                                {option}
                            </MenuItem>
                        ))}
                    </Menu>
                </div>
            )
        } else {
            return null;
        }
    };
}

ExerciseMenu.propTypes = {
    hidden: PropTypes.bool
};
export default ExerciseMenu;