import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Slide from '@material-ui/core/Slide';
import * as PropTypes from 'prop-types';
import {Trans} from "react-i18next";

function Transition(props) {
    return <Slide direction="up" {...props} />;
}

class ConfirmationDialog extends React.Component {

    render() {
        return (
            <div>
                <Dialog
                    open={this.props.open}
                    TransitionComponent={Transition}
                    keepMounted
                    onClose={this.props.onCancel}

                >
                    <DialogTitle>
                        {this.props.title}
                    </DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            {this.props.contentText}
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.props.onCancel} color="primary">
                            <Trans>Cancel</Trans>
                        </Button>
                        <Button onClick={this.props.onConfirm} color="secondary">
                            <Trans>Confirm</Trans>
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}

ConfirmationDialog.propTypes = {
    open: PropTypes.bool,
    title: PropTypes.object,
    contentText: PropTypes.object,
    onCancel: PropTypes.func,
    onConfirm: PropTypes.func
};
export default ConfirmationDialog;